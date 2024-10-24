package org.example.service.impl;

import java.util.Optional;
import org.example.entity.TraineeEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.repository.core.TraineeEntityRepository;
import org.example.service.core.TraineeService;
import org.example.service.core.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private final TraineeEntityRepository traineeDao;
    private final UserService userService;

    public TraineeServiceImpl(TraineeEntityRepository traineeDao, UserService userService) {
        this.traineeDao = traineeDao;
        this.userService = userService;
    }

    @Override
    public TraineeEntity create(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeCreateParams must not be null");
        LOGGER.info("Creating a TraineeEntity based on TraineeCreateParams - {}", trainee);
        userService.create(trainee.getUser());
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
        traineeDao.deleteById(traineeId);
        LOGGER.info("Successfully deleted a trainee with an id of {}", traineeId);
        return true;
    }

    @Override
    public boolean delete(String username) {
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(username, "Username must not be empty");
        LOGGER.info("Deleting a Trainee with a username of {}", username);
        traineeDao.deleteByUsername(username);
        LOGGER.info("Successfully deleted a Trainee with a username of {}", username);
        return true;
    }

    @Override
    public TraineeEntity select(Long traineeId) {
        Assert.notNull(traineeId, "TraineeEntity id must not be null");
        LOGGER.info("Selecting a TraineeEntity with an id of {}", traineeId);
        TraineeEntity trainee =
            traineeDao.findById(traineeId).orElseThrow(() -> new TraineeNotFoundException(traineeId));
        LOGGER.info("Successfully selected a TraineeEntity with an id of {}, result - {}", traineeId, trainee);
        return trainee;
    }

    @Override
    public TraineeEntity selectByUsername(String username) {
        Assert.notNull(username, "TraineeEntity username must not be null");
        Assert.hasText(username, "TraineeEntity username must not be empty");
        LOGGER.info("Selecting a TraineeEntity with a username of {}", username);
        TraineeEntity trainee =
            traineeDao.findByUsername(username).orElseThrow(() -> new TraineeNotFoundException(username));
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
