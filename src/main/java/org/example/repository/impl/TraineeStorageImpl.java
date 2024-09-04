package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.TraineeEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.repository.core.FileStorage;
import org.example.repository.core.TraineeStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.*;

public class TraineeStorageImpl implements TraineeStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeStorageImpl.class);

    private Map<Long, TraineeEntity> inMemoryStorage; // trainee id - trainee

    private FileStorage<TraineeEntity> traineeFileStorage;

    public TraineeStorageImpl(Map<Long, TraineeEntity> map) {
        Assert.notNull(map, "In memory storage must not be null");
        inMemoryStorage = map;
    }

    public TraineeStorageImpl() {
        inMemoryStorage = new HashMap<>();
    }

    @Override
    public TraineeEntity get(Long id) {
        LOGGER.info("Retrieving a TraineeEntity with an id of {} from the in-memory storage", id);
        Assert.notNull(id, "TraineeEntity Id must not be null");
        TraineeEntity trainee = inMemoryStorage.get(id);
        if(trainee == null) throw new TraineeNotFoundException(id);
        LOGGER.info("Successfully retrieved a TrainerEntity with an id of {}, result - {}", id, trainee);
        return trainee;
    }

    @Override
    public TraineeEntity add(TraineeEntity trainee) {
        LOGGER.info("Adding {} to the in-memory storage", trainee);
        Assert.notNull(trainee, "TraineeEntity must not be null");
        TraineeEntity addedTrainee = inMemoryStorage.put(trainee.getUserId(), trainee);
        LOGGER.info("Successfully added {} to the in-memory storage", addedTrainee);
        traineeFileStorage.persist(inMemoryStorage);
        return addedTrainee;
    }

    @Override
    public boolean remove(Long id) {
        LOGGER.info("Removing a TraineeEntity with an id of {} from the in-memory storage", id);
        Assert.notNull(id, "TraineeEntity id must not be null");
        if(!inMemoryStorage.containsKey(id)) throw new TraineeNotFoundException(id);
        TraineeEntity removedTrainee = inMemoryStorage.remove(id);
        LOGGER.info("Successfully removed {} from the in-memory storage", removedTrainee);
        traineeFileStorage.persist(inMemoryStorage);
        return true;
    }

    @Override
    public TraineeEntity update(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        LOGGER.info("Updating a TraineeEntity with an id of {}", trainee.getUserId());
        TraineeEntity updatedTrainee = inMemoryStorage.put(trainee.getUserId(), trainee);
        LOGGER.info("Successfully updated a TraineeEntity with an id of {}, final result - {}", trainee.getUserId(), updatedTrainee);
        traineeFileStorage.persist(inMemoryStorage);
        return updatedTrainee;
    }

    @Override
    public TraineeEntity getByUsername(String username) {
        LOGGER.info("Retrieving a TraineeEntity with a username of {}", username);
        Assert.notNull(username, "TraineeEntity username must not be null");
        Assert.hasText(username, "TraineeEntity username must not be empty");

        for (Map.Entry<Long, TraineeEntity> pair : inMemoryStorage.entrySet()) {
            TraineeEntity trainee = pair.getValue();
            if(trainee.getUsername().equals(username)) {
                LOGGER.info("Successfully retrieved a TraineeEntity with a username of {}, result - {}", username, trainee);
                return trainee;
            }
        }
        LOGGER.error("Failed to retrieve a TraineeEntity with a username of {}, throwing a TraineeNotFoundException", username);
        throw new TraineeNotFoundException(username);
    }

    @Override
    public Optional<TraineeEntity> findByUsername(String username) {
        LOGGER.info("Retrieving an optional of a TraineeEntity with a username of {}", username);
        Assert.notNull(username, "TraineeEntity username must not be null");
        Assert.hasText(username, "TraineeEntity username must not be empty");
        for(Map.Entry<Long, TraineeEntity> pair : inMemoryStorage.entrySet()) {
            TraineeEntity trainee = pair.getValue();
            if(trainee.getUsername().equals(username)) {
                Optional<TraineeEntity> optionalTrainee = Optional.of(trainee);
                LOGGER.info("Successfully retrieved an optional of a TraineeEntity with a username of {}, result - {}", username, optionalTrainee);
                return optionalTrainee;
            }
        }
        Optional<TraineeEntity> optionalTrainee = Optional.empty();
        LOGGER.info("Successfully retrieved an optional of a TraineeEntity with a username of {}, result - {}", username, optionalTrainee);
        return optionalTrainee;
    }

    @Override
    public Optional<TraineeEntity> findById(Long id) {
        LOGGER.info("Retrieving an optional of a TraineeEntity with an id of {}", id);
        Assert.notNull(id, "TraineeEntity id must not be null");

        TraineeEntity trainee = inMemoryStorage.get(id);
        Optional<TraineeEntity> optionalTrainee = Optional.ofNullable(trainee);
        LOGGER.info("Successfully retrieved an optional of a TraineeEntity with an id of {}, result - {}", id, optionalTrainee);
        return optionalTrainee;
    }

    @Autowired
    public void setTraineeFileStorage(FileStorage<TraineeEntity> traineeFileStorage) {
        this.traineeFileStorage = traineeFileStorage;
    }

    @PostConstruct
    public void init() {
        inMemoryStorage = traineeFileStorage.parseMemoryFile();
    }
}
