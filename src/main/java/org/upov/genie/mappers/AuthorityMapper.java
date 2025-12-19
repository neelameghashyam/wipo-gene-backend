package org.upov.genie.mappers;

import org.springframework.stereotype.Component;
import org.upov.genie.domain.dtos.AuthorityDTO;
import org.upov.genie.domain.entities.Authority;

@Component
public class AuthorityMapper {

    public AuthorityDTO toDTO(Authority authority) {
        if (authority == null) {
            return null;
        }

        return AuthorityDTO.builder()
                .authorityId(authority.getAuthorityId())
                .name(authority.getAuthorityName())
                .isoCode(authority.getAuthorityCode())
                .authorityType(authority.getAuthorityType())
                .administrativeWebsite(authority.getAdministrativeWebAddress())
                .lawWebsite(authority.getLawWebAddress())
                .build();
    }
}