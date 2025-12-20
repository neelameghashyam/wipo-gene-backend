package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.AuthorityName;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityNameRepository extends JpaRepository<AuthorityName, Long> {
    
    @Query("SELECT an FROM AuthorityName an " +
           "WHERE an.authorityId = :authorityId " +
           "AND an.languageId = :languageId")
    Optional<AuthorityName> findByAuthorityIdAndLanguageId(
        @Param("authorityId") Long authorityId,
        @Param("languageId") Integer languageId
    );

    @Query("SELECT an FROM AuthorityName an " +
           "WHERE an.authorityId = :authorityId")
    List<AuthorityName> findByAuthorityId(@Param("authorityId") Long authorityId);
}