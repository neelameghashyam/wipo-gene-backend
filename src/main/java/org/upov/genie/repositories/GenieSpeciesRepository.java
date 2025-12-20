// GenieSpeciesRepository.java - Simplified without the problematic joins
package org.upov.genie.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieSpecies;

import java.util.Optional;

@Repository
public interface GenieSpeciesRepository extends JpaRepository<GenieSpecies, Long> {

    @Query("SELECT g FROM GenieSpecies g WHERE g.codeActive = 'Y' " +
           "ORDER BY g.updateDate DESC NULLS LAST, g.createDate DESC NULLS LAST, g.upovCode")
    Page<GenieSpecies> findAllActiveOrderedByDate(Pageable pageable);

    @Query("SELECT g FROM GenieSpecies g " +
           "LEFT JOIN FETCH g.names " +
           "WHERE g.genieId = :genieId")
    Optional<GenieSpecies> findByIdWithNames(@Param("genieId") Long genieId);

    @Query("SELECT DISTINCT g FROM GenieSpecies g " +
           "LEFT JOIN g.names n " +
           "WHERE g.codeActive = 'Y' AND " +
           "(LOWER(g.upovCode) LIKE LOWER(CONCAT(:searchTerm, '%')) OR " +
           "LOWER(g.genieName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.genieName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "ORDER BY g.updateDate DESC NULLS LAST, g.createDate DESC NULLS LAST, g.upovCode")
    Page<GenieSpecies> searchSpeciesWithDetails(@Param("searchTerm") String searchTerm, Pageable pageable);
}