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
@RequestMapping("/api/v1/species")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaxonRestController {

    private final TaxonDataService taxonDataService;

    @GetMapping
    public ResponseEntity<TaxonSearchEnhancedResponse> getAllSpecies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        
        log.info("Request: GET /api/v1/species?page={}&pageSize={}", page, pageSize);
        return ResponseEntity.ok(taxonDataService.getAllSpecies(page, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaxonListItemEnhanced>> searchSpecies(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        
        log.info("Request: GET /api/v1/species/search?q={}&page={}&pageSize={}", q, page, pageSize);
        return ResponseEntity.ok(taxonDataService.searchSpecies(q, page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxonDetailsResponse> getSpeciesDetails(
            @PathVariable Long id,
            @RequestParam(defaultValue = "en") String lang) {
        
        log.info("Request: GET /api/v1/species/{}?lang={}", id, lang);
        return ResponseEntity.ok(taxonDataService.getSpeciesDetails(id, lang));
    }

    @GetMapping("/{id}/protection")
    public ResponseEntity<SpeciesProtectionInfo> getSpeciesProtection(
            @PathVariable Long id,
            @RequestParam(defaultValue = "en") String lang) {
        
        log.info("Request: GET /api/v1/species/{}/protection?lang={}", id, lang);
        return ResponseEntity.ok(taxonDataService.getSpeciesProtection(id, lang));
    }

    @GetMapping("/{id}/dus")
    public ResponseEntity<SpeciesDUSInfo> getSpeciesDUS(
            @PathVariable Long id,
            @RequestParam(defaultValue = "en") String lang) {
        
        log.info("Request: GET /api/v1/species/{}/dus?lang={}", id, lang);
        return ResponseEntity.ok(taxonDataService.getSpeciesDUS(id, lang));
    }

    @GetMapping("/reports/protection")
    public ResponseEntity<List<ProtectionReportResponse>> getProtectionReport() {
        log.info("Request: GET /api/v1/species/reports/protection");
        return ResponseEntity.ok(taxonDataService.getProtectionReport());
    }

    @GetMapping("/reports/cooperation")
    public ResponseEntity<List<CooperationReportResponse>> getCooperationReport(
            @RequestParam(defaultValue = "true") boolean includeDerived) {
        
        log.info("Request: GET /api/v1/species/reports/cooperation?includeDerived={}", includeDerived);
        return ResponseEntity.ok(taxonDataService.getCooperationReport(includeDerived));
    }
}