package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.GenieGuideline;

import java.util.List;

@Repository
public interface GenieGuidelineRepository extends JpaRepository<GenieGuideline, Long> {
    
    @Query("SELECT gg FROM GenieGuideline gg " +
           "LEFT JOIN FETCH gg.guideline " +
           "LEFT JOIN FETCH gg.derivation " +
           "WHERE gg.genieId = :genieId " +
           "ORDER BY gg.guideline.guidelineCode")
    List<GenieGuideline> findByGenieIdWithDetails(@Param("genieId") Long genieId);

    @Query("SELECT gg FROM GenieGuideline gg " +
           "LEFT JOIN FETCH gg.guideline " +
           "LEFT JOIN FETCH gg.derivation " +
           "WHERE gg.genieId IN (" +
           "  SELECT gh.childGenieId FROM GenieHierarchy gh WHERE gh.parentGenieId = :genieId" +
           ") " +
           "AND gg.derivationId = 2 " +
           "ORDER BY gg.guideline.guidelineCode")
    List<GenieGuideline> findChildGuidelinesByGenieId(@Param("genieId") Long genieId);
}