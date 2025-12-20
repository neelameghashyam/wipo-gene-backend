package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class AuthorityProtectionResponse {
    private Long authorityId;
    private String authorityName;
    private String authorityCode;
    private String protectionType; // "All species", "Selected species", etc.
    private String protectionNotes;
    private List<ProtectedTaxonInfo> protectedTaxa;
}