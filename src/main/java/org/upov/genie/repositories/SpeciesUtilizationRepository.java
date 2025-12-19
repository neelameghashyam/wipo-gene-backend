package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.SpeciesUtilization;

import java.util.List;

@Repository
public interface SpeciesUtilizationRepository extends JpaRepository<SpeciesUtilization, Long> {
    
    @Query("SELECT u FROM SpeciesUtilization u " +
           "LEFT JOIN FETCH u.utilizingAuthority " +
           "LEFT JOIN FETCH u.providingAuthority " +
           "LEFT JOIN FETCH u.derivation " +
           "WHERE u.genieSpecies.genieId = :genieId " +
           "AND u.utilizingAuthority.authoritySequence = 2 " +
           "AND u.providingAuthority.authoritySequence = 2 " +
           "ORDER BY u.derivation.derivationIndicator DESC, " +
           "u.utilizingAuthority.authorityCode, " +
           "u.providingAuthority.authorityCode")
    List<SpeciesUtilization> findByGenieIdForCooperation(@Param("genieId") Long genieId);
}
