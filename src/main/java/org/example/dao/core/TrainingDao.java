package org.example.dao.core;

import org.example.entity.Training;

import java.util.Optional;

public interface TrainingDao extends Dao<Training> {

    Training getByUsername(String username);

    Optional<Training> findByUsername(String username);

    Optional<Training> findById(Long id);
}
