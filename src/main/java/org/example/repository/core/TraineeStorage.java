package org.example.repository.core;

import org.example.entity.Trainee;

import java.util.Optional;

public interface TraineeStorage extends Storage<Trainee> {

    Trainee getByUsername(String username);

    Optional<Trainee> findByUsername(String username);

    Optional<Trainee> findById(Long id);
}
