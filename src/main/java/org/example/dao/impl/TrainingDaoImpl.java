package org.example.dao.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.example.dao.core.TrainingDao;
import org.example.entity.TrainingEntity;
import org.example.exception.TrainingNotFoundException;
import org.example.repository.core.TrainingEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingDaoImpl implements TrainingDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDaoImpl.class);

    private TrainingEntityRepository trainingEntityRepository;

    @Autowired
    public void setStorage(TrainingEntityRepository storage) {
        this.trainingEntityRepository = storage;
    }

    @Override
    public TrainingEntity get(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving a TrainingEntity with an id of {}", id);
        TrainingEntity trainingEntity = trainingEntityRepository.findById(id)
            .orElseThrow(() -> new TrainingNotFoundException(id));
        LOGGER.info("Successfully retrieved a TrainingEntity with an id of {}, result - {}", id, trainingEntity);
        return trainingEntity;
    }

    @Override
    public TrainingEntity save(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        LOGGER.info("Saving {}", trainingEntity);
        TrainingEntity addedTrainingEntity = trainingEntityRepository.save(trainingEntity);
        LOGGER.info("Successfully saved {}", addedTrainingEntity);
        return addedTrainingEntity;
    }

    @Override
    public TrainingEntity update(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        LOGGER.info("Updating a TrainingEntity with an id of {}", trainingEntity.getId());
        TrainingEntity updatedTrainingEntity = trainingEntityRepository.save(trainingEntity);
        LOGGER.info("Successfully updated a TrainingEntity with an id of {}, result - {}",
            trainingEntity.getId(),
            updatedTrainingEntity);
        return updatedTrainingEntity;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Deleting a TrainingEntity with an id of {}", id);
        trainingEntityRepository.deleteById(id);
        LOGGER.info("Successfully deleted a TrainingEntity with an id of {}", id);
        return true;
    }

    @Override
    public Optional<TrainingEntity> findById(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainingEntity with an id of {}", id);
        Optional<TrainingEntity> optionalTraining = trainingEntityRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainingEntity with an id of {}, result - {}", id,
            optionalTraining);
        return optionalTraining;
    }

    @Override
    public List<TrainingEntity> findAll() {
        LOGGER.info("Retrieving a list of all Training Entities");
        List<TrainingEntity> all = trainingEntityRepository.findAll();
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

        List<TrainingEntity> all =
            trainingEntityRepository.findAllByTraineeUsernameAndCriteria(traineeUsername, from, to, trainerUsername,
                trainingTypeId);

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

        List<TrainingEntity> all =
            trainingEntityRepository.findAllByTrainerUsernameAndCriteria(trainerUsername, from, to, traineeUsername);

        LOGGER.info("Successfully retrieved all Training Entities of a trainer({}) based on the following criteria - "
                + "from = {}, to = {}, traineeUsername = {}. Result of the query - {}",
            trainerUsername, from, to, traineeUsername, all);
        return all;
    }
}
