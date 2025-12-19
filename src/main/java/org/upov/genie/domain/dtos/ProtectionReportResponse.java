package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class ProtectionReportResponse {
    private String upovCode;
    private TaxonNamesInfo names;
    private List<String> protectingAuthorities;
}

