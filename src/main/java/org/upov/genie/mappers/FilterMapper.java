package org.upov.genie.mappers;

import org.springframework.stereotype.Component;
import org.upov.genie.domain.dtos.FilterOption;
import org.upov.genie.domain.dtos.FilterOptionsResponse;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FilterMapper {

    // Mapping from TWP codes to crop type display names
    private static final Map<String, String> TWP_TO_CROP_TYPE = Map.of(
        "TWA", "Agriculture",
        "TWF", "Fruit",
        "TWO", "Ornamental",
        "TWO-T", "Forest tree",
        "TWO-O", "Ornamental",
        "TWV", "Vegetable"
    );

    /**
     * Map authority query results to FilterOption list
     * @param queryResults List<Object[]> where [0]=code, [1]=name, [2]=count
     */
    public List<FilterOption> mapAuthorities(List<Object[]> queryResults) {
        if (queryResults == null || queryResults.isEmpty()) {
            return new ArrayList<>();
        }

        return queryResults.stream()
            .filter(row -> row[0] != null && row[1] != null)
            .map(row -> FilterOption.builder()
                .value((String) row[0])  // authorityCode
                .label((String) row[1])  // authorityName
                .count(((Number) row[2]).longValue())  // species count
                .build())
            .collect(Collectors.toList());
    }

    /**
     * Map family query results to FilterOption list
     * @param queryResults List<Object[]> where [0]=familyName, [1]=count
     */
    public List<FilterOption> mapFamilies(List<Object[]> queryResults) {
        if (queryResults == null || queryResults.isEmpty()) {
            return new ArrayList<>();
        }

        return queryResults.stream()
            .filter(row -> row[0] != null)
            .map(row -> {
                String familyName = (String) row[0];
                return FilterOption.builder()
                    .value(familyName)
                    .label(familyName)
                    .count(((Number) row[1]).longValue())
                    .build();
            })
            .collect(Collectors.toList());
    }

    /**
     * Map TWP query results to crop type FilterOption list
     * Combines multiple TWP codes into logical crop type categories
     * @param queryResults List<Object[]> where [0]=twpCode, [1]=twpName, [2]=count
     */
    public List<FilterOption> mapCropTypes(List<Object[]> queryResults) {
        if (queryResults == null || queryResults.isEmpty()) {
            return new ArrayList<>();
        }

        // Group by crop type and aggregate counts
        Map<String, Long> cropTypeCounts = new LinkedHashMap<>();

        for (Object[] row : queryResults) {
            String twpCode = (String) row[0];
            Long count = ((Number) row[2]).longValue();

            // Map TWP code to crop type
            String cropType = TWP_TO_CROP_TYPE.getOrDefault(twpCode, "Other");
            
            // Special handling for TWO variants - all map to "Ornamental"
            if (twpCode != null && twpCode.startsWith("TWO")) {
                cropType = "Ornamental";
            }

            // Aggregate counts
            cropTypeCounts.merge(cropType, count, Long::sum);
        }

        // Convert to FilterOption list
        return cropTypeCounts.entrySet().stream()
            .map(entry -> FilterOption.builder()
                .value(entry.getKey())
                .label(entry.getKey())
                .count(entry.getValue())
                .build())
            .sorted(Comparator.comparing(FilterOption::getLabel))
            .collect(Collectors.toList());
    }

    /**
     * Build complete FilterOptionsResponse
     */
    public FilterOptionsResponse buildFilterResponse(
            List<Object[]> authorities,
            List<Object[]> families,
            List<Object[]> cropTypes) {
        
        return FilterOptionsResponse.builder()
            .authorities(mapAuthorities(authorities))
            .families(mapFamilies(families))
            .cropTypes(mapCropTypes(cropTypes))
            .build();
    }
}