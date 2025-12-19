package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;
import java.util.Map;

@Data
@Builder
public class TaxonNamesInfo {
    private String defaultName;
    private Map<String, String> commonNames;
}

