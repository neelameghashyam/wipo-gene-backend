package org.upov.genie.domain.dtos;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterOptionsResponse {
    private List<FilterOption> authorities;
    private List<FilterOption> families;
    private List<FilterOption> cropTypes;
}