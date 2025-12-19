// GenieTWPRepository.java - CORRECTED
package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieTWP;

import java.util.List;

@Repository
public interface GenieTWPRepository extends JpaRepository<GenieTWP, Long> {
    
    // FIXED: Use gt.genieId instead of gt.genieSpecies.genieId
    @Query("SELECT gt FROM GenieTWP gt " +
           "LEFT JOIN FETCH gt.twp " +
           "WHERE gt.genieId = :genieId")
    List<GenieTWP> findByGenieIdWithTWP(@Param("genieId") Long genieId);
}