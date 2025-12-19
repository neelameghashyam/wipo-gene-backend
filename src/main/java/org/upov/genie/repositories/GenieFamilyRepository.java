// GenieFamilyRepository.java
package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieFamily;

import java.util.List;

@Repository
public interface GenieFamilyRepository extends JpaRepository<GenieFamily, Long> {
    
    @Query("SELECT gf FROM GenieFamily gf " +
           "LEFT JOIN FETCH gf.family " +
           "WHERE gf.genieId = :genieId")
    List<GenieFamily> findByGenieIdWithFamily(@Param("genieId") Long genieId);
}