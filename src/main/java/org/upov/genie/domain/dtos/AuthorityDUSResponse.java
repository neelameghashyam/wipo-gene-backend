package org.upov.genie.domain.dtos;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDUSResponse {
    private Long authorityId;
    private String authorityName;
    private String authorityCode;
    private String authorityType;
    private List<TaxonExperienceInfo> taxaWithExperience;
    private List<OfferingDetailInfo> offerings;
    private List<UtilizationDetailInfo> utilizations;
}