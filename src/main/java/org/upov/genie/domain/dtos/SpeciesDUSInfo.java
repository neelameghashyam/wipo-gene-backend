package org.upov.genie.domain.dtos;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDUSInfo {
    private String upovCode;
    private String botanicalName;
    private List<String> otherBotanicalNames;
    private List<String> commonNamesEnglish;
    private List<TestGuidelineInfo> testGuidelines;
    private String draftingAuthority;
    private List<PracticalExperienceInfo> authoritiesWithExperience;
    private List<CooperationOfferingInfo> offerings;
    private List<UtilizationInfo> utilizations;
}