package org.upov.genie.domain.dtos;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProtectedSpeciesInfo {
    private Long genieId;
    private String upovCode;
    private String botanicalName;
    private List<String> commonNames;
    private String status;
    private String protectionType;
    private boolean isDerived;
    private String notes;
}