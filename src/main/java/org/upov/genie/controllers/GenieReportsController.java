package org.upov.genie.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upov.genie.domain.dtos.*;
import org.upov.genie.services.TaxonDataService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GenieReportsController {
    
    private final TaxonDataService taxonDataService;
    
    @GetMapping("/protection")
    public ResponseEntity<List<ProtectionReportResponse>> getProtectionReport() {
        log.info("GET /api/v1/reports/protection");
        List<ProtectionReportResponse> report = taxonDataService.getProtectionReport();
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/cooperation")
    public ResponseEntity<List<CooperationReportResponse>> getCooperationReport(
        @RequestParam(defaultValue = "true") boolean includeDerived
    ) {
        log.info("GET /api/v1/reports/cooperation?includeDerived={}", includeDerived);
        List<CooperationReportResponse> report = 
            taxonDataService.getCooperationReport(includeDerived);
        return ResponseEntity.ok(report);
    }
}

