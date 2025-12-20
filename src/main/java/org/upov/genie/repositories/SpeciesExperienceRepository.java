package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.SpeciesExperience;
import org.upov.genie.domain.entities.GenieSpecies;

import java.util.List;

@Repository
public interface SpeciesExperienceRepository extends JpaRepository<SpeciesExperience, Long> {
    
    // EXISTING METHOD - Keep as is
    @Query("SELECT e FROM SpeciesExperience e " +
           "LEFT JOIN FETCH e.authority " +
           "LEFT JOIN FETCH e.derivation " +
           "WHERE e.genieSpecies.genieId = :genieId " +
           "ORDER BY e.authority.authorityCode, e.derivation.derivationId")
    List<SpeciesExperience> findByGenieIdWithDetails(@Param("genieId") Long genieId);
    
    // NEW METHOD - Add this
    @Query("SELECT e FROM SpeciesExperience e " +
           "LEFT JOIN FETCH e.genieSpecies " +
           "LEFT JOIN FETCH e.derivation " +
           "WHERE e.authority.authorityId = :authorityId " +
           "ORDER BY e.genieSpecies.upovCode")
    List<SpeciesExperience> findByAuthorityIdWithDetails(@Param("authorityId") Long authorityId);
    
    // EXISTING METHOD - Keep as is
    @Query("SELECT DISTINCT g FROM GenieSpecies g " +
           "JOIN g.experiences e " +
           "WHERE g.codeActive = 'Y' AND e.derivation.derivationId = 2 " +
           "ORDER BY g.upovCode")
    List<GenieSpecies> findSpeciesWithExperience();
}