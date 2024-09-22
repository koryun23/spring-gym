package org.example.repository.core;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainerEntity;

public interface TrainerEntityRepository extends CustomRepository<Long, TrainerEntity> {

    Optional<TrainerEntity> findByUsername(String username);

    List<TrainerEntity> findAllTrainersNotAssignedTo(String traineeUsername);

}
