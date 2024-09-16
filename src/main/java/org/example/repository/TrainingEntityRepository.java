package org.example.repository;

import org.example.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingEntityRepository extends JpaRepository<TrainingEntity, Long> {
}
