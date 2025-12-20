package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upov.genie.domain.entities.Guideline;

@Repository
public interface GuidelineRepository extends JpaRepository<Guideline, Long> {
}