package org.upov.genie.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upov.genie.domain.dtos.AuthorityDTO;
import org.upov.genie.domain.dtos.AuthorityDUSResponse;
import org.upov.genie.domain.dtos.AuthorityProtectionResponse;
import org.upov.genie.services.AuthorityDataService;
import org.upov.genie.services.AuthorityService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;
    private final AuthorityDataService authorityDataService;

    /**
     * Search authorities by name or code
     * GET /api/v1/authority/search?q={AUTHORITY_NAME,AUTHORITY_CODE}
     */
    @GetMapping("/search")
    public ResponseEntity<List<AuthorityDTO>> searchAuthorities(
            @RequestParam("q") String query) {
        List<AuthorityDTO> results = authorityService.searchAuthorities(query);
        return ResponseEntity.ok(results);
    }

    /**
     * Get authority by ID
     * GET /api/v1/authority/{AUTHORITY_ID}
     */
    @GetMapping("/{authorityId}")
    public ResponseEntity<AuthorityDTO> getAuthorityById(
            @PathVariable Long authorityId) {
        AuthorityDTO authority = authorityService.getAuthorityById(authorityId);
        return ResponseEntity.ok(authority);
    }

      @GetMapping("/{id}/protection")
    public ResponseEntity<AuthorityProtectionResponse> getAuthorityProtection(
            @PathVariable Long id,
            @RequestParam(defaultValue = "en") String lang) {
        
        // Log.info("Request: GET /api/v1/authority/{}/protection?lang={}", id, lang);
        return ResponseEntity.ok(authorityDataService.getAuthorityProtection(id, lang));
    }

    @GetMapping("/{id}/dus")
    public ResponseEntity<AuthorityDUSResponse> getAuthorityDUS(
            @PathVariable Long id,
            @RequestParam(defaultValue = "en") String lang) {
        
        // log.info("Request: GET /api/v1/authority/{}/dus?lang={}", id, lang);
        return ResponseEntity.ok(authorityDataService.getAuthorityDUS(id, lang));
    }
}