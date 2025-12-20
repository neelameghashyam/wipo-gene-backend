package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.UpovAuthority;

@Repository
public interface UpovAuthorityRepository extends JpaRepository<UpovAuthority, Long> {
}