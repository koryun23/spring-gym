package org.example.service.core;

import org.example.entity.Trainer;

import java.util.Optional;

public interface TrainerService {

    Trainer create(Trainer params);

    Trainer update(Trainer params);

    Trainer select(Long trainerId);

    Trainer selectByUsername(String username);

    Optional<Trainer> findById(Long id);

    Optional<Trainer> findByUsername(String username);
}
