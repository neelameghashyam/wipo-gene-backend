package org.upov.genie.mappers;

import org.upov.genie.domain.dtos.SpeciesDto;
import org.upov.genie.domain.entities.GenieEntity;
import org.springframework.stereotype.Component;

@Component
public class SpeciesMapper {

    public SpeciesDto toDto(GenieEntity entity) {
        if (entity == null) return null;
        return SpeciesDto.builder()
                .genieId(entity.getGenieId())
                .upovCode(entity.getUpovCode())
                .build();
    }
}