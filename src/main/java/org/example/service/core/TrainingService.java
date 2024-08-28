package org.example.service.core;

import org.example.entity.Training;
import org.example.service.params.TrainingCreateParams;

public interface TrainingService {

    Training create(TrainingCreateParams params);

    Training select(Long id);
}
