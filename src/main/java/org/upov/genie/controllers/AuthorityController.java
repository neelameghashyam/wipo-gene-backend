package org.upov.genie.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upov.genie.domain.dtos.AuthorityDTO;
import org.upov.genie.services.AuthorityService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

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
}