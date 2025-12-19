package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CooperationOfferingInfo {
    private String authorityCode;
    private String offeringString;
    private boolean isEoDesignation;
    private boolean isDerived;
}
