package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class DUSCooperationInfo {
    private String testGuideline;
    private String draftingAuthority;
    private List<PracticalExperienceInfo> practicalExperience;
    private List<CooperationOfferingInfo> offerings;
    private List<ReportUtilizationInfo> utilizations;
}

