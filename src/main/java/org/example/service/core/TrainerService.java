package org.example.service.core;

import org.example.entity.TrainerEntity;

import java.util.Optional;

public interface TrainerService {

    TrainerEntity create(TrainerEntity params);

    TrainerEntity update(TrainerEntity params);

    TrainerEntity select(Long trainerId);

    TrainerEntity selectByUsername(String username);

    Optional<TrainerEntity> findById(Long id);

    Optional<TrainerEntity> findByUsername(String username);
}
