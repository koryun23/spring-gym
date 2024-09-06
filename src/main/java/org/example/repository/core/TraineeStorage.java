package org.example.repository.core;

import java.util.Optional;
import org.example.entity.TraineeEntity;

public interface TraineeStorage extends Storage<TraineeEntity> {

    TraineeEntity getByUsername(String username);

    Optional<TraineeEntity> findByUsername(String username);
}
