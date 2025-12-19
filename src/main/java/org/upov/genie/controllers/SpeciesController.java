package org.upov.genie.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upov.genie.domain.dtos.SpeciesDto;
import org.upov.genie.services.SpeciesService;
import org.upov.genie.utils.ApiException;

import java.util.List;

@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesService service;

    @GetMapping("/{id}")
    public ResponseEntity<SpeciesDto> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SpeciesDto>> search(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) {
            throw new ApiException("Query parameter 'q' is required", 400);
        }
        return ResponseEntity.ok(service.search(q));
    }
}