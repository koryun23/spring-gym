package org.example.service.core.training;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;

public interface TrainingTypeService {

    TrainingTypeEntity create(TrainingTypeEntity trainingType);

    TrainingTypeEntity update(TrainingTypeEntity trainingType);

    boolean delete(Long id);

    TrainingTypeEntity get(Long id);

    Optional<TrainingTypeEntity> findById(Long id);

    List<TrainingTypeEntity> findAll();

    Optional<TrainingTypeEntity> findByTrainingType(TrainingType trainingType);
}
