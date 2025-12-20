package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.AuthorityTypes;

import java.util.List;

@Repository
public interface AuthorityTypesRepository extends JpaRepository<AuthorityTypes, Long> {
    
    @Query("SELECT at FROM AuthorityTypes at " +
           "LEFT JOIN FETCH at.authorityType " +
           "WHERE at.authority.authorityId = :authorityId")
    List<AuthorityTypes> findByAuthorityIdWithType(@Param("authorityId") Long authorityId);
}