package org.upov.genie.mappers;

import org.springframework.stereotype.Component;
import org.upov.genie.domain.dtos.*;
import org.upov.genie.domain.entities.*;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TaxonResponseMapper {

    private static final Map<Integer, String> LANGUAGE_CODES = Map.of(
        1, "en",
        2, "en", 
        3, "fr",
        4, "de",
        5, "es"
    );

    private static final Map<String, String> TWP_CROP_TYPE_MAP = Map.of(
        "TWA", "Agriculture",
        "TWF", "Fruit",
        "TWO", "Ornamental",
        "TWO-T", "Forest tree",
        "TWO-O", "Ornamental",
        "TWV", "Vegetable"
    );

    public TaxonListItemEnhanced toListItemEnhanced(
            GenieSpecies genie, 
            List<GenieFamily> families, 
            List<GenieTWP> twps) {
        
        String defaultName = "";
        if (genie.getNames() != null) {
            defaultName = genie.getNames().stream()
                .filter(n -> n.getLanguageId() == 1 && "Y".equals(n.getDefaultName()))
                .map(GenieSpeciesName::getGenieName)
                .collect(Collectors.joining("; "));
        }

        String family = "";
        if (families != null && !families.isEmpty()) {
            family = families.stream()
                .filter(gf -> gf.getFamily() != null)
                .map(gf -> gf.getFamily().getFamilyName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
        }

        String cropType = "";
        if (twps != null && !twps.isEmpty()) {
            Set<String> cropTypes = new LinkedHashSet<>();
            for (GenieTWP gt : twps) {
                if (gt.getTwp() != null && gt.getTwp().getTwpCode() != null) {
                    String twpCode = gt.getTwp().getTwpCode();
                    String mappedType = TWP_CROP_TYPE_MAP.get(twpCode);
                    if (mappedType != null) {
                        cropTypes.add(mappedType);
                    }
                }
            }
            cropType = String.join(", ", cropTypes);
        }

        List<String> authorityNames = new ArrayList<>();
        List<String> authorityIsoCodes = new ArrayList<>();
        
        if (genie.getProtections() != null) {
            for (GenieSpeciesProtection p : genie.getProtections()) {
                if (p.getAuthority() != null) {
                    UpovAuthority auth = p.getAuthority();
                    if (auth.getNames() != null && !auth.getNames().isEmpty()) {
                        String name = auth.getNames().stream()
                            .findFirst()
                            .map(AuthorityName::getAuthorityName)
                            .orElse(auth.getAuthorityCode());
                        if (!authorityNames.contains(name)) {
                            authorityNames.add(name);
                        }
                    }
                    if (auth.getAuthorityCode() != null && !authorityIsoCodes.contains(auth.getAuthorityCode())) {
                        authorityIsoCodes.add(auth.getAuthorityCode());
                    }
                }
            }
        }

        return TaxonListItemEnhanced.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .defaultName(defaultName)
            .family(family)
            .cropType(cropType)
            .updatedDate(genie.getUpdateDate())
            .createdDate(genie.getCreateDate())
            .authorityNames(authorityNames)
            .authorityIsoCodes(authorityIsoCodes)
            .build();
    }

    public TaxonDetailsResponse toDetailsResponse(
            GenieSpecies genie,
            List<GenieSpeciesName> names,
            List<GenieSpeciesProtection> protections,
            List<SpeciesExperience> experiences,
            List<SpeciesOffering> offerings,
            List<SpeciesUtilization> utilizations,
            List<GenieFamily> families,
            List<GenieTWP> twps,
            List<GenieDenomination> denominations,
            String lang) {

        String family = "";
        if (families != null && !families.isEmpty()) {
            family = families.stream()
                .filter(gf -> gf.getFamily() != null)
                .map(gf -> gf.getFamily().getFamilyName())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
        }

        String cropType = "";
        if (twps != null && !twps.isEmpty()) {
            Set<String> cropTypes = new LinkedHashSet<>();
            for (GenieTWP gt : twps) {
                if (gt.getTwp() != null && gt.getTwp().getTwpCode() != null) {
                    String twpCode = gt.getTwp().getTwpCode();
                    String mappedType = TWP_CROP_TYPE_MAP.get(twpCode);
                    if (mappedType != null) {
                        cropTypes.add(mappedType);
                    }
                }
            }
            cropType = String.join(", ", cropTypes);
        }

        String twp = "";
        if (twps != null && !twps.isEmpty()) {
            Set<String> twpCodes = new LinkedHashSet<>();
            for (GenieTWP gt : twps) {
                if (gt.getTwp() != null && gt.getTwp().getTwpCode() != null) {
                    String twpCode = gt.getTwp().getTwpCode();
                    if (twpCode.startsWith("TWO")) {
                        twpCodes.add("TWO");
                    } else {
                        twpCodes.add(twpCode);
                    }
                }
            }
            twp = String.join(", ", twpCodes);
        }

        String otherBotanicalNames = "";
        if (names != null && !names.isEmpty()) {
            otherBotanicalNames = names.stream()
                .filter(n -> !"Y".equals(n.getDefaultName()))
                .filter(n -> !genie.getGenieName().equals(n.getGenieName()))
                .map(GenieSpeciesName::getGenieName)
                .distinct()
                .collect(Collectors.joining("; "));
        }

        String denominationClass = "";
        if (denominations != null && !denominations.isEmpty()) {
            denominationClass = denominations.stream()
                .filter(d -> d.getDenomination() != null)
                .map(d -> d.getDenomination().getDenominationName())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.joining(", "));
        }

        return TaxonDetailsResponse.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .otherBotanicalNames(otherBotanicalNames)
            .denominationClass(denominationClass)
            .family(family)
            .cropType(cropType)
            .twp(twp)
            .denomClass(denominationClass)
            .testGuideline("")
            .names(mapNames(names))
            .protection(mapProtections(protections))
            .dusGuidance(mapDUSGuidance(experiences, offerings, utilizations))
            .build();
    }

    private TaxonNamesInfo mapNames(List<GenieSpeciesName> names) {
        if (names == null) {
            return TaxonNamesInfo.builder()
                .defaultName("")
                .commonNames(new HashMap<>())
                .build();
        }

        String defaultName = "";
        Map<String, String> commonNames = new LinkedHashMap<>();
        Map<String, List<String>> byLang = new HashMap<>();

        for (GenieSpeciesName name : names) {
            if (name.getGenieName() == null) continue;

            String langCode = LANGUAGE_CODES.getOrDefault(name.getLanguageId(), "other");

            if (name.getLanguageId() == 1 && "Y".equals(name.getDefaultName())) {
                if (!defaultName.isEmpty()) defaultName += "; ";
                defaultName += name.getGenieName();
            }

            byLang.computeIfAbsent(langCode, k -> new ArrayList<>()).add(name.getGenieName());
        }

        byLang.forEach((lang, nameList) -> 
            commonNames.put(lang, String.join("; ", nameList))
        );

        return TaxonNamesInfo.builder()
            .defaultName(defaultName)
            .commonNames(commonNames)
            .build();
    }

    private List<AuthorityProtectionInfo> mapProtections(List<GenieSpeciesProtection> protections) {
        if (protections == null) return new ArrayList<>();

        List<AuthorityProtectionInfo> result = new ArrayList<>();
        for (GenieSpeciesProtection p : protections) {
            if (p.getAuthority() != null && p.getDerivation() != null) {
                result.add(AuthorityProtectionInfo.builder()
                    .authorityCode(p.getAuthority().getAuthorityCode())
                    .protectionType(getProtectionType(p.getProtectionId()))
                    .isDerived("Y".equals(p.getDerivation().getDerivationIndicator()))
                    .notes(p.getNoteString())
                    .build());
            }
        }
        return result;
    }

    private DUSCooperationInfo mapDUSGuidance(
            List<SpeciesExperience> experiences,
            List<SpeciesOffering> offerings,
            List<SpeciesUtilization> utilizations) {

        return DUSCooperationInfo.builder()
            .testGuideline("")
            .draftingAuthority("None")
            .practicalExperience(mapExperiences(experiences))
            .offerings(mapOfferings(offerings))
            .utilizations(mapUtilizations(utilizations))
            .build();
    }

    private List<PracticalExperienceInfo> mapExperiences(List<SpeciesExperience> experiences) {
    if (experiences == null) return new ArrayList<>();

    List<PracticalExperienceInfo> result = new ArrayList<>();
    for (SpeciesExperience e : experiences) {
        if (e.getAuthority() != null && e.getDerivation() != null) {
            result.add(PracticalExperienceInfo.builder()
                .authorityCode(e.getAuthority().getAuthorityCode())
                .isDerived("Y".equals(e.getDerivation().getDerivationIndicator()))
                .notes(e.getNoteString())  // CHANGE FROM noteSequence to notes
                .build());
        }
    }
    return result;
}

    private List<CooperationOfferingInfo> mapOfferings(List<SpeciesOffering> offerings) {
        if (offerings == null) return new ArrayList<>();

        List<CooperationOfferingInfo> result = new ArrayList<>();
        for (SpeciesOffering o : offerings) {
            if (o.getAuthority() != null) {
                result.add(CooperationOfferingInfo.builder()
                    .offeringAuthorityCode(o.getAuthority().getAuthorityCode())
                    .offeringString(o.getOfferingString())
                    .isEoDesignation("Y".equals(o.getEoDesignation()))
                    .isDerived(o.getDerivation() != null && "Y".equals(o.getDerivation().getDerivationIndicator()))
                    .build());
            }
        }
        return result;
    }

    private List<ReportUtilizationInfo> mapUtilizations(List<SpeciesUtilization> utilizations) {
        if (utilizations == null) return new ArrayList<>();

        List<ReportUtilizationInfo> result = new ArrayList<>();
        for (SpeciesUtilization u : utilizations) {
            if (u.getUtilizingAuthority() != null && u.getProvidingAuthority() != null) {
                result.add(ReportUtilizationInfo.builder()
                    .utilizingAuthority(u.getUtilizingAuthority().getAuthorityCode())
                    .providingAuthority(u.getProvidingAuthority().getAuthorityCode())
                    .isDerived(u.getDerivation() != null && "Y".equals(u.getDerivation().getDerivationIndicator()))
                    .noteSequence(u.getNoteString())
                    .build());
            }
        }
        return result;
    }

    public ProtectionReportResponse toProtectionReportResponse(
            GenieSpecies genie,
            List<GenieSpeciesName> names,
            List<GenieSpeciesProtection> protections) {

        List<String> authorities = new ArrayList<>();
        if (protections != null) {
            for (GenieSpeciesProtection p : protections) {
                if (p.getDerivation() != null && p.getAuthority() != null) {
                    String code = p.getAuthority().getAuthorityCode();
                    if (!authorities.contains(code)) {
                        authorities.add(code);
                    }
                }
            }
        }

        return ProtectionReportResponse.builder()
            .upovCode(genie.getUpovCode())
            .names(mapNames(names))
            .protectingAuthorities(authorities)
            .build();
    }

    public CooperationReportResponse toCooperationReportResponse(
            GenieSpecies genie,
            List<GenieSpeciesName> names,
            List<SpeciesExperience> experiences,
            List<SpeciesOffering> offerings,
            List<SpeciesUtilization> utilizations,
            boolean includeDerived) {

        return CooperationReportResponse.builder()
            .upovCode(genie.getUpovCode())
            .names(mapNames(names))
            .experiences(mapExperiences(experiences))
            .offerings(mapOfferings(offerings))
            .utilizations(mapUtilizations(utilizations))
            .build();
    }

    private String getProtectionType(Integer protectionId) {
        if (protectionId == null) return "Unknown";
        return switch (protectionId) {
            case 2 -> "Selected species";
            case 5 -> "All species";
            case 3 -> "Families (all)";
            case 7 -> "Families";
            default -> "Unknown";
        };
    }
}