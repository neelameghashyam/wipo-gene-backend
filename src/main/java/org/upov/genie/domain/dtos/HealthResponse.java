package org.upov.genie.domain.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HealthResponse {
    private String status;      // "UP" / "DOWN"
    private long speciesCount;
}