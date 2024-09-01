package org.example.service.core;

import org.example.entity.Training;
import org.example.service.params.TrainingCreateParams;

import java.util.Optional;

public interface TrainingService {

    Training create(TrainingCreateParams params);

    Training select(Long id);

    Optional<Training> findById(Long id);
}
