package org.example.dao.core;

import org.example.entity.Trainee;

import java.util.Optional;

public interface TraineeDao extends Dao<Trainee> {

    Trainee getByUsername(String username);

    Optional<Trainee> findByUsername(String username);

    Optional<Trainee> findById(Long id);
}
