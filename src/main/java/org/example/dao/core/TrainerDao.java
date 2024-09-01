package org.example.dao.core;

import org.example.entity.Trainer;

import java.util.Optional;

public interface TrainerDao extends Dao<Trainer> {

    Trainer getByUsername(String username);

    Optional<Trainer> findByUsername(String username);
}
