package org.example.repository.core;

import org.example.entity.TraineeEntity;

import java.util.Optional;

public interface TraineeStorage extends Storage<TraineeEntity> {

    TraineeEntity getByUsername(String username);

    Optional<TraineeEntity> findByUsername(String username);
}
