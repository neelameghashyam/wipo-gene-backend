package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ReportUtilizationInfo {
    private Long utilizingAuthorityId;
    private Long providingAuthorityId;
    
    private String utilizingAuthority;
    private String providingAuthority;
    private boolean isDerived;
    private String noteSequence;
    
    private String utilizingAuthorityName;
    private String utilizingAdministrativeWebsite;
    private String utilizingLawWebsite;
    
    private String providingAuthorityName;
    private String providingAdministrativeWebsite;
    private String providingLawWebsite;
}