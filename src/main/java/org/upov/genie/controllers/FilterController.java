package org.upov.genie.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upov.genie.domain.dtos.FilterOptionsResponse;
import org.upov.genie.services.FilterService;

@Slf4j
@RestController
@RequestMapping("/api/v1/filters")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FilterController {

    private final FilterService filterService;

    @GetMapping
    public ResponseEntity<FilterOptionsResponse> getFilterOptions() {
        log.info("GET /api/v1/filters - Fetching all filter options");
        
        FilterOptionsResponse response = filterService.getFilterOptions();
        
        log.info("Successfully retrieved filter options: {} authorities, {} families, {} crop types",
                response.getAuthorities().size(),
                response.getFamilies().size(),
                response.getCropTypes().size());
        
        return ResponseEntity.ok(response);
    }
}