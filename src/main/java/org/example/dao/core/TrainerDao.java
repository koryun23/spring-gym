package org.example.dao.core;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainerEntity;

public interface TrainerDao extends Dao<TrainerEntity> {

    TrainerEntity getByUsername(String username);

    Optional<TrainerEntity> findByUsername(String username);

    List<TrainerEntity> findAllTrainersNotAssignedTo(String traineeUsername);
}
