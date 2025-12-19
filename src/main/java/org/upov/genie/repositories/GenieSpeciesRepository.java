// GenieSpeciesRepository.java - Updated
package org.upov.genie.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieSpecies;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenieSpeciesRepository extends JpaRepository<GenieSpecies, Long> {

    @Query("SELECT g FROM GenieSpecies g WHERE g.codeActive = 'Y' ORDER BY g.updateDate DESC NULLS LAST, g.createDate DESC NULLS LAST, g.upovCode")
    Page<GenieSpecies> findAllActiveOrderedByDate(Pageable pageable);

    @Query("SELECT g FROM GenieSpecies g " +
           "LEFT JOIN FETCH g.names " +
           "LEFT JOIN FETCH g.genieFamilies gf " +
           "LEFT JOIN FETCH gf.family " +
           "LEFT JOIN FETCH g.genieTWPs gt " +
           "LEFT JOIN FETCH gt.twp " +
           "LEFT JOIN FETCH g.protections gp " +
           "LEFT JOIN FETCH gp.authority " +
           "WHERE g.genieId = :genieId")
    Optional<GenieSpecies> findByIdWithAllDetails(@Param("genieId") Long genieId);

    @Query("SELECT DISTINCT g FROM GenieSpecies g " +
           "LEFT JOIN g.names n " +
           "LEFT JOIN g.genieFamilies gf " +
           "LEFT JOIN gf.family f " +
           "LEFT JOIN g.genieTWPs gt " +
           "LEFT JOIN gt.twp t " +
           "LEFT JOIN g.protections gp " +
           "LEFT JOIN gp.authority a " +
           "WHERE g.codeActive = 'Y' AND " +
           "(LOWER(g.upovCode) LIKE LOWER(CONCAT(:searchTerm, '%')) OR " +
           "LOWER(g.genieName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(n.genieName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "ORDER BY g.updateDate DESC NULLS LAST, g.createDate DESC NULLS LAST, g.upovCode")
    Page<GenieSpecies> searchSpeciesWithDetails(@Param("searchTerm") String searchTerm, Pageable pageable);
}