package org.example.repository;

import org.example.entity.training.TrainingType;
import org.example.entity.training.TrainingTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeEntityRepository extends JpaRepository<TrainingTypeEntity, Long> {

    TrainingTypeEntity getByTrainingType(TrainingType trainingType);
}
