package org.upov.genie.domain.dtos;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesProtectionInfo {
    private String upovCode;
    private String botanicalName;
    private List<String> otherBotanicalNames;
    private List<String> commonNamesEnglish;
    private String entryNote;
    private String memberUnionNote;
    private List<ProtectionAuthorityInfo> protectingAuthorities;
}