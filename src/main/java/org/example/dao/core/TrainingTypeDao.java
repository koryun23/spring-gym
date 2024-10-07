package org.example.dao.core;

import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;

public interface TrainingTypeDao extends Dao<TrainingTypeEntity> {
    TrainingTypeEntity getByTrainingType(TrainingType trainingType);
}
