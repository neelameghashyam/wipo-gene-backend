package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class TaxonDetailsResponse {
    private Long genieId;
    private String upovCode;
    private String botanicalName;
    private String family;
    private String cropType;
    private String twp;
    private String denomClass;
    private String testGuideline;
    private TaxonNamesInfo names;
    private List<AuthorityProtectionInfo> protection;
    private DUSCooperationInfo dusGuidance;
}
