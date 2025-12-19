package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ReportUtilizationInfo {
    private String utilizingAuthority;
    private String providingAuthority;
    private boolean isDerived;
    private String noteSequence;
}

