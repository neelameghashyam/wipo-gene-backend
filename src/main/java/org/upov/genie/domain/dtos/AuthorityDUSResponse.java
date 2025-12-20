package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class AuthorityDUSResponse {
    private Long authorityId;
    private String authorityName;
    private String authorityCode;
    private List<TaxonExperienceInfo> taxaWithExperience;
}