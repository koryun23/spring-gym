package org.example.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
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
    public List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                                    String trainerUsername, Long trainingTypeId) {
        Assert.notNull(traineeUsername, "Trainee Username must not be null");
        LOGGER.info("Retrieving all Training Entities of a trainee({}) based on the following criteria - "
                + "from = {}, to = {}, trainerUsername = {}, trainingTypeId = {}",
            traineeUsername, from, to, trainerUsername, trainingTypeId);

        List<TrainingEntity> all = trainingDao.findAllByTraineeUsernameAndCriteria(traineeUsername, from, to,
            trainerUsername, trainingTypeId);

        LOGGER.info("Successfully retrieved all Training Entities of a trainee({}) based on the following criteria - "
                + "from = {}, to = {}, trainerUsername = {}, trainingTypeId = {}. Result of the query - {}",
            traineeUsername, from, to, trainerUsername, trainingTypeId, all);
        return all;
    }

    @Override
    public List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                                    String traineeUsername) {
        Assert.notNull(trainerUsername, "Trainer Username must not be null");
        LOGGER.info("Retrieving all Training Entities of a trainer({}) based on the following criteria - "
                + "from = {}, to = {}, traineeUsername = {}",
            trainerUsername, from, to, traineeUsername);

        List<TrainingEntity> all = trainingDao.findAllByTrainerUsernameAndCriteria(trainerUsername, from, to,
            traineeUsername);

        LOGGER.info("Successfully retrieved all Training Entities of a trainer({}) based on the following criteria - "
                + "from = {}, to = {}, traineeUsername = {}. Result of the query - {}",
            trainerUsername, from, to, traineeUsername, all);
        return all;
    }

    @Override
    public void deleteAllByTraineeUsername(String traineeUsername) {
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        LOGGER.info("Deleting all trainings of a trainee with a username of {}", traineeUsername);

        trainingDao.deleteAllByTraineeUsername(traineeUsername);

        LOGGER.info("Successfully deleted all trainings of a trainee with a username of {}", traineeUsername);
    }

    @Override
    public void deleteAllByTrainerUsername(String trainerUsername) {
        Assert.notNull(trainerUsername, "Trainer username must not be null");
        LOGGER.info("Deleting all trainings of a trainer with a username of {}", trainerUsername);

        trainingDao.deleteAllByTrainerUsername(trainerUsername);

        LOGGER.info("Successfully deleted all trainings of a trainer with a username of {}", trainerUsername);
    }


}
