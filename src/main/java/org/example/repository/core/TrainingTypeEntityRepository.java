package org.example.repository.core;

import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;

public interface TrainingTypeEntityRepository extends CustomRepository<Long, TrainingTypeEntity> {

    TrainingTypeEntity getByTrainingType(TrainingType trainingType);
}
