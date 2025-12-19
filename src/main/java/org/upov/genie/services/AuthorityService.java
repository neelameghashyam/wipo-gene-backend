package org.upov.genie.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upov.genie.domain.dtos.AuthorityDTO;
import org.upov.genie.domain.entities.Authority;
import org.upov.genie.mappers.AuthorityMapper;
import org.upov.genie.repositories.AuthorityRepository;
import org.upov.genie.utils.ApiException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    public List<AuthorityDTO> searchAuthorities(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new ApiException("Search query cannot be empty", 400);
        }

        List<Authority> authorities = authorityRepository.searchByNameOrCode(query);

        return authorities.stream()
                .map(authorityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AuthorityDTO getAuthorityById(Long authorityId) {
        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new ApiException("Authority not found with id: " + authorityId, 404));

        return authorityMapper.toDTO(authority);
    }
}