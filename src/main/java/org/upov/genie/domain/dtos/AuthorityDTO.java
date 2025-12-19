package org.upov.genie.domain.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthorityDTO {
    private Long authorityId;
    private String name;
    private String isoCode;
    private String authorityType;
    private String administrativeWebsite;
    private String lawWebsite;
}