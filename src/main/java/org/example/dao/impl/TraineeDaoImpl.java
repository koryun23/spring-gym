package org.example.dao.impl;

import java.util.Optional;
import org.example.dao.core.TraineeDao;
import org.example.entity.TraineeEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.repository.TraineeEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeDaoImpl implements TraineeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);

    private TraineeEntityRepository traineeEntityRepository;

    @Autowired
    public void setTraineeEntityRepository(TraineeEntityRepository traineeEntityRepository) {
        this.traineeEntityRepository = traineeEntityRepository;
    }

    @Override
    public TraineeEntity get(Long id) {
        Assert.notNull(id, "TraineeEntity id must not be null");
        LOGGER.info("Retrieving a TraineeEntity with an id of {}", id);
        TraineeEntity trainee = traineeEntityRepository.findById(id)
            .orElseThrow(() -> new TraineeNotFoundException(id));
        LOGGER.info("Successfully retrieved a TraineeEntity with an id of {}, result - {}", id, trainee);
        return trainee;
    }

    @Override
    public TraineeEntity save(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        LOGGER.info("Saving {}", trainee);
        TraineeEntity addedTrainee = traineeEntityRepository.save(trainee);
        LOGGER.info("Successfully saved {}", trainee);
        return addedTrainee;
    }

    @Override
    public TraineeEntity update(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        LOGGER.info("Updating a TraineeEntity with an id of {}", trainee.getId());
        TraineeEntity updatedTrainee = traineeEntityRepository.save(trainee);
        LOGGER.info("Successfully updated a TraineeEntity with an id of {}, result - {}", trainee.getId(),
            updatedTrainee);
        return updatedTrainee;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "TraineeEntity Id must not be null");
        LOGGER.info("Deleting a trainee with an id of {}", id);
        traineeEntityRepository.deleteById(id);
        LOGGER.info("Successfully removed a trainee with an id of {}", id);
        return true;
    }

    @Override
    public TraineeEntity getByUsername(String username) {
        Assert.notNull(username, "TraineeEntity username must not be null");
        Assert.hasText(username, "TraineeEntity username must not be empty");
        LOGGER.info("Retrieving a TraineeEntity with a username of {}", username);
        TraineeEntity trainee = traineeEntityRepository.findByUsername(username)
            .orElseThrow(() -> new TraineeNotFoundException(username));
        LOGGER.info("Successfully retrieved a TraineeEntity with a username of {}, result - {}", username, trainee);
        return trainee;
    }

    @Override
    public Optional<TraineeEntity> findByUsername(String username) {
        Assert.notNull(username, "TraineeEntity username must not be null");
        Assert.hasText(username, "TraineeEntity username must not be empty");
        LOGGER.info("Retrieving an optional TraineeEntity with a username of {}", username);
        Optional<TraineeEntity> optionalTrainee = traineeEntityRepository.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional TraineeEntity with a username of {}, result - {}", username,
            optionalTrainee);
        return optionalTrainee;
    }

    @Override
    public Optional<TraineeEntity> findById(Long id) {
        Assert.notNull(id, "TraineeEntity id must not be null");
        LOGGER.info("Retrieving an optional TraineeEntity with an id of {}", id);
        Optional<TraineeEntity> optionalTrainee = traineeEntityRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional TraineeEntity with an id {}, result - {}", id, optionalTrainee);
        return optionalTrainee;
    }
}
