package org.upov.genie.domain.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SpeciesDto {
    private String genieId;
    private String upovCode;
}