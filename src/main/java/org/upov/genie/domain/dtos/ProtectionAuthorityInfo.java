package org.upov.genie.domain.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProtectionAuthorityInfo {
    private Long authorityId;
    private String authorityName;
    private String authorityCode;
    private String status;
    private String protectionType;
    private boolean isDerived;
    private String notes;
}