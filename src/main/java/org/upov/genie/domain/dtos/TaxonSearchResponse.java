package org.upov.genie.domain.dtos;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class TaxonSearchResponse {
    private int totalCount;
    private int page;
    private int pageSize;
    private List<TaxonListItem> species;
}

