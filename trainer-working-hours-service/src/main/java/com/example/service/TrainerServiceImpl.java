package com.example.service;

import com.example.entity.TrainerEntity;
import com.example.repository.TrainerRepository;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import java.util.List;
import java.util.Optional;
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
    public Long updateWorkingHours(TrainerEntity trainerEntity, TrainerWorkingHoursUpdateStrategy strategy) {
        Assert.notNull(trainerEntity, "Trainer Entity must not be null");
        Assert.notNull(strategy, "Trainer Working Hours Update Strategy must not be null");
        log.info("Updating working hours of {}", trainerEntity.getTrainerUsername());
        Long updatedDuration = strategy.updateTrainerWorkingHours(trainerEntity);
        log.info("Updated working hours of {}, new value - {}", trainerEntity.getTrainerUsername(),
            updatedDuration);
        return updatedDuration;
    }

    @Override
    public List<TrainerEntity> findAllByUsername(String username) {
        Assert.notNull(username, "Trainer username must not be null");
        Assert.hasText(username, "Trainer username must not be empty");
        log.info("Retrieving all trainers with a username of {} and their working hours", username);

        List<TrainerEntity> allByTrainerUsername = trainerRepository.findAllByTrainerUsername(username);

        log.info("Successfully retrieved all trainers with a username of {} and their working hours, result - {}",
            username, allByTrainerUsername);

        return allByTrainerUsername;
    }

    @Override
    public List<TrainerEntity> findAll() {

        log.info("Retrieving all registered trainers and their working hours");

        List<TrainerEntity> all = trainerRepository.findAll();

        log.info("Successfully retrieved all registered trainers and their working hours, {}", all);

        return all;
    }

    @Override
    public Optional<TrainerEntity> findByUsernameAndMonthAndYear(String username, Integer month, Integer year) {
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be in the range from 1 to 12");
        }
        if (year < 0) {
            throw new IllegalArgumentException("Year must be a positive integer");
        }
        log.info("Retrieving an optional registered trainer and their working hours by username, month({}), year({})",
            month,
            year);

        Optional<TrainerEntity> optionalTrainerEntity =
            trainerRepository.findByTrainerUsernameAndTrainingMonthAndTrainingYear(username, month, year);

        log.info(
            "Successfully retrieved an optional registered trainer and their working hours by username, month({}), year({}), result - {}",
            month, year, optionalTrainerEntity);
        return optionalTrainerEntity;
    }
}
