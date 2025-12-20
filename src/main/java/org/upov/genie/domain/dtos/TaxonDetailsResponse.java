// TaxonDetailsResponse.java - UPDATED
package org.upov.genie.domain.dtos;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxonDetailsResponse {
    private Long genieId;
    private String upovCode;
    private String botanicalName;
    private String otherBotanicalNames;  // NEW FIELD
    private String denominationClass;     // NEW FIELD
    private String family;
    private String cropType;
    private String twp;
    private String denomClass;  // Keep for backward compatibility
    private String testGuideline;
    private TaxonNamesInfo names;
    private List<AuthorityProtectionInfo> protection;
    private DUSCooperationInfo dusGuidance;
}