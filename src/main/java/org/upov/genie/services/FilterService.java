package org.upov.genie.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upov.genie.domain.dtos.FilterOptionsResponse;
import org.upov.genie.mappers.FilterMapper;
import org.upov.genie.repositories.FilterRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilterService {

    private final FilterRepository filterRepository;
    private final FilterMapper filterMapper;

    /**
     * Get all filter options for authorities, families, and crop types
     * This method is cached to improve performance since filter options
     * don't change frequently
     */
    @Cacheable(value = "filterOptions", unless = "#result == null")
    public FilterOptionsResponse getFilterOptions() {
        log.info("Fetching filter options from database");

        try {
            // Fetch data from repositories
            List<Object[]> authorities = filterRepository.findAllAuthoritiesWithCount();
            List<Object[]> families = filterRepository.findAllFamiliesWithCount();
            List<Object[]> cropTypes = filterRepository.findAllTWPsWithCount();

            log.debug("Found {} authorities, {} families, {} TWP codes", 
                     authorities.size(), families.size(), cropTypes.size());

            // Map to response DTOs
            FilterOptionsResponse response = filterMapper.buildFilterResponse(
                authorities, families, cropTypes
            );

            log.info("Successfully built filter response with {} authorities, {} families, {} crop types",
                    response.getAuthorities().size(),
                    response.getFamilies().size(),
                    response.getCropTypes().size());

            return response;

        } catch (Exception e) {
            log.error("Error fetching filter options", e);
            throw new RuntimeException("Failed to fetch filter options: " + e.getMessage(), e);
        }
    }

    /**
     * Get only authorities for filtering
     */
    public List<Object[]> getAuthorities() {
        log.debug("Fetching authorities only");
        return filterRepository.findAllAuthoritiesWithCount();
    }

    /**
     * Get only families for filtering
     */
    public List<Object[]> getFamilies() {
        log.debug("Fetching families only");
        return filterRepository.findAllFamiliesWithCount();
    }

    /**
     * Get only crop types for filtering
     */
    public List<Object[]> getCropTypes() {
        log.debug("Fetching crop types only");
        return filterRepository.findAllTWPsWithCount();
    }
}