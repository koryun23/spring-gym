package com.example.service;

import com.example.entity.TrainerEntity;
import com.example.repository.TrainerRepository;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
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
        Assert.notNull(strategy, "Trainer Working Hours Update Strategy must not be null");
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
        log.info("Retrieving all trainers with a username of {} and their working hours", username);

        List<TrainerEntity> allByTrainerUsername = trainerRepository.findAllByTrainerUsername(username);

        log.info("Successfully retrieved all trainers with a username of {} and their working hours, result - {}",
            username, allByTrainerUsername);

        return allByTrainerUsername;
    }

    @Override
    public List<TrainerEntity> findAllByUsernameAndMonthAndYear(String username, Long month, Long year) {
        log.info("Retrieving all registered trainers and their working hours by username, month = {}, year = {}", month,
            year);

        // TODO: change parameter type from long to int to avoid the unnecessary conversions
        List<TrainerEntity> all =
            trainerRepository.findAllByUsernameAndMonthAndYear(username, Math.toIntExact(month), Math.toIntExact(year));

        log.info(
            "Successfully retrieved all registered trainers and their working hours by username, month = {}, year = {}",
            month, year);

        return all;
    }

    @Override
    public List<TrainerEntity> findAll() {

        log.info("Retrieving all registered trainers and their working hours");

        List<TrainerEntity> all = trainerRepository.findAll();

        log.info("Successfully retrieved all registered trainers and their working hours, {}", all);

        return all;
    }
}
