package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.DerivationName;

import java.util.Optional;

@Repository
public interface DerivationNameRepository extends JpaRepository<DerivationName, Long> {
    
    @Query("SELECT dn FROM DerivationName dn " +
           "WHERE dn.derivationId = :derivationId " +
           "AND dn.languageId = :languageId")
    Optional<DerivationName> findByDerivationIdAndLanguageId(
        @Param("derivationId") Long derivationId,
        @Param("languageId") Integer languageId
    );
}