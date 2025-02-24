package com.example.service;

import com.example.entity.TrainerEntity;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import java.util.List;
import java.util.Optional;

public interface TrainerService {

    Long updateWorkingHours(TrainerEntity trainerEntity,
                                     TrainerWorkingHoursUpdateStrategy trainerWorkingHoursUpdateStrategy);

    List<TrainerEntity> findAllByUsername(String username);

    List<TrainerEntity> findAll();

    Optional<TrainerEntity> findByUsernameAndMonthAndYear(String username, Integer month, Integer year);
}
