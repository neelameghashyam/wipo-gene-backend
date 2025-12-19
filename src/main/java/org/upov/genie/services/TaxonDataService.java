// TaxonDataService.java - Fixed with separate queries
package org.upov.genie.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upov.genie.domain.dtos.*;
import org.upov.genie.domain.entities.*;
import org.upov.genie.mappers.TaxonResponseMapper;
import org.upov.genie.repositories.*;
import org.upov.genie.utils.GenieApiException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaxonDataService {

    private final GenieSpeciesRepository genieSpeciesRepository;
    private final GenieSpeciesNameRepository genieSpeciesNameRepository;
    private final GenieSpeciesProtectionRepository protectionRepository;
    private final SpeciesExperienceRepository experienceRepository;
    private final SpeciesOfferingRepository offeringRepository;
    private final SpeciesUtilizationRepository utilizationRepository;
    private final GenieFamilyRepository genieFamilyRepository;
    private final GenieTWPRepository genieTWPRepository;
    private final TaxonResponseMapper taxonMapper;

    public TaxonDetailsResponse getSpeciesDetails(Long genieId, String lang) {
        log.debug("Fetching details for species ID: {} in language: {}", genieId, lang);

        GenieSpecies genie = genieSpeciesRepository.findById(genieId)
            .orElseThrow(() -> new GenieApiException("Species not found with ID: " + genieId));

        List<GenieSpeciesName> names = genieSpeciesNameRepository.findByGenieIdOrdered(genieId);
        List<GenieSpeciesProtection> protections = protectionRepository.findByGenieIdWithDetails(genieId);
        List<SpeciesExperience> experiences = experienceRepository.findByGenieIdWithDetails(genieId);
        List<SpeciesOffering> offerings = offeringRepository.findByGenieIdForCooperation(genieId);
        List<SpeciesUtilization> utilizations = utilizationRepository.findByGenieIdForCooperation(genieId);
        List<GenieFamily> families = genieFamilyRepository.findByGenieIdWithFamily(genieId);
        List<GenieTWP> twps = genieTWPRepository.findByGenieIdWithTWP(genieId);

        return taxonMapper.toDetailsResponse(
            genie, names, protections, experiences, offerings, utilizations, families, twps, lang
        );
    }

    public TaxonSearchEnhancedResponse getAllSpecies(int page, int pageSize) {
        log.debug("Fetching all active species - page: {}, size: {}", page, pageSize);

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<GenieSpecies> speciesPage = genieSpeciesRepository.findAllActiveOrderedByDate(pageable);

        List<TaxonListItemEnhanced> speciesList = speciesPage.getContent().stream()
            .map(genie -> {
                List<GenieFamily> families = genieFamilyRepository.findByGenieIdWithFamily(genie.getGenieId());
                List<GenieTWP> twps = genieTWPRepository.findByGenieIdWithTWP(genie.getGenieId());
                return taxonMapper.toListItemEnhanced(genie, families, twps);
            })
            .collect(Collectors.toList());

        return TaxonSearchEnhancedResponse.builder()
            .totalCount((int) speciesPage.getTotalElements())
            .page(page)
            .pageSize(pageSize)
            .species(speciesList)
            .build();
    }

    public List<TaxonListItemEnhanced> searchSpecies(String searchTerm, int page, int pageSize) {
        log.debug("Searching species with term: {}, page: {}, size: {}", searchTerm, page, pageSize);

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return List.of();
        }

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<GenieSpecies> results = genieSpeciesRepository.searchSpeciesWithDetails(
            searchTerm.trim(), pageable
        );

        return results.getContent().stream()
            .map(genie -> {
                List<GenieFamily> families = genieFamilyRepository.findByGenieIdWithFamily(genie.getGenieId());
                List<GenieTWP> twps = genieTWPRepository.findByGenieIdWithTWP(genie.getGenieId());
                return taxonMapper.toListItemEnhanced(genie, families, twps);
            })
            .collect(Collectors.toList());
    }

    public List<ProtectionReportResponse> getProtectionReport() {
        log.debug("Generating protection report");
        
        List<GenieSpecies> allSpecies = genieSpeciesRepository.findAll().stream()
            .filter(g -> "Y".equals(g.getCodeActive()))
            .collect(Collectors.toList());

        return allSpecies.stream()
            .map(genie -> {
                List<GenieSpeciesProtection> protections = 
                    protectionRepository.findByGenieIdWithDetails(genie.getGenieId());
                List<GenieSpeciesName> names = 
                    genieSpeciesNameRepository.findByGenieIdOrdered(genie.getGenieId());
                
                return taxonMapper.toProtectionReportResponse(genie, names, protections);
            })
            .filter(dto -> !dto.getProtectingAuthorities().isEmpty())
            .collect(Collectors.toList());
    }

    public List<CooperationReportResponse> getCooperationReport(boolean includeDerived) {
        log.debug("Generating cooperation report, includeDerived: {}", includeDerived);
        
        List<GenieSpecies> speciesWithExperience = 
            experienceRepository.findSpeciesWithExperience();

        return speciesWithExperience.stream()
            .map(genie -> {
                List<GenieSpeciesName> names = 
                    genieSpeciesNameRepository.findByGenieIdOrdered(genie.getGenieId());
                List<SpeciesExperience> experiences = 
                    experienceRepository.findByGenieIdWithDetails(genie.getGenieId());
                List<SpeciesOffering> offerings = 
                    offeringRepository.findByGenieIdForCooperation(genie.getGenieId());
                List<SpeciesUtilization> utilizations = 
                    utilizationRepository.findByGenieIdForCooperation(genie.getGenieId());

                return taxonMapper.toCooperationReportResponse(
                    genie, names, experiences, offerings, utilizations, includeDerived
                );
            })
            .collect(Collectors.toList());
    }
}