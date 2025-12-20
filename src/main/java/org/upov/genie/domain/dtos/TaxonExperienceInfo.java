package org.upov.genie.domain.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxonExperienceInfo {
    private Long genieId;
    private String upovCode;
    private String botanicalName;
    private String englishName;
    private String frenchName;
    private String germanName;
    private String spanishName;
    private boolean isDerived;
    private String notes;
}