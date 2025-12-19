package org.upov.genie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.upov.genie.domain.entities.GenieEntity;
import java.util.List;
import java.util.Optional;

public interface GenieRepository extends JpaRepository<GenieEntity, String> {

    Optional<GenieEntity> findByGenieId(String genieId);

  @Query("SELECT g FROM GenieEntity g WHERE LOWER(g.upovCode) LIKE LOWER(CONCAT('%', :q, '%'))")
List<GenieEntity> searchByCodeOrName(String q);
}