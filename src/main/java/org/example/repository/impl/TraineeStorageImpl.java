package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Trainee;
import org.example.exception.TraineeNotFoundException;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.example.repository.core.TraineeStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.*;
import java.util.*;

public class TraineeStorageImpl implements TraineeStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeStorageImpl.class);

    private Map<Long, Trainee> inMemoryStorage; // trainee id - trainee

    private FileStorage<Trainee> traineeFileStorage;

    public TraineeStorageImpl(Map<Long, Trainee> map) {
        Assert.notNull(map, "In memory storage must not be null");
        inMemoryStorage = map;
    }

    public TraineeStorageImpl() {
        inMemoryStorage = new HashMap<>();
    }

    @Override
    public Trainee get(Long id) {
        LOGGER.info("Retrieving a Trainee with an id of {} from the in-memory storage", id);
        Assert.notNull(id, "Trainee Id must not be null");
        Trainee trainee = inMemoryStorage.get(id);
        if(trainee == null) throw new TraineeNotFoundException(id);
        LOGGER.info("Successfully retrieved a Trainer with an id of {}, result - {}", id, trainee);
        return trainee;
    }

    @Override
    public Trainee add(Trainee trainee) {
        LOGGER.info("Adding {} to the in-memory storage", trainee);
        Assert.notNull(trainee, "Trainee must not be null");
        Trainee addedTrainee = inMemoryStorage.put(trainee.getUserId(), trainee);
        LOGGER.info("Successfully added {} to the in-memory storage", addedTrainee);
        traineeFileStorage.persist(inMemoryStorage);
        return addedTrainee;
    }

    @Override
    public boolean remove(Long id) {
        LOGGER.info("Removing a Trainee with an id of {} from the in-memory storage", id);
        Assert.notNull(id, "Trainee id must not be null");
        if(!inMemoryStorage.containsKey(id)) throw new TraineeNotFoundException(id);
        Trainee removedTrainee = inMemoryStorage.remove(id);
        LOGGER.info("Successfully removed {} from the in-memory storage", removedTrainee);
        traineeFileStorage.persist(inMemoryStorage);
        return true;
    }

    @Override
    public Trainee update(Trainee trainee) {
        Assert.notNull(trainee, "Trainee must not be null");
        LOGGER.info("Updating a Trainee with an id of {}", trainee.getUserId());
        Trainee updatedTrainee = inMemoryStorage.put(trainee.getUserId(), trainee);
        LOGGER.info("Successfully updated a Trainee with an id of {}, final result - {}", trainee.getUserId(), updatedTrainee);
        traineeFileStorage.persist(inMemoryStorage);
        return updatedTrainee;
    }

    @Override
    public Trainee getByUsername(String username) {
        LOGGER.info("Retrieving a Trainee with a username of {}", username);
        Assert.notNull(username, "Trainee username must not be null");
        Assert.hasText(username, "Trainee username must not be empty");

        for (Map.Entry<Long, Trainee> pair : inMemoryStorage.entrySet()) {
            Trainee trainee = pair.getValue();
            if(trainee.getUsername().equals(username)) {
                LOGGER.info("Successfully retrieved a Trainee with a username of {}, result - {}", username, trainee);
                return trainee;
            }
        }
        LOGGER.error("Failed to retrieve a Trainee with a username of {}, throwing a TraineeNotFoundException", username);
        throw new TraineeNotFoundException(username);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        LOGGER.info("Retrieving an optional of a Trainee with a username of {}", username);
        Assert.notNull(username, "Trainee username must not be null");
        Assert.hasText(username, "Trainee username must not be empty");
        for(Map.Entry<Long, Trainee> pair : inMemoryStorage.entrySet()) {
            Trainee trainee = pair.getValue();
            if(trainee.getUsername().equals(username)) {
                Optional<Trainee> optionalTrainee = Optional.of(trainee);
                LOGGER.info("Successfully retrieved an optional of a Trainee with a username of {}, result - {}", username, optionalTrainee);
                return optionalTrainee;
            }
        }
        Optional<Trainee> optionalTrainee = Optional.empty();
        LOGGER.info("Successfully retrieved an optional of a Trainee with a username of {}, result - {}", username, optionalTrainee);
        return optionalTrainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        LOGGER.info("Retrieving an optional of a Trainee with an id of {}", id);
        Assert.notNull(id, "Trainee id must not be null");

        Trainee trainee = inMemoryStorage.get(id);
        Optional<Trainee> optionalTrainee = Optional.of(trainee);
        LOGGER.info("Successfully retrieved an optional of a Trainee with an id of {}, result - {}", id, optionalTrainee);
        return optionalTrainee;
    }

    @Autowired
    public void setTraineeFileStorage(FileStorage<Trainee> traineeFileStorage) {
        this.traineeFileStorage = traineeFileStorage;
    }

    @PostConstruct
    public void init() {
        inMemoryStorage = traineeFileStorage.parseMemoryFile();
    }
}
