package org.example.repository.core;

import org.example.entity.TrainerEntity;

import java.util.Optional;

public interface TrainerStorage extends Storage<TrainerEntity> {

    TrainerEntity getByUsername(String username);

    Optional<TrainerEntity> findByUsername(String username);
}
