package org.upov.genie.domain.dtos;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferingDetailInfo {
    private Long genieId;
    private String upovCode;
    private String botanicalName;
    private String offeringString;
    private boolean isEoDesignation;
    private boolean isDerived;
    private List<String> receivingAuthorities;
}