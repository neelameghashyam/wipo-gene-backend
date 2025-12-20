package org.upov.genie.domain.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingAuthorityInfo {
    private Long authorityId;
    private String authorityName;
    private String authorityCode;
}