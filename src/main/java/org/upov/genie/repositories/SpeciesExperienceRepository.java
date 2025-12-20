package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.SpeciesExperience;

import java.util.List;

@Repository
public interface SpeciesExperienceRepository extends JpaRepository<SpeciesExperience, Long> {
    
    @Query("SELECT se FROM SpeciesExperience se " +
           "LEFT JOIN FETCH se.authority " +
           "LEFT JOIN FETCH se.derivation " +
           "WHERE se.genieId = :genieId " +
           "ORDER BY se.authority.authorityCode, se.derivation.derivationIndicator DESC")
    List<SpeciesExperience> findByGenieIdWithDetails(@Param("genieId") Long genieId);

    @Query("SELECT se FROM SpeciesExperience se " +
           "LEFT JOIN FETCH se.authority " +
           "LEFT JOIN FETCH se.derivation " +
           "LEFT JOIN FETCH se.genieSpecies " +
           "WHERE se.authorityId = :authorityId " +
           "AND se.derivationId = 2 " +
           "AND se.genieSpecies.codeActive = 'Y' " +
           "ORDER BY se.genieSpecies.upovCode")
    List<SpeciesExperience> findByAuthorityIdWithDetails(@Param("authorityId") Long authorityId);

    @Query("SELECT DISTINCT gs FROM GenieSpecies gs " +
           "INNER JOIN SpeciesExperience se ON se.genieId = gs.genieId " +
           "WHERE gs.codeActive = 'Y' " +
           "ORDER BY gs.upovCode")
    List<org.upov.genie.domain.entities.GenieSpecies> findSpeciesWithExperience();
}