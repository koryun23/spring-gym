package org.example.service.core;

import java.util.Optional;
import org.example.entity.TraineeEntity;

public interface TraineeService {

    TraineeEntity create(TraineeEntity params);

    TraineeEntity update(TraineeEntity params);

    boolean delete(Long traineeId);

    TraineeEntity select(Long traineeId);

    TraineeEntity selectByUsername(String username);

    Optional<TraineeEntity> findById(Long id);

    Optional<TraineeEntity> findByUsername(String username);
}
