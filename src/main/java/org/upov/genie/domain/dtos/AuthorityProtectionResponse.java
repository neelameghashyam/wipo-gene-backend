package org.upov.genie.domain.dtos;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityProtectionResponse {
    private Long authorityId;
    private String authorityName;
    private String authorityCode;
    private String authorityType;
    private String administrativeWebsite;
    private String lawWebsite;
    private String protectionNote;
    private boolean protectsAllSpecies;
    private List<ProtectedSpeciesInfo> protectedSpecies;
}