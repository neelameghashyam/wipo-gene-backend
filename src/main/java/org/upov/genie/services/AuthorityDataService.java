package org.upov.genie.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upov.genie.domain.dtos.*;
import org.upov.genie.domain.entities.*;
import org.upov.genie.mappers.AuthorityResponseMapper;
import org.upov.genie.repositories.*;
import org.upov.genie.utils.GenieApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityDataService {

    private final AuthorityRepository authorityRepository;
    private final GenieSpeciesProtectionRepository protectionRepository;
    private final SpeciesExperienceRepository experienceRepository;
    private final AuthorityResponseMapper authorityResponseMapper;

    /**
     * Get protection information for a specific authority
     * Shows which taxa are protected by this authority
     */
    public AuthorityProtectionResponse getAuthorityProtection(Long authorityId) {
        log.debug("Fetching protection info for authority ID: {}", authorityId);

        // Get authority details
        Authority authority = authorityRepository.findById(authorityId)
            .orElseThrow(() -> new GenieApiException("Authority not found with ID: " + authorityId));

        // Get all species protected by this authority
        List<GenieSpeciesProtection> protections = protectionRepository
            .findByAuthorityIdWithDetails(authorityId);

        log.debug("Found {} protected taxa for authority {}", protections.size(), authorityId);

        // Map to response
        return authorityResponseMapper.toProtectionResponse(authority, protections);
    }

    /**
     * Get DUS experience information for a specific authority
     * Shows which taxa the authority has practical DUS testing experience with
     */
    public AuthorityDUSResponse getAuthorityDUS(Long authorityId) {
        log.debug("Fetching DUS info for authority ID: {}", authorityId);

        // Get authority details
        Authority authority = authorityRepository.findById(authorityId)
            .orElseThrow(() -> new GenieApiException("Authority not found with ID: " + authorityId));

        // Get all species where this authority has DUS experience
        List<SpeciesExperience> experiences = experienceRepository
            .findByAuthorityIdWithDetails(authorityId);

        log.debug("Found {} taxa with DUS experience for authority {}", experiences.size(), authorityId);

        // Map to response
        return authorityResponseMapper.toDUSResponse(authority, experiences);
    }
}