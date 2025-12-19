package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.Authority;
import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query("SELECT a FROM Authority a WHERE " +
            "LOWER(a.authorityName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.authorityCode) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Authority> searchByNameOrCode(@Param("searchTerm") String searchTerm);
}