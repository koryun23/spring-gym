package org.example.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.example.dao.core.TrainingDao;
import org.example.entity.TrainingEntity;
import org.example.service.core.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    @Autowired
    private TrainingDao trainingDao;

    @Override
    public TrainingEntity create(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingCreateParams must not be null");
        LOGGER.info("Creating a TrainingEntity according to the TrainingCreateParams - {}", trainingEntity);
        TrainingEntity createdTrainingEntity = trainingDao.save(trainingEntity);
        LOGGER.info(
            "Successfully created a TrainingEntity according to the TrainingEntity Create Params - {}, result - {}",
            trainingEntity, createdTrainingEntity);
        return createdTrainingEntity;
    }

    @Override
    public TrainingEntity select(Long id) {
        Assert.notNull(id, "TrainingEntity Id must not be null");
        LOGGER.info("Selecting a TrainingEntity with an id of {}", id);
        TrainingEntity trainingEntity = trainingDao.get(id);
        LOGGER.info("Successfully selected a TrainingEntity with an id of {}, result - {}", id, trainingEntity);
        return trainingEntity;
    }

    @Override
    public Optional<TrainingEntity> findById(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainingEntity with an id of {}", id);
        Optional<TrainingEntity> optionalTraining = trainingDao.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainingEntity with an id of {}, result - {}", id,
            optionalTraining);
        return optionalTraining;
    }

    @Override
    public List<TrainingEntity> findAll() {
        LOGGER.info("Retrieving a list of all TrainingEntities");
        List<TrainingEntity> all = trainingDao.findAll();
        LOGGER.info("Successfully retrieved a list of all Training Entities, result - {}", all);
        return all;
    }

    @Override
    public List<TrainingEntity> findAllByTrainee(String traineeUsername) {
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        Assert.hasText(traineeUsername, "Trainee username must not be empty");
        LOGGER.info("Retrieving all trainings of a trainee with a username of {}", traineeUsername);
        List<TrainingEntity> all = findAll().stream()
            .filter(trainingEntity -> trainingEntity.getTrainee().getUser().getUsername().equals(traineeUsername))
            .toList();
        LOGGER.info("Successfully retrieved all trainings of a trainee with a username of {}, result - {}", traineeUsername, all);
        return all;
    }

    @Override
    public List<TrainingEntity> findAllByTrainer(String trainerUsername) {
        Assert.notNull(trainerUsername, "Trainee username must not be null");
        Assert.hasText(trainerUsername, "Trainee username must not be empty");
        LOGGER.info("Retrieving all trainings of a trainer with a username of {}", trainerUsername);
        List<TrainingEntity> all = findAll().stream()
            .filter(trainingEntity -> trainingEntity.getTrainer().getUser().getUsername().equals(trainerUsername))
            .toList();
        LOGGER.info("Successfully retrieved all trainings of a trainer with a username of {}, result - {}", trainerUsername, all);
        return all;
    }

    @Override
    public List<TrainingEntity> findAllByTraineeTrainer(String traineeUsername, String trainerUsername) {
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        Assert.hasText(traineeUsername, "Trainee username must not be empty");
        Assert.notNull(trainerUsername, "Trainee username must not be null");
        Assert.hasText(trainerUsername, "Trainee username must not be empty");
        LOGGER.info("Retrieving all trainings of a trainee({}) with a trainer({})", traineeUsername, traineeUsername);

        List<TrainingEntity> all = findAll().stream()
            .filter(trainingEntity -> trainingEntity.getTrainee().getUser().getUsername().equals(traineeUsername) &&
                trainingEntity.getTrainer().getUser().getUsername().equals(trainerUsername))
            .toList();

        LOGGER.info("Successfully retrieved all trainings of a trainee({}) with a trainer({}), result - {}", traineeUsername, trainerUsername, all);
        return all;
    }

    @Override
    public List<TrainingEntity> findAllByTraineeDate(String traineeUsername, Date from, Date to) {
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        Assert.hasText(traineeUsername, "Trainee username must not be empty");
        Assert.notNull(from, "Starting date must not be null");
        Assert.notNull(to, "Ending date must not be null");
        LOGGER.info("Retrieving all trainings with a trainee({}) between {} and {}", traineeUsername, from, to);

        List<TrainingEntity> all = findAll().stream()
            .filter(trainingEntity -> trainingEntity.getTrainee().getUser().getUsername().equals(traineeUsername) &&
                trainingEntity.getDate().compareTo(from) >= 0 && trainingEntity.getDate().compareTo(to) <= 0)
            .toList();

        LOGGER.info("Successfully retrieved all trainings with a trainee({}) between {} and {}, result - {}",
            traineeUsername, from, to, all);

        return all;
    }

    @Override
    public List<TrainingEntity> findAllByTrainerDate(String trainerUsername, Date from, Date to) {
        Assert.notNull(trainerUsername, "Trainer username must not be null");
        Assert.hasText(trainerUsername, "Trainer username must not be empty");
        Assert.notNull(from, "Starting date must not be null");
        Assert.notNull(to, "Ending date must not be null");
        LOGGER.info("Retrieving all trainings with a trainer({}) between {} and {}", trainerUsername, from, to);

        List<TrainingEntity> all = findAll().stream()
            .filter(trainingEntity -> trainingEntity.getTrainer().getUser().getUsername().equals(trainerUsername) &&
                trainingEntity.getDate().compareTo(from) >= 0 && trainingEntity.getDate().compareTo(to) <= 0)
            .toList();

        LOGGER.info("Successfully retrieved all trainings with a trainer({}) between {} and {}, result - {}",
            trainerUsername, from, to, all);

        return all;
    }

    @Override
    public List<TrainingEntity> findAllByTraineeTrainerDate(String traineeUsername, Date from, Date to,
                                                            String trainerUsername) {
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        Assert.hasText(traineeUsername, "Trainee username must not be empty");
        Assert.notNull(trainerUsername, "Trainee username must not be null");
        Assert.hasText(trainerUsername, "Trainee username must not be empty");
        Assert.notNull(from, "Starting date must not be null");
        Assert.notNull(to, "Ending date must not be null");
        LOGGER.info("Retrieving all trainings of a trainee({}) with trainer({}) between {} and {}",
            traineeUsername, trainerUsername, from, to);

        List<TrainingEntity> all = findAll().stream()
            .filter(trainingEntity -> trainingEntity.getTrainee().getUser().getUsername().equals(traineeUsername) &&
                trainingEntity.getTrainer().getUser().getUsername().equals(trainerUsername) &&
                trainingEntity.getDate().compareTo(from) >= 0 && trainingEntity.getDate().compareTo(to) <= 0)
            .toList();

        LOGGER.info("Successfully retrieved all trainings of a trainee({}) with trainer({}) between {} and {}, result - {}",
            traineeUsername, trainerUsername, from, to, all);
        return all;
    }
}
