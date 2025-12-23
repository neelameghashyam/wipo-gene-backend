package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CooperationOfferingInfo {
    private Long authorityId;
    private String authorityCode;
    private String offeringString;
    private boolean isEoDesignation;
    private boolean isDerived;
    private String authorityName;
    private String administrativeWebsite;
    private String lawWebsite;
}