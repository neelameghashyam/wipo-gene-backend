package org.upov.genie.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.upov.genie.domain.dtos.*;
import org.upov.genie.domain.entities.*;
import org.upov.genie.repositories.GenieSpeciesNameRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorityResponseMapper {

    private final GenieSpeciesNameRepository nameRepository;
    private final TaxonResponseMapper taxonResponseMapper;

    private static final Map<Integer, String> PROTECTION_TYPE_MAP = Map.of(
        2, "Selected species",
        5, "All species",
        3, "Families (all)",
        7, "Families"
    );

    /**
     * Map Authority and its protections to AuthorityProtectionResponse
     */
    public AuthorityProtectionResponse toProtectionResponse(
            Authority authority,
            List<GenieSpeciesProtection> protections) {

        // Determine protection type from authority's PROTECTION_ID
        String protectionType = "Unknown";
        String protectionNotes = "";
        
        if (authority.getProtectionId() != null) {
            protectionType = PROTECTION_TYPE_MAP.getOrDefault(
                authority.getProtectionId(), 
                "Unknown"
            );
            
            // Special handling for "All species"
            if (authority.getProtectionId() == 5) {
                protectionNotes = "This member of the Union protects the whole or essentially the whole plant kingdom.";
            }
        }

        // Map protected taxa
        List<ProtectedTaxonInfo> protectedTaxa = new ArrayList<>();
        
        // Only show detailed list for "Selected species" (protection_id = 2)
        if (authority.getProtectionId() != null && authority.getProtectionId() == 2) {
            protectedTaxa = protections.stream()
                .filter(p -> p.getGenieSpecies() != null)
                .filter(p -> "Y".equals(p.getProtectionActive()))
                .map(this::mapToProtectedTaxon)
                .collect(Collectors.toList());
        }

        return AuthorityProtectionResponse.builder()
            .authorityId(authority.getAuthorityId())
            .authorityName(authority.getAuthorityName())
            .authorityCode(authority.getAuthorityCode())
            .protectionType(protectionType)
            .protectionNotes(protectionNotes)
            .protectedTaxa(protectedTaxa)
            .build();
    }

    /**
     * Map Authority and its DUS experiences to AuthorityDUSResponse
     */
    public AuthorityDUSResponse toDUSResponse(
            Authority authority,
            List<SpeciesExperience> experiences) {

        List<TaxonExperienceInfo> taxaWithExperience = experiences.stream()
            .filter(e -> e.getGenieSpecies() != null)
            .filter(e -> e.getDerivation() != null)
            // Only include direct experience (DERIVATION_ID = 2)
            .filter(e -> e.getDerivation().getDerivationId() == 2)
            .map(this::mapToExperienceInfo)
            .collect(Collectors.toList());

        return AuthorityDUSResponse.builder()
            .authorityId(authority.getAuthorityId())
            .authorityName(authority.getAuthorityName())
            .authorityCode(authority.getAuthorityCode())
            .taxaWithExperience(taxaWithExperience)
            .build();
    }

    /**
     * Map GenieSpeciesProtection to ProtectedTaxonInfo
     */
    private ProtectedTaxonInfo mapToProtectedTaxon(GenieSpeciesProtection protection) {
        GenieSpecies genie = protection.getGenieSpecies();
        
        // Get names for this species
        List<GenieSpeciesName> names = nameRepository.findByGenieIdOrdered(genie.getGenieId());
        TaxonNamesInfo namesInfo = taxonResponseMapper.mapNames(names);

        // Determine protection status
        String status = "Protected";
        boolean isDerived = false;
        
        if (protection.getDerivation() != null) {
            isDerived = "Y".equals(protection.getDerivation().getDerivationIndicator());
            if (isDerived) {
                status = "Protected (Derived)";
            }
        }

        return ProtectedTaxonInfo.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .names(namesInfo)
            .protectionStatus(status)
            .isDerived(isDerived)
            .notes(protection.getNoteString() != null ? protection.getNoteString() : "")
            .build();
    }

    /**
     * Map SpeciesExperience to TaxonExperienceInfo
     */
    private TaxonExperienceInfo mapToExperienceInfo(SpeciesExperience experience) {
        GenieSpecies genie = experience.getGenieSpecies();
        
        // Get names for this species
        List<GenieSpeciesName> names = nameRepository.findByGenieIdOrdered(genie.getGenieId());
        TaxonNamesInfo namesInfo = taxonResponseMapper.mapNames(names);

        boolean isDerived = false;
        if (experience.getDerivation() != null) {
            isDerived = "Y".equals(experience.getDerivation().getDerivationIndicator());
        }

        return TaxonExperienceInfo.builder()
            .genieId(genie.getGenieId())
            .upovCode(genie.getUpovCode())
            .botanicalName(genie.getGenieName())
            .names(namesInfo)
            .isDerived(isDerived)
            .notes(experience.getNoteString() != null ? experience.getNoteString() : "")
            .build();
    }
}