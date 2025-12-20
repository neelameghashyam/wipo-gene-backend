package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.ProtectionName;

import java.util.Optional;

@Repository
public interface ProtectionNameRepository extends JpaRepository<ProtectionName, Long> {
    
    @Query("SELECT pn FROM ProtectionName pn " +
           "WHERE pn.protectionId = :protectionId " +
           "AND pn.languageId = :languageId")
    Optional<ProtectionName> findByProtectionIdAndLanguageId(
        @Param("protectionId") Long protectionId,
        @Param("languageId") Integer languageId
    );
}