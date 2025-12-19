package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.SpeciesOffering;

import java.util.List;

@Repository
public interface SpeciesOfferingRepository extends JpaRepository<SpeciesOffering, Long> {
    
    @Query("SELECT o FROM SpeciesOffering o " +
           "LEFT JOIN FETCH o.authority " +
           "LEFT JOIN FETCH o.derivation " +
           "WHERE o.genieSpecies.genieId = :genieId " +
           "AND o.derivation.derivationId <> 5 " +
           "AND o.authority.authoritySequence = 2 " +
           "ORDER BY o.authority.authorityCode, o.derivation.derivationId")
    List<SpeciesOffering> findByGenieIdForCooperation(@Param("genieId") Long genieId);
}

