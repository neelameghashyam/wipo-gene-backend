package org.upov.genie.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.upov.genie.domain.dtos.AuthorityDTO;
import org.upov.genie.domain.entities.Authority;
import org.upov.genie.domain.entities.AuthorityTypes;
import org.upov.genie.domain.entities.UpovAuthority;
import org.upov.genie.repositories.AuthorityTypesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorityMapper {

    private final AuthorityTypesRepository authorityTypesRepository;

    /**
     * Map Authority entity to AuthorityDTO
     * FIXED: Now properly fetches authorityType from AUTHORITY_TYPES table
     */
    public AuthorityDTO toDTO(Authority authority) {
        if (authority == null) {
            return null;
        }

        // Fetch authority types from junction table
        String authorityType = null;
        if (authority.getAuthorityId() != null) {
            List<AuthorityTypes> types = authorityTypesRepository
                .findByAuthorityIdWithType(authority.getAuthorityId());
            authorityType = types.stream()
                    .filter(at -> at.getAuthorityType() != null)
                    .map(at -> at.getAuthorityType().getAuthorityType())
                    .collect(Collectors.joining(", "));
        }

        return AuthorityDTO.builder()
                .authorityId(authority.getAuthorityId())
                .name(authority.getAuthorityName())
                .isoCode(authority.getAuthorityCode())
                .authorityType(authorityType != null && !authorityType.isEmpty() ? authorityType : null)
                .administrativeWebsite(authority.getAdministrativeWebAddress())
                .lawWebsite(authority.getLawWebAddress())
                .build();
    }
    
    /**
     * Map UpovAuthority entity to AuthorityDTO
     * FIXED: Now properly fetches authorityType from AUTHORITY_TYPES table
     */
    public AuthorityDTO toDTO(UpovAuthority authority) {
        if (authority == null) {
            return null;
        }

        // Fetch authority types from junction table
        String authorityType = null;
        if (authority.getAuthorityId() != null) {
            List<AuthorityTypes> types = authorityTypesRepository
                .findByAuthorityIdWithType(authority.getAuthorityId());
            authorityType = types.stream()
                    .filter(at -> at.getAuthorityType() != null)
                    .map(at -> at.getAuthorityType().getAuthorityType())
                    .collect(Collectors.joining(", "));
        }

        return AuthorityDTO.builder()
                .authorityId(authority.getAuthorityId())
                .name(authority.getAuthorityName())
                .isoCode(authority.getAuthorityCode())
                .authorityType(authorityType != null && !authorityType.isEmpty() ? authorityType : null)
                .administrativeWebsite(authority.getAdministrativeWebAddress())
                .lawWebsite(authority.getLawWebAddress())
                .build();
    }
}