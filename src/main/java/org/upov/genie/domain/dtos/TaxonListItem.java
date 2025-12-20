package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaxonListItem {
    private Long genieId;
    private String upovCode;
    private String botanicalName;
    private String defaultName;
    private String family;
    private String cropType;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
    private List<String> authorityNames;
    private List<String> authorityIsoCodes;
}