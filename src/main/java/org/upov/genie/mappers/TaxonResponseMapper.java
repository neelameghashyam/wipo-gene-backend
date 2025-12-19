package org.upov.genie.mappers;

import org.springframework.stereotype.Component;
import org.upov.genie.domain.dtos.*;
import org.upov.genie.domain.entities.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TaxonResponseMapper {
    
    private static final Map<Integer, String> LANGUAGE_CODES = Map.of(
        1, "en", 2, "fr", 3, "fr", 4, "de", 5, "es"
    );
    
    public TaxonDetailsResponse toDetailsResponse(
        GenieSpecies genie, List<GenieSpeciesName> names,
        List<GenieSpeciesProtection> protections, List<SpeciesExperience> experiences,
        List<SpeciesOffering> offerings, List<SpeciesUtilization> utilizations, String lang) {
        
        return TaxonDetailsResponse.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .family(genie.getGenusCode())
            .cropType("Type" + genie.getCheckingTypeId())
            .twp(genie.getTwpString())
            .denomClass(genie.getGenusCode())
            .testGuideline("")
            .names(mapNames(names))
            .protection(mapProtections(protections))
            .dusGuidance(mapDUSGuidance(experiences, offerings, utilizations))
            .build();
    }
    
    private TaxonNamesInfo mapNames(List<GenieSpeciesName> names) {
        if (names == null) return TaxonNamesInfo.builder()
            .defaultName("").commonNames(new HashMap<>()).build();
        
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
            commonNames.put(lang, String.join("; ", nameList)));
        
        return TaxonNamesInfo.builder()
            .defaultName(defaultName)
            .commonNames(commonNames)
            .build();
    }
    
    private List<AuthorityProtectionInfo> mapProtections(List<GenieSpeciesProtection> protections) {
        if (protections == null) return new ArrayList<>();
        return protections.stream()
            .filter(p -> p.getAuthority() != null && p.getDerivation() != null)
            .map(p -> AuthorityProtectionInfo.builder()
                .authorityCode(p.getAuthority().getAuthorityCode())
                .protectionType(getProtectionType(p.getProtectionId()))
                .isDerived("Y".equals(p.getDerivation().getDerivationIndicator()))
                .notes(p.getNoteString())
                .build())
            .collect(Collectors.toList());
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
        return experiences.stream()
            .filter(e -> e.getAuthority() != null && e.getDerivation() != null)
            .map(e -> PracticalExperienceInfo.builder()
                .authorityCode(e.getAuthority().getAuthorityCode())
                .isDerived("Y".equals(e.getDerivation().getDerivationIndicator()))
                .noteSequence(e.getNoteString())
                .build())
            .collect(Collectors.toList());
    }
    
    private List<CooperationOfferingInfo> mapOfferings(List<SpeciesOffering> offerings) {
        if (offerings == null) return new ArrayList<>();
        return offerings.stream()
            .filter(o -> o.getAuthority() != null)
            .map(o -> CooperationOfferingInfo.builder()
                .authorityCode(o.getAuthority().getAuthorityCode())
                .offeringString(o.getOfferingString())
                .isEoDesignation("Y".equals(o.getEoDesignation()))
                .isDerived("Y".equals(o.getDerivation().getDerivationIndicator()))
                .build())
            .collect(Collectors.toList());
    }
    
    private List<ReportUtilizationInfo> mapUtilizations(List<SpeciesUtilization> utilizations) {
        if (utilizations == null) return new ArrayList<>();
        return utilizations.stream()
            .filter(u -> u.getUtilizingAuthority() != null && u.getProvidingAuthority() != null)
            .map(u -> ReportUtilizationInfo.builder()
                .utilizingAuthority(u.getUtilizingAuthority().getAuthorityCode())
                .providingAuthority(u.getProvidingAuthority().getAuthorityCode())
                .isDerived("Y".equals(u.getDerivation().getDerivationIndicator()))
                .noteSequence(u.getNoteString())
                .build())
            .collect(Collectors.toList());
    }
    
    public TaxonListItem toListItem(GenieSpecies genie) {
        String defaultName = "";
        if (genie.getNames() != null) {
            defaultName = genie.getNames().stream()
                .filter(n -> n.getLanguageId() == 1 && "Y".equals(n.getDefaultName()))
                .map(GenieSpeciesName::getGenieName)
                .collect(Collectors.joining("; "));
        }
        
        return TaxonListItem.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .defaultName(defaultName)
            .build();
    }
    
    private String getProtectionType(Integer protectionId) {
        return switch (protectionId) {
            case 2 -> "Selected species";
            case 5 -> "All species";
            case 3 -> "Families (all)";
            case 7 -> "Families";
            default -> "Unknown";
        };
    }
    
    public ProtectionReportResponse toProtectionReportResponse(
        GenieSpecies genie, List<GenieSpeciesName> names,
        List<GenieSpeciesProtection> protections) {
        
        List<String> authorities = protections != null ? protections.stream()
            .filter(p -> p.getDerivation() != null && p.getAuthority() != null)
            .map(p -> p.getAuthority().getAuthorityCode())
            .distinct()
            .collect(Collectors.toList()) : new ArrayList<>();
        
        return ProtectionReportResponse.builder()
            .upovCode(genie.getUpovCode())
            .names(mapNames(names))
            .protectingAuthorities(authorities)
            .build();
    }
    
    public CooperationReportResponse toCooperationReportResponse(
        GenieSpecies genie, List<GenieSpeciesName> names,
        List<SpeciesExperience> experiences, List<SpeciesOffering> offerings,
        List<SpeciesUtilization> utilizations, boolean includeDerived) {
        
        return CooperationReportResponse.builder()
            .upovCode(genie.getUpovCode())
            .names(mapNames(names))
            .experiences(mapExperiences(experiences))
            .offerings(mapOfferings(offerings))
            .utilizations(mapUtilizations(utilizations))
            .build();
    }
}
