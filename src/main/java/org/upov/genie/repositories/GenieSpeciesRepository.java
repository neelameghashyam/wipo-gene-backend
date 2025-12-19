package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieSpecies;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenieSpeciesRepository extends JpaRepository<GenieSpecies, Long> {
    
    @Query("SELECT g FROM GenieSpecies g WHERE g.codeActive = 'Y' ORDER BY g.upovCode")
    List<GenieSpecies> findAllActive();
    
    @Query("SELECT g FROM GenieSpecies g LEFT JOIN FETCH g.names WHERE g.genieId = :genieId")
    Optional<GenieSpecies> findByIdWithNames(@Param("genieId") Long genieId);
    
    @Query("SELECT g FROM GenieSpecies g " +
           "LEFT JOIN FETCH g.names " +
           "LEFT JOIN FETCH g.protections " +
           "LEFT JOIN FETCH g.experiences " +
           "WHERE g.genieId = :genieId")
    Optional<GenieSpecies> findByIdWithAllDetails(@Param("genieId") Long genieId);
    
    @Query("SELECT DISTINCT g FROM GenieSpecies g " +
           "JOIN g.names n " +
           "WHERE g.codeActive = 'Y' AND " +
           "(LOWER(g.upovCode) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(g.genieName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.genieName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<GenieSpecies> searchSpecies(@Param("searchTerm") String searchTerm);
}

