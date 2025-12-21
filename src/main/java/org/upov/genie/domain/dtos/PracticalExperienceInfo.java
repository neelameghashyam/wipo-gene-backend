package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class PracticalExperienceInfo {
    private String authorityCode;
    private boolean isDerived;
    private String noteSequence;
    private String authorityName;
    private String administrativeWebsite;
    private String lawWebsite;
}