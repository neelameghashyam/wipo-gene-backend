// GenieDenominationRepository.java
package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieDenomination;

import java.util.List;

@Repository
public interface GenieDenominationRepository extends JpaRepository<GenieDenomination, Long> {
    
    @Query("SELECT gd FROM GenieDenomination gd " +
           "LEFT JOIN FETCH gd.denomination " +
           "WHERE gd.genieId = :genieId " +
           "ORDER BY gd.denomination.sortCode")
    List<GenieDenomination> findByGenieIdWithDenomination(@Param("genieId") Long genieId);
}