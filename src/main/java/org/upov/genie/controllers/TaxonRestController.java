// TaxonRestController.java - Updated
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

    @GetMapping("/{id}")
    public ResponseEntity<TaxonDetailsResponse> getSpeciesDetails(
        @PathVariable Long id,
        @RequestParam(defaultValue = "en") String lang
    ) {
        log.info("GET /api/v1/species/{} - lang: {}", id, lang);
        TaxonDetailsResponse details = taxonDataService.getSpeciesDetails(id, lang);
        return ResponseEntity.ok(details);
    }

    @GetMapping
    public ResponseEntity<TaxonSearchEnhancedResponse> getAllSpecies(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        log.info("GET /api/v1/species - page: {}, size: {}", page, size);
        TaxonSearchEnhancedResponse response = taxonDataService.getAllSpecies(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaxonListItemEnhanced>> searchSpecies(
        @RequestParam String q,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        log.info("GET /api/v1/species/search?q={}, page={}, size={}", q, page, size);
        List<TaxonListItemEnhanced> results = taxonDataService.searchSpecies(q, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}/names")
    public ResponseEntity<TaxonNamesInfo> getSpeciesNames(@PathVariable Long id) {
        log.info("GET /api/v1/species/{}/names", id);
        TaxonDetailsResponse details = taxonDataService.getSpeciesDetails(id, "en");
        return ResponseEntity.ok(details.getNames());
    }

    @GetMapping("/{id}/protection")
    public ResponseEntity<List<AuthorityProtectionInfo>> getSpeciesProtection(@PathVariable Long id) {
        log.info("GET /api/v1/species/{}/protection", id);
        TaxonDetailsResponse details = taxonDataService.getSpeciesDetails(id, "en");
        return ResponseEntity.ok(details.getProtection());
    }

    @GetMapping("/{id}/dus-guidance")
    public ResponseEntity<DUSCooperationInfo> getDUSGuidance(@PathVariable Long id) {
        log.info("GET /api/v1/species/{}/dus-guidance", id);
        TaxonDetailsResponse details = taxonDataService.getSpeciesDetails(id, "en");
        return ResponseEntity.ok(details.getDusGuidance());
    }
}