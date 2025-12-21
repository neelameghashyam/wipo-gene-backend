package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class AuthorityProtectionInfo {
    private String authorityCode;
    private String protectionType;
    private boolean isDerived;
    private String notes;
    private String authorityName;
    private String administrativeWebsite;
    private String lawWebsite;
}