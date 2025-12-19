package org.upov.genie.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.upov.genie.domain.dtos.SpeciesDto;
import org.upov.genie.domain.entities.GenieEntity;
import org.upov.genie.mappers.SpeciesMapper;
import org.upov.genie.repositories.GenieRepository;
import org.upov.genie.utils.ApiException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeciesService {

    private final GenieRepository repo;
    private final SpeciesMapper mapper;

    public SpeciesDto getById(String id) {
        GenieEntity entity = repo.findByGenieId(id)
                .orElseThrow(() -> new ApiException("Species not found: " + id, 404));
        return mapper.toDto(entity);
    }

    public List<SpeciesDto> search(String q) {
        return repo.searchByCodeOrName(q).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public long countAll() {
        return repo.count();
    }
}