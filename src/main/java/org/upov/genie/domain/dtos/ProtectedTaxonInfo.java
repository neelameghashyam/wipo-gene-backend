package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ProtectedTaxonInfo {
    private Long genieId;
    private String upovCode;
    private String botanicalName;
    private TaxonNamesInfo names;
    private String protectionStatus;
    private boolean isDerived;
    private String notes;
}