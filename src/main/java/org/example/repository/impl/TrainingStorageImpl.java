package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Training;
import org.example.entity.TrainingType;
import org.example.exception.TrainingNotFoundException;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.example.repository.core.TrainingStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainingStorageImpl implements TrainingStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerStorageImpl.class);

    private Map<Long, Training> inMemoryStorage;

    private FileStorage<Training> trainingFileStorage;

    public TrainingStorageImpl() {
        inMemoryStorage = new HashMap<>();
    }

    public TrainingStorageImpl(Map<Long, Training> map) {
        inMemoryStorage = map;
    }

    @Override
    public Training get(Long id) {
        Assert.notNull(id, "Training id must not be null");
        LOGGER.info("Retrieving a Training with an id of {}", id);
        Training training = inMemoryStorage.get(id);
        LOGGER.info("Successfully retrieved a Training with an id of {}, result - {}", id, training);
        if(training == null) throw new TrainingNotFoundException(id);
        return training;
    }

    @Override
    public Training add(Training training) {
        Assert.notNull(training, "Training must not be null");
        LOGGER.info("Adding a Training with an id of {} to the in-memory storage", training.getTrainingId());
        Training addedTraining = inMemoryStorage.put(training.getTrainingId(), training);
        LOGGER.info("Successfully added {} to the in-memory storage", addedTraining);
        trainingFileStorage.persist(inMemoryStorage);
        return training;
    }

    @Override
    public boolean remove(Long id) {
        Assert.notNull(id, "Training id must not be null");
        LOGGER.info("Removing a Training with an id of {} from the in-memory storage", id);
        Training removedTraining = inMemoryStorage.remove(id);
        LOGGER.info("Successfully removed {} from the in-memory storage", removedTraining);
        trainingFileStorage.persist(inMemoryStorage);
        return true;
    }

    @Override
    public Training update(Training training) {
        Assert.notNull(training, "Training id must noe be null");
        LOGGER.info("Updating a Training with an id of {}", training.getTrainingId());
        Training updatedTraining = inMemoryStorage.put(training.getTrainingId(), training);
        LOGGER.info("Successfully updated a Training with an id of {}, result - {}", training.getTrainingId(), updatedTraining);
        trainingFileStorage.persist(inMemoryStorage);
        return training;
    }

    @Autowired
    public void setTrainingFileStorage(FileStorage<Training> trainingFileStorage) {
        this.trainingFileStorage = trainingFileStorage;
    }

    @PostConstruct
    public void init() {
        this.inMemoryStorage = trainingFileStorage.parseMemoryFile();
    }
}
