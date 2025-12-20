package org.upov.genie.domain.dtos;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CooperationOfferingInfo {
    private Long offeringAuthorityId;
    private String offeringAuthorityName;
    private String offeringAuthorityCode;
    private List<ReceivingAuthorityInfo> receivingAuthorities;
    private boolean isEoDesignation;
    private boolean isDerived;
    private String offeringString;
    private String notes;
}