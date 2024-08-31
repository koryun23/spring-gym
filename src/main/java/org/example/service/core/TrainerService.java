package org.example.service.core;

import org.example.entity.Trainer;
import org.example.service.params.TrainerCreateParams;
import org.example.service.params.TrainerUpdateParams;

import java.util.Optional;

public interface TrainerService {

    Trainer create(TrainerCreateParams params);

    Trainer update(TrainerUpdateParams params);

    Trainer select(Long trainerId);

    Trainer selectByUsername(String username);

    Optional<Trainer> findById(Long id);

    Optional<Trainer> findByUsername(String username);
}
