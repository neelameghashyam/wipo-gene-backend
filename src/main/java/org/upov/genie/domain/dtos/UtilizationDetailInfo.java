package org.upov.genie.domain.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilizationDetailInfo {
    private Long genieId;
    private String upovCode;
    private String botanicalName;
    private String providingAuthorityCode;
    private String providingAuthorityName;
    private boolean isDerived;
    private String notes;
}