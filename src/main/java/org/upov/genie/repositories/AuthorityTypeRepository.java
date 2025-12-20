package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.AuthorityType;

@Repository
public interface AuthorityTypeRepository extends JpaRepository<AuthorityType, Long> {
}