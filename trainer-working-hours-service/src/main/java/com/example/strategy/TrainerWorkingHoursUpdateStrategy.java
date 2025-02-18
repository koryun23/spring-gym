package com.example.strategy;

import com.example.entity.TrainerEntity;

public interface TrainerWorkingHoursUpdateStrategy {

    Long updateTrainerWorkingHours(TrainerEntity trainerEntity);
}
