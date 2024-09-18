package org.example.service.core;

import java.util.Optional;
import org.example.entity.TrainingTypeEntity;

public interface TrainingTypeService {

    TrainingTypeEntity create(TrainingTypeEntity trainingType);

    TrainingTypeEntity update(TrainingTypeEntity trainingType);

    boolean delete(Long id);

    TrainingTypeEntity get(Long id);

    Optional<TrainingTypeEntity> findById(Long id);
}