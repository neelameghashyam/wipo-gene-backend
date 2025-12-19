package org.upov.genie.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upov.genie.domain.dtos.HealthResponse;
import org.upov.genie.services.SpeciesService;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final SpeciesService speciesService;

    @GetMapping("/api/health")
    public ResponseEntity<HealthResponse> health() {
        long count = speciesService.countAll();
        HealthResponse resp = HealthResponse.builder()
                .status("UP")
                .speciesCount(count)
                .build();
        return ResponseEntity.ok(resp);
    }
}