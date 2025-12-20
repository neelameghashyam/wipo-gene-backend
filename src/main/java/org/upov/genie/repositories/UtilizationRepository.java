package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.Utilization;

import java.util.List;

@Repository
public interface UtilizationRepository extends JpaRepository<Utilization, Long> {
    
    @Query("SELECT u FROM Utilization u " +
           "LEFT JOIN FETCH u.utilizingAuthority " +
           "LEFT JOIN FETCH u.providingAuthority " +
           "LEFT JOIN FETCH u.derivation " +
           "WHERE u.genieId = :genieId " +
           "ORDER BY u.utilizingAuthority.authorityCode")
    List<Utilization> findByGenieIdWithDetails(@Param("genieId") Long genieId);

    @Query("SELECT u FROM Utilization u " +
           "LEFT JOIN FETCH u.utilizingAuthority " +
           "LEFT JOIN FETCH u.providingAuthority " +
           "LEFT JOIN FETCH u.derivation " +
           "WHERE u.utilizingAuthorityId = :authorityId " +
           "ORDER BY u.genieId")
    List<Utilization> findByUtilizingAuthorityIdWithDetails(@Param("authorityId") Long authorityId);

    @Query("SELECT u FROM Utilization u " +
           "LEFT JOIN FETCH u.utilizingAuthority " +
           "LEFT JOIN FETCH u.providingAuthority " +
           "LEFT JOIN FETCH u.derivation " +
           "WHERE u.providingAuthorityId = :authorityId " +
           "ORDER BY u.genieId")
    List<Utilization> findByProvidingAuthorityIdWithDetails(@Param("authorityId") Long authorityId);
}