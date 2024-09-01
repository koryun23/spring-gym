package org.example.repository.core;

import org.example.entity.Trainer;

import java.util.Optional;

public interface TrainerStorage extends Storage<Trainer> {

    Trainer getByUsername(String username);

    Optional<Trainer> findByUsername(String username);
}
