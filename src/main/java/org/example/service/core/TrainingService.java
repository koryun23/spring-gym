package org.example.service.core;

import org.example.entity.Training;

import java.util.Optional;

public interface TrainingService {

    Training create(Training params);

    Training select(Long id);

    Optional<Training> findById(Long id);
}
