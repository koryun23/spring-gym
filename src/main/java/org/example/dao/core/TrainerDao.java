package org.example.dao.core;

import org.example.entity.TrainerEntity;

import java.util.Optional;

public interface TrainerDao extends Dao<TrainerEntity> {

    TrainerEntity getByUsername(String username);

    Optional<TrainerEntity> findByUsername(String username);
}
