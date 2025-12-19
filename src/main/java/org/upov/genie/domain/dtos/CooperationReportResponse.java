package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class CooperationReportResponse {
    private String upovCode;
    private TaxonNamesInfo names;
    private List<PracticalExperienceInfo> experiences;
    private List<CooperationOfferingInfo> offerings;
    private List<ReportUtilizationInfo> utilizations;
}
