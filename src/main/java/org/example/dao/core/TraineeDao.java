package org.example.dao.core;

import org.example.entity.TraineeEntity;

import java.util.Optional;

public interface TraineeDao extends Dao<TraineeEntity> {

    TraineeEntity getByUsername(String username);

    Optional<TraineeEntity> findByUsername(String username);
}
