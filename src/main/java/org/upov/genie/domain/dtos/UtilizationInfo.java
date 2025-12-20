package org.upov.genie.domain.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilizationInfo {
    private Long utilizingAuthorityId;
    private String utilizingAuthorityName;
    private String utilizingAuthorityCode;
    private Long providingAuthorityId;
    private String providingAuthorityName;
    private String providingAuthorityCode;
    private boolean isDerived;
    private String notes;
}