package org.example.service.core.training;

import java.util.List;
import java.util.Optional;
import org.example.entity.training.TrainingTypeEntity;

public interface TrainingTypeService {

    TrainingTypeEntity get(Long id);

    Optional<TrainingTypeEntity> findById(Long id);

    List<TrainingTypeEntity> findAll();
}
