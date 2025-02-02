package com.example.service.impl;

import com.example.entity.TrainerEntity;
import com.example.repository.TrainerRepository;
import com.example.service.core.TrainerService;
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
    public TrainerEntity addWorkingHours(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer entity must not be null");
        log.info("Adding working hours - {}", trainerEntity);

        Optional<TrainerEntity> optionalByCriteria =
            trainerRepository.findByUsernameAndMonthAndYear(trainerEntity.getTrainerUsername(),
                trainerEntity.getTrainingMonth(), trainerEntity.getTrainingYear());

        TrainerEntity savedTrainerEntity;

        if (optionalByCriteria.isPresent()) {
            TrainerEntity trainerEntityByCriteria = optionalByCriteria.get();
            trainerEntityByCriteria.setDuration(trainerEntityByCriteria.getDuration() + trainerEntity.getDuration());
            savedTrainerEntity = trainerRepository.save(trainerEntityByCriteria);
        } else {
            savedTrainerEntity = trainerRepository.save(trainerEntity);
        }

        log.info("Successfully added working hours - {}", savedTrainerEntity);
        return savedTrainerEntity;
    }

    @Override
    public TrainerEntity removeWorkingHours(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer Entity must not be null");
        log.info("Removing working hours - {}", trainerEntity);

        Optional<TrainerEntity> optionalTrainerEntity =
            trainerRepository.findByUsernameAndMonthAndYear(trainerEntity.getTrainerUsername(),
                trainerEntity.getTrainingMonth(), trainerEntity.getTrainingYear());
        TrainerEntity savedTrainerEntity;

        // TODO: throw exception
        if (optionalTrainerEntity.isEmpty()) {
            log.warn("Given trainer - {}, has no working hours registered", trainerEntity);
            return trainerEntity;
        }

        TrainerEntity trainerEntityByCriteria = optionalTrainerEntity.get();
        trainerEntityByCriteria.setDuration(trainerEntityByCriteria.getDuration() - trainerEntity.getDuration());
        savedTrainerEntity = trainerRepository.save(trainerEntityByCriteria);
        log.info("Successfully removed working hours, {}", trainerEntity);
        return savedTrainerEntity;
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
