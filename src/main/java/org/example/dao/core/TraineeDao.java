package org.example.dao.core;

import java.util.Optional;
import org.example.entity.TraineeEntity;

public interface TraineeDao extends Dao<TraineeEntity> {

    TraineeEntity getByUsername(String username);

    Optional<TraineeEntity> findByUsername(String username);

    boolean deleteByUsername(String username);
}
