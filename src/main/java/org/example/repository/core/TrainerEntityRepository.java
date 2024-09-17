package org.example.repository.core;

import java.util.Optional;
import org.example.entity.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerEntityRepository extends JpaRepository<TrainerEntity, Long> {

    Optional<TrainerEntity> findByUsername(String username);
}
