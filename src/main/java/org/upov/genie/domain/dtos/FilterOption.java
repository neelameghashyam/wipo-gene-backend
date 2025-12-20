package org.upov.genie.domain.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterOption {
    private String value;
    private String label;
    private Long count; 
}