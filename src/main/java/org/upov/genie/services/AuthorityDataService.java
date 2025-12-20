package org.upov.genie.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upov.genie.domain.dtos.*;
import org.upov.genie.domain.entities.*;
import org.upov.genie.repositories.*;
import org.upov.genie.utils.GenieApiException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityDataService {

    private final UpovAuthorityRepository authorityRepository;
    private final GenieSpeciesRepository genieSpeciesRepository;
    private final GenieSpeciesNameRepository genieSpeciesNameRepository;
    private final GenieSpeciesProtectionRepository protectionRepository;
    private final SpeciesExperienceRepository experienceRepository;
    private final OfferingRepository offeringRepository;
    private final UtilizationRepository utilizationRepository;
    private final ProtectionNameRepository protectionNameRepository;
    private final DerivationNameRepository derivationNameRepository;

    public AuthorityProtectionResponse getAuthorityProtection(Long authorityId, String lang) {
        log.debug("Fetching protection info for authority ID: {} in language: {}", authorityId, lang);

        UpovAuthority authority = authorityRepository.findById(authorityId)
            .orElseThrow(() -> new GenieApiException("Authority not found with ID: " + authorityId));

        List<GenieSpeciesProtection> protections = protectionRepository.findByAuthorityIdWithDetails(authorityId);

        boolean protectsAll = protections.stream()
            .anyMatch(p -> p.getProtectionId() != null && p.getProtectionId() == 5);

        String protectionNote = null;
        List<ProtectedSpeciesInfo> protectedSpecies = new ArrayList<>();

        if (protectsAll) {
            protectionNote = getMemberProtectionNoteByLanguage(lang);
            
            if ("TT".equals(authority.getAuthorityCode())) {
                protectionNote = getListTaxaNoteByLanguage(lang) + getTTExceptionsByLanguage(lang);
            } else if ("KR".equals(authority.getAuthorityCode())) {
                protectionNote = getKRExceptionsByLanguage(lang);
            }
        } else {
            protectionNote = getListTaxaNoteByLanguage(lang);
            
            protectedSpecies = protections.stream()
                .filter(p -> p.getProtectionId() != 5)
                .filter(p -> p.getNoteId() == null || p.getNoteId() != 45L)
                .map(p -> buildProtectedSpeciesInfo(p, lang))
                .collect(Collectors.toList());
        }

        String authorityType = authority.getAuthorityType() != null ? 
            authority.getAuthorityType().getAuthorityType() : null;

        return AuthorityProtectionResponse.builder()
            .authorityId(authority.getAuthorityId())
            .authorityName(getAuthorityNameByLanguage(authority, lang))
            .authorityCode(authority.getAuthorityCode())
            .authorityType(authorityType)
            .administrativeWebsite(authority.getAdministrativeWebAddress())
            .lawWebsite(authority.getLawWebAddress())
            .protectionNote(protectionNote)
            .protectsAllSpecies(protectsAll)
            .protectedSpecies(protectedSpecies)
            .build();
    }

    public AuthorityDUSResponse getAuthorityDUS(Long authorityId, String lang) {
        log.debug("Fetching DUS info for authority ID: {} in language: {}", authorityId, lang);

        UpovAuthority authority = authorityRepository.findById(authorityId)
            .orElseThrow(() -> new GenieApiException("Authority not found with ID: " + authorityId));

        List<SpeciesExperience> experiences = experienceRepository.findByAuthorityIdWithDetails(authorityId);
        List<Offering> offerings = offeringRepository.findByAuthorityIdWithDetails(authorityId);
        List<Utilization> utilizationsAsUtilizer = utilizationRepository.findByUtilizingAuthorityIdWithDetails(authorityId);
        List<Utilization> utilizationsAsProvider = utilizationRepository.findByProvidingAuthorityIdWithDetails(authorityId);

        List<TaxonExperienceInfo> taxaWithExperience = experiences.stream()
            .map(e -> buildTaxonExperienceInfo(e, lang))
            .collect(Collectors.toList());

        List<OfferingDetailInfo> offeringDetails = offerings.stream()
            .map(o -> buildOfferingDetailInfo(o, lang))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        List<UtilizationDetailInfo> utilizationDetails = new ArrayList<>();
        utilizationDetails.addAll(utilizationsAsUtilizer.stream()
            .map(u -> buildUtilizationDetailInfo(u, true, lang))
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));
        utilizationDetails.addAll(utilizationsAsProvider.stream()
            .map(u -> buildUtilizationDetailInfo(u, false, lang))
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));

        String authorityType = authority.getAuthorityType() != null ? 
            authority.getAuthorityType().getAuthorityType() : null;

        return AuthorityDUSResponse.builder()
            .authorityId(authority.getAuthorityId())
            .authorityName(getAuthorityNameByLanguage(authority, lang))
            .authorityCode(authority.getAuthorityCode())
            .authorityType(authorityType)
            .taxaWithExperience(taxaWithExperience)
            .offerings(offeringDetails)
            .utilizations(utilizationDetails)
            .build();
    }

    private ProtectedSpeciesInfo buildProtectedSpeciesInfo(GenieSpeciesProtection protection, String lang) {
        GenieSpecies genie = protection.getGenieSpecies();
        
        String status = "";
        String protectionType = "";
        boolean isDerived = false;

        if (protection.getProtectionId() != null && protection.getDerivation() != null) {
            // Convert Integer to Long
            protectionType = getProtectionTypeByLanguage(Long.valueOf(protection.getProtectionId()), lang);
            
            if ("Y".equals(protection.getDerivation().getDerivationIndicator())) {
                isDerived = true;
                String derivationName = getDerivationNameByLanguage(protection.getDerivationId(), lang);
                status = protectionType + " (" + derivationName + ")";
            } else {
                status = protectionType;
            }
        }

        List<String> commonNames = genieSpeciesNameRepository.findByGenieIdOrdered(genie.getGenieId()).stream()
            .filter(n -> n.getLanguageId() == getLanguageId(lang))
            .map(GenieSpeciesName::getGenieName)
            .collect(Collectors.toList());

        return ProtectedSpeciesInfo.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .commonNames(commonNames)
            .status(status)
            .protectionType(protectionType)
            .isDerived(isDerived)
            .notes(protection.getNoteString())
            .build();
    }

    private TaxonExperienceInfo buildTaxonExperienceInfo(SpeciesExperience experience, String lang) {
        GenieSpecies genie = experience.getGenieSpecies();
        List<GenieSpeciesName> names = genieSpeciesNameRepository.findByGenieIdOrdered(genie.getGenieId());
        
        boolean isDerived = experience.getDerivation() != null && 
            "Y".equals(experience.getDerivation().getDerivationIndicator());

        return TaxonExperienceInfo.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .englishName(extractCommonName(names, 2))
            .frenchName(extractCommonName(names, 3))
            .germanName(extractCommonName(names, 4))
            .spanishName(extractCommonName(names, 5))
            .isDerived(isDerived)
            .notes(experience.getNoteString())
            .build();
    }

    private OfferingDetailInfo buildOfferingDetailInfo(Offering offering, String lang) {
        GenieSpecies genie = genieSpeciesRepository.findById(offering.getGenieId())
            .orElse(null);
        
        if (genie == null) {
            return null;
        }

        boolean isEo = "Y".equals(offering.getEoDesignation());
        boolean isDerived = offering.getDerivation() != null && 
            "Y".equals(offering.getDerivation().getDerivationIndicator());

        return OfferingDetailInfo.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .offeringString(offering.getOfferingString())
            .isEoDesignation(isEo)
            .isDerived(isDerived)
            .receivingAuthorities(new ArrayList<>())
            .build();
    }

    private UtilizationDetailInfo buildUtilizationDetailInfo(Utilization utilization, boolean isUtilizer, String lang) {
        GenieSpecies genie = genieSpeciesRepository.findById(utilization.getGenieId())
            .orElse(null);
        
        if (genie == null) {
            return null;
        }

        boolean isDerived = utilization.getDerivation() != null && 
            "Y".equals(utilization.getDerivation().getDerivationIndicator());

        String otherAuthorityCode = isUtilizer ? 
            (utilization.getProvidingAuthority() != null ? utilization.getProvidingAuthority().getAuthorityCode() : null) :
            (utilization.getUtilizingAuthority() != null ? utilization.getUtilizingAuthority().getAuthorityCode() : null);

        String otherAuthorityName = isUtilizer ?
            (utilization.getProvidingAuthority() != null ? getAuthorityNameByLanguage(utilization.getProvidingAuthority(), lang) : null) :
            (utilization.getUtilizingAuthority() != null ? getAuthorityNameByLanguage(utilization.getUtilizingAuthority(), lang) : null);

        return UtilizationDetailInfo.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .providingAuthorityCode(otherAuthorityCode)
            .providingAuthorityName(otherAuthorityName)
            .isDerived(isDerived)
            .notes(utilization.getNoteString())
            .build();
    }

    private String extractCommonName(List<GenieSpeciesName> names, int languageId) {
        return names.stream()
            .filter(n -> n.getLanguageId() == languageId)
            .map(GenieSpeciesName::getGenieName)
            .collect(Collectors.joining("; "));
    }

    private String getAuthorityNameByLanguage(UpovAuthority authority, String lang) {
        if (authority.getNames() != null && !authority.getNames().isEmpty()) {
            int languageId = getLanguageId(lang);
            return authority.getNames().stream()
                .filter(n -> n.getLanguageId() == languageId)
                .map(AuthorityName::getAuthorityName)
                .findFirst()
                .orElse(authority.getAuthorityCode());
        }
        return authority.getAuthorityCode();
    }

    private String getDerivationNameByLanguage(Long derivationId, String lang) {
        int languageId = getLanguageId(lang);
        return derivationNameRepository.findByDerivationIdAndLanguageId(derivationId, languageId)
            .map(DerivationName::getDerivationName)
            .orElse("Derived");
    }

    private String getProtectionTypeByLanguage(Long protectionId, String lang) {
        int languageId = getLanguageId(lang);
        return protectionNameRepository.findByProtectionIdAndLanguageId(protectionId, languageId)
            .map(ProtectionName::getProtectionName)
            .orElse("Unknown");
    }

    private String getMemberProtectionNoteByLanguage(String lang) {
        Map<String, String> notes = Map.of(
            "en", "This member of the Union protects the whole or essentially the whole plant kingdom.",
            "fr", "Ce membre de l'Union protège l'ensemble ou essentiellement l'ensemble du règne végétal.",
            "es", "Este miembro de la Unión protege la totalidad o prácticamente la totalidad del reino vegetal.",
            "de", "Dieses Verbandsmitglied schützt das ganze oder praktisch das ganze Pflanzenreich."
        );
        
        return notes.getOrDefault(lang, notes.get("en"));
    }

    private String getListTaxaNoteByLanguage(String lang) {
        Map<String, String> notes = Map.of(
            "en", "List of taxa for which titles of protection may be issued for varieties of the taxon concerned",
            "fr", "Liste des taxons dont les variétés peuvent être protégées",
            "es", "Lista de los taxones de los cuales pueden protegerse las variedades",
            "de", "Liste der Taxa, von denen Sorten geschützt sein können"
        );
        
        return notes.getOrDefault(lang, notes.get("en"));
    }

    private String getTTExceptionsByLanguage(String lang) {
        return ", in addition to the following families: Anthuriums; Bromeliaceae; Heliconaceae; Orchidaceae; Sterculiaceae.";
    }

    private String getKRExceptionsByLanguage(String lang) {
        return "According to Ordinance N° 2009-28 of May 1, 2009, the protection provided by the Law on Seed Industry extends to all genera and species, except specific exceptions for Blueberry, Cherry, Mandarin, Raspberry, Strawberry, and Seaweed species.";
    }

    private int getLanguageId(String lang) {
        Map<String, Integer> langIds = Map.of(
            "en", 2,
            "fr", 3,
            "de", 4,
            "es", 5
        );
        return langIds.getOrDefault(lang, 2);
    }
}