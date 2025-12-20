package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.Offering;

import java.util.List;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {
    
    @Query("SELECT o FROM Offering o " +
           "LEFT JOIN FETCH o.authority " +
           "LEFT JOIN FETCH o.derivation " +
           "WHERE o.genieId = :genieId " +
           "ORDER BY o.authority.authorityCode")
    List<Offering> findByGenieIdWithDetails(@Param("genieId") Long genieId);

    @Query("SELECT o FROM Offering o " +
           "LEFT JOIN FETCH o.authority " +
           "LEFT JOIN FETCH o.derivation " +
           "WHERE o.authorityId = :authorityId " +
           "ORDER BY o.genieId")
    List<Offering> findByAuthorityIdWithDetails(@Param("authorityId") Long authorityId);
}