package com.example.service.core;

import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import com.example.entity.TrainerEntity;
import java.util.List;

public interface TrainerService {

    TrainerEntity updateWorkingHours(TrainerEntity trainerEntity, TrainerWorkingHoursUpdateStrategy trainerWorkingHoursUpdateStrategy);

    List<TrainerEntity> findAllByUsername(String username);

    List<TrainerEntity> findAll();
}
