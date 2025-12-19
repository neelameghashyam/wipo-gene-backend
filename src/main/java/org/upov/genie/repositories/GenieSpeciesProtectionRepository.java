package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieSpeciesProtection;

import java.util.List;

@Repository
public interface GenieSpeciesProtectionRepository extends JpaRepository<GenieSpeciesProtection, Long> {
    
    @Query("SELECT gp FROM GenieSpeciesProtection gp " +
           "LEFT JOIN FETCH gp.authority " +
           "LEFT JOIN FETCH gp.derivation " +
           "WHERE gp.genieSpecies.genieId = :genieId " +
           "AND gp.protectionActive = 'Y' " +
           "ORDER BY gp.authority.authorityCode, gp.derivation.derivationId")
    List<GenieSpeciesProtection> findByGenieIdWithDetails(@Param("genieId") Long genieId);
}

