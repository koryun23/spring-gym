package org.example.service.core;

import java.util.Optional;
import org.example.entity.TrainingEntity;

public interface TrainingService {

    TrainingEntity create(TrainingEntity params);

    TrainingEntity select(Long id);

    Optional<TrainingEntity> findById(Long id);
}
