package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieSpeciesName;

import java.util.List;

@Repository
public interface GenieSpeciesNameRepository extends JpaRepository<GenieSpeciesName, Long> {
    
    @Query("SELECT gn FROM GenieSpeciesName gn WHERE gn.genieSpecies.genieId = :genieId " +
           "ORDER BY gn.languageId, gn.defaultName DESC, gn.genieName")
    List<GenieSpeciesName> findByGenieIdOrdered(@Param("genieId") Long genieId);
    
    @Query("SELECT gn FROM GenieSpeciesName gn WHERE gn.genieSpecies.genieId = :genieId " +
           "AND gn.languageId = :languageId " +
           "ORDER BY gn.defaultName DESC, gn.genieName")
    List<GenieSpeciesName> findByGenieIdAndLanguage(
        @Param("genieId") Long genieId, 
        @Param("languageId") Integer languageId
    );
}

