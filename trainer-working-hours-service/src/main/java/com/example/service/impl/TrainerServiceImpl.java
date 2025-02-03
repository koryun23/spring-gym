package com.example.service.impl;

import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import com.example.entity.TrainerEntity;
import com.example.repository.TrainerRepository;
import com.example.service.core.TrainerService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }


    @Override
    public TrainerEntity updateWorkingHours(TrainerEntity trainerEntity, TrainerWorkingHoursUpdateStrategy strategy) {
        Assert.notNull(trainerEntity, "Trainer Entity must not be null");
        log.info("Updating working hours of {}", trainerEntity.getTrainerUsername());
        TrainerEntity updatedTrainerEntity = strategy.updateTrainerWorkingHours(trainerEntity);
        log.info("Updated working hours of {}, new value - {}", trainerEntity.getTrainerUsername(),
            updatedTrainerEntity.getDuration());
        return updatedTrainerEntity;
    }

    @Override
    public List<TrainerEntity> findAllByUsername(String username) {
        Assert.notNull(username, "Trainer username must not be null");
        Assert.hasText(username, "Trainer username must not be empty");
        log.info("Retrieving all trainer entities with a username of {}", username);

        List<TrainerEntity> allByTrainerUsername = trainerRepository.findAllByTrainerUsername(username);

        log.info("Successfully retrieved all trainer entities with a username of {}, result - {}", username,
            allByTrainerUsername);

        return allByTrainerUsername;
    }

    @Override
    public List<TrainerEntity> findAll() {

        log.info("Retrieving all registered trainers and their working hours");

        List<TrainerEntity> all = trainerRepository.findAll();

        log.info("Successfully retrieved all registered trainers and their working hours, {}", all);

        return all;
    }
}
