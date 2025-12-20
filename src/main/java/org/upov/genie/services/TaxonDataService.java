package org.upov.genie.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upov.genie.domain.dtos.*;
import org.upov.genie.domain.entities.*;
import org.upov.genie.repositories.*;
import org.upov.genie.mappers.TaxonResponseMapper;
import org.upov.genie.utils.GenieApiException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaxonDataService {

    private final GenieSpeciesRepository genieSpeciesRepository;
    private final GenieSpeciesNameRepository genieSpeciesNameRepository;
    private final GenieSpeciesProtectionRepository protectionRepository;
    private final SpeciesExperienceRepository experienceRepository;
    private final SpeciesOfferingRepository speciesOfferingRepository;
    private final SpeciesUtilizationRepository speciesUtilizationRepository;
    private final GenieFamilyRepository genieFamilyRepository;
    private final GenieTWPRepository genieTWPRepository;
    private final GenieDenominationRepository genieDenominationRepository;
    private final GenieGuidelineRepository genieGuidelineRepository;
    private final OfferingRepository offeringRepository;
    private final UtilizationRepository utilizationRepository;
    private final ProtectionNameRepository protectionNameRepository;
    private final DerivationNameRepository derivationNameRepository;
    private final TaxonResponseMapper taxonMapper;

    public TaxonDetailsResponse getSpeciesDetails(Long genieId, String lang) {
        log.debug("Fetching details for species ID: {} in language: {}", genieId, lang);

        GenieSpecies genie = genieSpeciesRepository.findById(genieId)
            .orElseThrow(() -> new GenieApiException("Species not found with ID: " + genieId));

        List<GenieSpeciesName> names = genieSpeciesNameRepository.findByGenieIdOrdered(genieId);
        List<GenieSpeciesProtection> protections = protectionRepository.findByGenieIdWithDetails(genieId);
        List<SpeciesExperience> experiences = experienceRepository.findByGenieIdWithDetails(genieId);
        List<SpeciesOffering> offerings = speciesOfferingRepository.findByGenieIdForCooperation(genieId);
        List<SpeciesUtilization> utilizations = speciesUtilizationRepository.findByGenieIdForCooperation(genieId);
        List<GenieFamily> families = genieFamilyRepository.findByGenieIdWithFamily(genieId);
        List<GenieTWP> twps = genieTWPRepository.findByGenieIdWithTWP(genieId);
        List<GenieDenomination> denominations = genieDenominationRepository.findByGenieIdWithDenomination(genieId);

        return taxonMapper.toDetailsResponse(
            genie, names, protections, experiences, offerings, utilizations, 
            families, twps, denominations, lang
        );
    }

    public SpeciesProtectionInfo getSpeciesProtection(Long genieId, String lang) {
        log.debug("Fetching protection info for species ID: {} in language: {}", genieId, lang);

        GenieSpecies genie = genieSpeciesRepository.findById(genieId)
            .orElseThrow(() -> new GenieApiException("Species not found with ID: " + genieId));

        List<GenieSpeciesName> names = genieSpeciesNameRepository.findByGenieIdOrdered(genieId);
        List<GenieSpeciesProtection> protections = protectionRepository.findByGenieIdWithDetails(genieId);
        List<GenieFamily> families = genieFamilyRepository.findByGenieIdWithFamily(genieId);

        return buildSpeciesProtectionInfo(genie, names, protections, families, lang);
    }

    public SpeciesDUSInfo getSpeciesDUS(Long genieId, String lang) {
        log.debug("Fetching DUS info for species ID: {} in language: {}", genieId, lang);

        GenieSpecies genie = genieSpeciesRepository.findById(genieId)
            .orElseThrow(() -> new GenieApiException("Species not found with ID: " + genieId));

        List<GenieSpeciesName> names = genieSpeciesNameRepository.findByGenieIdOrdered(genieId);
        List<GenieGuideline> guidelines = genieGuidelineRepository.findByGenieIdWithDetails(genieId);
        List<GenieGuideline> childGuidelines = genieGuidelineRepository.findChildGuidelinesByGenieId(genieId);
        List<SpeciesExperience> experiences = experienceRepository.findByGenieIdWithDetails(genieId);
        List<Offering> offerings = offeringRepository.findByGenieIdWithDetails(genieId);
        List<Utilization> utilizations = utilizationRepository.findByGenieIdWithDetails(genieId);

        return buildSpeciesDUSInfo(genie, names, guidelines, childGuidelines, experiences, offerings, utilizations, lang);
    }

    public TaxonSearchEnhancedResponse getAllSpecies(int page, int pageSize) {
        log.debug("Fetching all active species - page: {}, size: {}", page, pageSize);

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<GenieSpecies> speciesPage = genieSpeciesRepository.findAllActiveOrderedByDate(pageable);

        List<TaxonListItemEnhanced> speciesList = speciesPage.getContent().stream()
            .map(genie -> {
                List<GenieFamily> families = genieFamilyRepository.findByGenieIdWithFamily(genie.getGenieId());
                List<GenieTWP> twps = genieTWPRepository.findByGenieIdWithTWP(genie.getGenieId());
                return taxonMapper.toListItemEnhanced(genie, families, twps);
            })
            .collect(Collectors.toList());

        return TaxonSearchEnhancedResponse.builder()
            .totalCount((int) speciesPage.getTotalElements())
            .page(page)
            .pageSize(pageSize)
            .species(speciesList)
            .build();
    }

    public List<TaxonListItemEnhanced> searchSpecies(String searchTerm, int page, int pageSize) {
        log.debug("Searching species with term: {}, page: {}, size: {}", searchTerm, page, pageSize);

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return List.of();
        }

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<GenieSpecies> results = genieSpeciesRepository.searchSpeciesWithDetails(
            searchTerm.trim(), pageable
        );

        return results.getContent().stream()
            .map(genie -> {
                List<GenieFamily> families = genieFamilyRepository.findByGenieIdWithFamily(genie.getGenieId());
                List<GenieTWP> twps = genieTWPRepository.findByGenieIdWithTWP(genie.getGenieId());
                return taxonMapper.toListItemEnhanced(genie, families, twps);
            })
            .collect(Collectors.toList());
    }

    public List<ProtectionReportResponse> getProtectionReport() {
        log.debug("Generating protection report");
        
        List<GenieSpecies> allSpecies = genieSpeciesRepository.findAll().stream()
            .filter(g -> "Y".equals(g.getCodeActive()))
            .collect(Collectors.toList());

        return allSpecies.stream()
            .map(genie -> {
                List<GenieSpeciesProtection> protections = 
                    protectionRepository.findByGenieIdWithDetails(genie.getGenieId());
                List<GenieSpeciesName> names = 
                    genieSpeciesNameRepository.findByGenieIdOrdered(genie.getGenieId());
                
                return taxonMapper.toProtectionReportResponse(genie, names, protections);
            })
            .filter(dto -> !dto.getProtectingAuthorities().isEmpty())
            .collect(Collectors.toList());
    }

    public List<CooperationReportResponse> getCooperationReport(boolean includeDerived) {
        log.debug("Generating cooperation report, includeDerived: {}", includeDerived);
        
        List<GenieSpecies> speciesWithExperience = 
            experienceRepository.findSpeciesWithExperience();

        return speciesWithExperience.stream()
            .map(genie -> {
                List<GenieSpeciesName> names = 
                    genieSpeciesNameRepository.findByGenieIdOrdered(genie.getGenieId());
                List<SpeciesExperience> experiences = 
                    experienceRepository.findByGenieIdWithDetails(genie.getGenieId());
                List<SpeciesOffering> offerings = 
                    speciesOfferingRepository.findByGenieIdForCooperation(genie.getGenieId());
                List<SpeciesUtilization> utilizations = 
                    speciesUtilizationRepository.findByGenieIdForCooperation(genie.getGenieId());

                return taxonMapper.toCooperationReportResponse(
                    genie, names, experiences, offerings, utilizations, includeDerived
                );
            })
            .collect(Collectors.toList());
    }

    private SpeciesProtectionInfo buildSpeciesProtectionInfo(
            GenieSpecies genie,
            List<GenieSpeciesName> names,
            List<GenieSpeciesProtection> protections,
            List<GenieFamily> families,
            String lang) {

        List<String> otherNames = extractOtherBotanicalNames(names, genie.getGenieName());
        List<String> commonNamesEn = extractCommonNames(names, 2);

        Long categoryId = null;
        if (!families.isEmpty() && families.get(0).getFamily() != null) {
            categoryId = families.get(0).getFamily().getCategoryId();
        }

        String entryNote = getEntryNoteByLanguage(lang);
        String memberUnionNote = null;
        
        if (categoryId != null && (categoryId == 2L || categoryId == 3L)) {
            boolean hasMemberUnionNote = protections.stream()
                .anyMatch(p -> p.getNoteId() != null && p.getNoteId() == 45L);
            
            if (hasMemberUnionNote) {
                memberUnionNote = getMemberUnionNoteByLanguage(lang);
            }
        }

        List<ProtectionAuthorityInfo> protectingAuthorities = protections.stream()
            .filter(p -> p.getNoteId() == null || p.getNoteId() != 45L)
            .filter(p -> p.getAuthority() != null)
            .map(p -> buildProtectionAuthorityInfo(p, lang))
            .collect(Collectors.toList());

        return SpeciesProtectionInfo.builder()
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .otherBotanicalNames(otherNames)
            .commonNamesEnglish(commonNamesEn)
            .entryNote(entryNote)
            .memberUnionNote(memberUnionNote)
            .protectingAuthorities(protectingAuthorities)
            .build();
    }

    private ProtectionAuthorityInfo buildProtectionAuthorityInfo(
            GenieSpeciesProtection protection, String lang) {
        
        UpovAuthority authority = protection.getAuthority();
        
        String status = "";
        String protectionType = "";
        boolean isDerived = false;

        if (protection.getProtectionId() != null && protection.getDerivation() != null) {
            // Convert Integer to Long for method call
            protectionType = getProtectionTypeByLanguage(Long.valueOf(protection.getProtectionId()), lang);
            
            if ("Y".equals(protection.getDerivation().getDerivationIndicator())) {
                isDerived = true;
                String derivationName = getDerivationNameByLanguage(protection.getDerivationId(), lang);
                status = protectionType + " (" + derivationName + ")";
            } else {
                status = protectionType;
            }
        }

        return ProtectionAuthorityInfo.builder()
            .authorityId(authority.getAuthorityId())
            .authorityName(getAuthorityNameByLanguage(authority, lang))
            .authorityCode(authority.getAuthorityCode())
            .status(status)
            .protectionType(protectionType)
            .isDerived(isDerived)
            .notes(protection.getNoteString())
            .build();
    }

    private SpeciesDUSInfo buildSpeciesDUSInfo(
            GenieSpecies genie,
            List<GenieSpeciesName> names,
            List<GenieGuideline> guidelines,
            List<GenieGuideline> childGuidelines,
            List<SpeciesExperience> experiences,
            List<Offering> offerings,
            List<Utilization> utilizations,
            String lang) {

        List<String> otherNames = extractOtherBotanicalNames(names, genie.getGenieName());
        List<String> commonNamesEn = extractCommonNames(names, 2);

        List<TestGuidelineInfo> testGuidelines = new ArrayList<>();
        
        for (GenieGuideline gg : guidelines) {
            if (gg.getGuideline() != null) {
                testGuidelines.add(buildTestGuidelineInfo(gg, false, lang));
            }
        }
        
        for (GenieGuideline gg : childGuidelines) {
            if (gg.getGuideline() != null) {
                testGuidelines.add(buildTestGuidelineInfo(gg, true, lang));
            }
        }

        List<PracticalExperienceInfo> authoritiesWithExperience = experiences.stream()
            .filter(e -> e.getAuthority() != null)
            .map(e -> buildPracticalExperienceInfo(e, lang))
            .collect(Collectors.toList());

        List<CooperationOfferingInfo> cooperationOfferings = offerings.stream()
            .filter(o -> o.getAuthority() != null)
            .map(o -> buildCooperationOfferingInfo(o, lang))
            .collect(Collectors.toList());

        List<UtilizationInfo> utilizationInfos = utilizations.stream()
            .filter(u -> u.getUtilizingAuthority() != null && u.getProvidingAuthority() != null)
            .map(u -> buildUtilizationInfo(u, lang))
            .collect(Collectors.toList());

        return SpeciesDUSInfo.builder()
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .otherBotanicalNames(otherNames)
            .commonNamesEnglish(commonNamesEn)
            .testGuidelines(testGuidelines)
            .draftingAuthority("None")
            .authoritiesWithExperience(authoritiesWithExperience)
            .offerings(cooperationOfferings)
            .utilizations(utilizationInfos)
            .build();
    }

    private TestGuidelineInfo buildTestGuidelineInfo(GenieGuideline genieGuideline, boolean isChild, String lang) {
        Guideline guideline = genieGuideline.getGuideline();
        String code = guideline.getGuidelineCode();
        String name = guideline.getGuidelineName();
        String guidelineLink = buildGuidelineLink(code, lang);
        
        return TestGuidelineInfo.builder()
            .guidelineCode(code)
            .guidelineName(name)
            .guidelineLink(guidelineLink)
            .isChildGuideline(isChild)
            .build();
    }

    private String buildGuidelineLink(String code, String lang) {
        Pattern pattern = Pattern.compile("TG/(\\d+)/");
        Matcher matcher = pattern.matcher(code);
        
        if (matcher.find()) {
            String number = String.format("%03d", Integer.parseInt(matcher.group(1)));
            return String.format("https://www.upov.int/documents/d/upov/tg-documents-%s-tg%s.pdf", 
                lang, number);
        }
        
        return "";
    }

    private PracticalExperienceInfo buildPracticalExperienceInfo(
            SpeciesExperience experience, String lang) {
        
        boolean isDerived = experience.getDerivation() != null && 
            "Y".equals(experience.getDerivation().getDerivationIndicator());
        
        return PracticalExperienceInfo.builder()
            .authorityId(experience.getAuthority().getAuthorityId())
            .authorityName(getAuthorityNameByLanguage(experience.getAuthority(), lang))
            .authorityCode(experience.getAuthority().getAuthorityCode())
            .isDerived(isDerived)
            .isChildExperience(false)
            .notes(experience.getNoteString())
            .build();
    }

    private CooperationOfferingInfo buildCooperationOfferingInfo(Offering offering, String lang) {
        boolean isEo = "Y".equals(offering.getEoDesignation());
        boolean isDerived = offering.getDerivation() != null && 
            "Y".equals(offering.getDerivation().getDerivationIndicator());
        
        return CooperationOfferingInfo.builder()
            .offeringAuthorityId(offering.getAuthority().getAuthorityId())
            .offeringAuthorityName(getAuthorityNameByLanguage(offering.getAuthority(), lang))
            .offeringAuthorityCode(offering.getAuthority().getAuthorityCode())
            .receivingAuthorities(new ArrayList<>())
            .isEoDesignation(isEo)
            .isDerived(isDerived)
            .offeringString(offering.getOfferingString())
            .notes(offering.getNoteString())
            .build();
    }

    private UtilizationInfo buildUtilizationInfo(Utilization utilization, String lang) {
        boolean isDerived = utilization.getDerivation() != null && 
            "Y".equals(utilization.getDerivation().getDerivationIndicator());
        
        return UtilizationInfo.builder()
            .utilizingAuthorityId(utilization.getUtilizingAuthority().getAuthorityId())
            .utilizingAuthorityName(getAuthorityNameByLanguage(utilization.getUtilizingAuthority(), lang))
            .utilizingAuthorityCode(utilization.getUtilizingAuthority().getAuthorityCode())
            .providingAuthorityId(utilization.getProvidingAuthority().getAuthorityId())
            .providingAuthorityName(getAuthorityNameByLanguage(utilization.getProvidingAuthority(), lang))
            .providingAuthorityCode(utilization.getProvidingAuthority().getAuthorityCode())
            .isDerived(isDerived)
            .notes(utilization.getNoteString())
            .build();
    }

    private List<String> extractOtherBotanicalNames(List<GenieSpeciesName> names, String mainName) {
        return names.stream()
            .filter(n -> !"Y".equals(n.getDefaultName()))
            .filter(n -> !mainName.equals(n.getGenieName()))
            .map(GenieSpeciesName::getGenieName)
            .distinct()
            .collect(Collectors.toList());
    }

    private List<String> extractCommonNames(List<GenieSpeciesName> names, int languageId) {
        return names.stream()
            .filter(n -> n.getLanguageId() == languageId)
            .map(GenieSpeciesName::getGenieName)
            .collect(Collectors.toList());
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

    private String getEntryNoteByLanguage(String lang) {
        Map<String, String> notes = Map.of(
            "en", "The entry of data in the GENIE database has led to some variations from the taxonomic terms used in relevant laws and regulations, in particular because the nomenclatures used are not always universally harmonized. It is recommended to consult the relevant laws and regulations when precise information is needed.",
            "fr", "L'entrée des données dans la base de données GENIE a conduit à certaines dérivations des termes taxonomiques employés dans les lois et règlements correspondants, notamment parce que les nomenclatures utilisées ne sont pas toujours harmonisées universellement. On consultera les lois et règlements correspondants lorsque des informations précises sont nécessaires.",
            "es", "La entrada de los datos en la base de datos GENIE ha ocasionado algunas desviaciones de los términos taxonómicos empleados en leyes y regulaciones pertinentes, concretamente debido a que las nomenclaturas empleadas no están siempre universalmente armonizadas. Cuando se necesite información exacta se recomienda consultar las leyes y regulaciones pertinentes.",
            "de", "Der Eintrag von Daten in die GENIE-Datenbank hatte Abweichungen von den in den entsprechenden Gesetzen und Verordnungen benutzten taxonomischen Begriffen zur Folge, insbesondere deshalb, weil die benutzten Nomenklaturen nicht immer einheitlich harmonisiert sind. Es wird empfohlen, die entsprechenden Gesetze und Verordnungen einzusehen, wenn genaue Informationen benötigt werden."
        );
        
        return notes.getOrDefault(lang, notes.get("en"));
    }

    private String getMemberUnionNoteByLanguage(String lang) {
        Map<String, String> notes = Map.of(
            "en", "For members of the Union which protect the whole plant kingdom, but which are not listed below, it is necessary to check with the relevant member(s) of the Union concerned to know whether protection is offered for this genus/species.",
            "fr", "Pour les membres de l'Union qui protègent l'ensemble du règne végétal, mais qui ne figurent pas dans la liste ci-dessous, il est nécessaire de vérifier auprès du (des) membre(s) de l'Union pertinent(s) concerné(s) si la protection est offerte pour ce genre/espèce.",
            "es", "Para los miembros que protegen la totalidad del reino vegetal, pero que no están enumerados debajo, es necesario comprobar con el (los) miembro(s) de la Unión pertinente(s) pertinente(s) para saber si se ofrece la protección respecto a este género/especie.",
            "de", "Verbandsmitglieder, die das ganze Pflanzenreich schützen, die unten jedoch nicht aufgeführt sind, müssen bei dem (den) betreffenden entsprechenden Verbandsmitglied(ern) nachprüfen, ob diese Gattung/Sorte geschützt ist."
        );
        
        return notes.getOrDefault(lang, notes.get("en"));
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