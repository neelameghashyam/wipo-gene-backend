package org.upov.genie.domain.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestGuidelineInfo {
    private String guidelineCode;
    private String guidelineName;
    private String guidelineLink;
    private boolean isChildGuideline;
}