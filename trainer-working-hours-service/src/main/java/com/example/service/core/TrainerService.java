package com.example.service.core;

import com.example.entity.TrainerEntity;
import java.util.List;

public interface TrainerService {

    TrainerEntity addWorkingHours(TrainerEntity trainerEntity);

    TrainerEntity removeWorkingHours(TrainerEntity trainerEntity);

    List<TrainerEntity> findAllByUsername(String username);

    List<TrainerEntity> findAll();
}
