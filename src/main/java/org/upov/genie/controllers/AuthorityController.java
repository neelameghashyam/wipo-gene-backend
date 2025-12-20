package org.upov.genie.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upov.genie.domain.dtos.AuthorityDTO;
import org.upov.genie.domain.dtos.AuthorityProtectionResponse;
import org.upov.genie.domain.dtos.AuthorityDUSResponse;
import org.upov.genie.services.AuthorityService;
import org.upov.genie.services.AuthorityDataService;

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
    
    /**
     * Get protection information for an authority
     * Shows which taxa are protected by this authority
     * GET /api/v1/authority/{AUTHORITY_ID}/protection
     * 
     * NEW ENDPOINT - Equivalent to legacy authority.jsp protection table
     */
    @GetMapping("/{authorityId}/protection")
    public ResponseEntity<AuthorityProtectionResponse> getAuthorityProtection(
            @PathVariable Long authorityId) {
        AuthorityProtectionResponse response = authorityDataService.getAuthorityProtection(authorityId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get DUS guidance information for an authority
     * Shows which taxa the authority has DUS testing experience with
     * GET /api/v1/authority/{AUTHORITY_ID}/dus
     * 
     * NEW ENDPOINT - Equivalent to legacy authority_dus.jsp
     */
    @GetMapping("/{authorityId}/dus")
    public ResponseEntity<AuthorityDUSResponse> getAuthorityDUS(
            @PathVariable Long authorityId) {
        AuthorityDUSResponse response = authorityDataService.getAuthorityDUS(authorityId);
        return ResponseEntity.ok(response);
    }
}