package org.example.service.impl;

import java.util.Optional;
import org.example.dao.core.TraineeDao;
import org.example.entity.TraineeEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.service.core.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    private TraineeDao traineeDao;

    @Override
    public TraineeEntity create(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeCreateParams must not be null");
        LOGGER.info("Creating a TraineeEntity based on TraineeCreateParams - {}", trainee);
        TraineeEntity createdTrainee = traineeDao.save(trainee);
        LOGGER.info("Successfully created a TraineeEntity based on TraineeCreateParams - {}, result - {}", trainee,
            trainee);
        return createdTrainee;
    }

    @Override
    public TraineeEntity update(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeCreateParams must not be null");
        LOGGER.info("Updating a TraineeEntity based on TraineeUpdateParams - {}", trainee);
        TraineeEntity updatedTrainee = traineeDao.update(trainee);
        LOGGER.info("Successfully updated a TraineeEntity based on TraineeUpdateParams - {}, result - {}", trainee,
            trainee);
        return updatedTrainee;
    }

    @Override
    public boolean delete(Long traineeId) {
        Assert.notNull(traineeId, "TraineeEntity id must not be null");
        LOGGER.info("Deleting a TraineeEntity with an id of {}", traineeId);
        boolean success = traineeDao.delete(traineeId);
        if (success) {
            LOGGER.info("Successfully deleted a trainee with an id of {}", traineeId);
        } else {
            LOGGER.error("Failed to delete a trainee with an id of {}", traineeId);
            throw new TraineeNotFoundException(traineeId);
        }
        return success;
    }

    @Override
    public TraineeEntity select(Long traineeId) {
        Assert.notNull(traineeId, "TraineeEntity id must not be null");
        LOGGER.info("Selecting a TraineeEntity with an id of {}", traineeId);
        TraineeEntity trainee = traineeDao.get(traineeId);
        LOGGER.info("Successfully selected a TraineeEntity with an id of {}, result - {}", traineeId, trainee);
        return trainee;
    }

    @Override
    public TraineeEntity selectByUsername(String username) {
        Assert.notNull(username, "TraineeEntity username must not be null");
        Assert.hasText(username, "TraineeEntity username must not be empty");
        LOGGER.info("Selecting a TraineeEntity with a username of {}", username);
        TraineeEntity trainee = traineeDao.getByUsername(username);
        LOGGER.info("Successfully selected a trainee with a username of {}, result - {}", username, trainee);
        return trainee;
    }

    @Override
    public Optional<TraineeEntity> findById(Long id) {
        Assert.notNull(id, "TraineeEntity id must not be null");
        LOGGER.info("Retrieved an optional TraineeEntity with an id of {}", id);
        Optional<TraineeEntity> optionalTrainee = traineeDao.findById(id);
        LOGGER.info("Successfully retrieved an optional TraineeEntity with an id of {}, result - {}", id,
            optionalTrainee);
        return optionalTrainee;
    }

    @Override
    public Optional<TraineeEntity> findByUsername(String username) {
        Assert.notNull(username, "TraineeEntity username must not be null");
        Assert.hasText(username, "TraineeEntity username must not be empty");
        LOGGER.info("Retrieved an optional TraineeEntity with a username of {}", username);
        Optional<TraineeEntity> optionalTrainee = traineeDao.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional TraineeEntity with an username of {}, result - {}", username,
            optionalTrainee);
        return optionalTrainee;
    }
}
