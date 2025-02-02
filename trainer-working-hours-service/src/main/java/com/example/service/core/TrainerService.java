package com.example.service.core;

import com.example.entity.TrainerEntity;
import java.util.List;

public interface TrainerService {

    TrainerEntity create(TrainerEntity trainerEntity);

    List<TrainerEntity> findAllByUsername(String username);

    List<TrainerEntity> findAll();
}
