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
           "LEFT JOIN FETCH gp.protection " +
           "LEFT JOIN FETCH gp.derivation " +
           "WHERE gp.genieId = :genieId " +
           "ORDER BY gp.authority.authorityCode")
    List<GenieSpeciesProtection> findByGenieIdWithDetails(@Param("genieId") Long genieId);

    @Query("SELECT gp FROM GenieSpeciesProtection gp " +
           "LEFT JOIN FETCH gp.authority a " +
           "LEFT JOIN FETCH gp.protection " +
           "LEFT JOIN FETCH gp.derivation " +
           "LEFT JOIN FETCH gp.genieSpecies " +
           "WHERE gp.authorityId = :authorityId " +
           "AND gp.genieSpecies.codeActive = 'Y' " +
           "ORDER BY gp.genieSpecies.upovCode")
    List<GenieSpeciesProtection> findByAuthorityIdWithDetails(@Param("authorityId") Long authorityId);
}