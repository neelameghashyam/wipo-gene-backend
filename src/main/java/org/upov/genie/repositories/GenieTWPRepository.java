// GenieTWPRepository.java - New
package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieTWP;

import java.util.List;

@Repository
public interface GenieTWPRepository extends JpaRepository<GenieTWP, Long> {
    
    @Query("SELECT gt FROM GenieTWP gt " +
           "LEFT JOIN FETCH gt.twp " +
           "WHERE gt.genieSpecies.genieId = :genieId")
    List<GenieTWP> findByGenieIdWithTWP(@Param("genieId") Long genieId);
}