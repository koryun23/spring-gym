package org.example.service.core;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.example.entity.TrainerEntity;

public interface TrainerService {

    TrainerEntity create(TrainerEntity params);

    TrainerEntity update(TrainerEntity params);

    TrainerEntity select(Long trainerId);

    TrainerEntity selectByUsername(String username);

    Optional<TrainerEntity> findById(Long id);

    Optional<TrainerEntity> findByUsername(String username);

    List<TrainerEntity> findAllNotAssignedTo(String traineeUsername);

    Set<TrainerEntity> updateTrainersAssignedTo(String traineeUsername, Set<TrainerEntity> trainerEntityList);
}
