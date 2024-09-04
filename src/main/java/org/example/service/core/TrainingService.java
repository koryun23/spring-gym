package org.example.service.core;

import org.example.entity.TrainingEntity;

import java.util.Optional;

public interface TrainingService {

    TrainingEntity create(TrainingEntity params);

    TrainingEntity select(Long id);

    Optional<TrainingEntity> findById(Long id);
}
