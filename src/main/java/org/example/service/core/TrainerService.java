package org.example.service.core;

import org.example.entity.Trainer;
import org.example.service.params.TrainerCreateParams;
import org.example.service.params.TrainerUpdateParams;

public interface TrainerService {

    Trainer create(TrainerCreateParams params);

    Trainer update(TrainerUpdateParams params);

    Trainer select(Long trainerId);
}
