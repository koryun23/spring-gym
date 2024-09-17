package org.example.repository.core;

import org.example.entity.TrainingTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeEntityRepository extends JpaRepository<TrainingTypeEntity, Long> {
}
