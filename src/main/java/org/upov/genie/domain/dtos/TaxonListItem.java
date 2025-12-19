package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class TaxonListItem {
    private Long genieId;
    private String upovCode;
    private String botanicalName;
    private String defaultName;
}

