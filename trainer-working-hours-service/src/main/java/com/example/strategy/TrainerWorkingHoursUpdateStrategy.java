package com.example.strategy;

import com.example.entity.TrainerEntity;

public interface TrainerWorkingHoursUpdateStrategy {

    TrainerEntity updateTrainerWorkingHoursAndGet(TrainerEntity trainerEntity);
}
