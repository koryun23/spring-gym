package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.TrainingEntity;
import org.example.exception.TrainingNotFoundException;
import org.example.repository.core.FileStorage;
import org.example.repository.core.TrainingStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component
public class TrainingStorageImpl implements TrainingStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerStorageImpl.class);

    private Map<Long, TrainingEntity> inMemoryStorage;

    private FileStorage<TrainingEntity> trainingFileStorage;

    public TrainingStorageImpl() {
        inMemoryStorage = new HashMap<>();
    }

    public TrainingStorageImpl(Map<Long, TrainingEntity> map) {
        inMemoryStorage = map;
    }

    @Override
    public TrainingEntity get(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving a TrainingEntity with an id of {}", id);
        TrainingEntity trainingEntity = inMemoryStorage.get(id);
        LOGGER.info("Successfully retrieved a TrainingEntity with an id of {}, result - {}", id, trainingEntity);
        if(trainingEntity == null) throw new TrainingNotFoundException(id);
        return trainingEntity;
    }

    @Override
    public TrainingEntity add(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        LOGGER.info("Adding a TrainingEntity with an id of {} to the in-memory storage", trainingEntity.getTrainingId());
        TrainingEntity addedTrainingEntity = inMemoryStorage.put(trainingEntity.getTrainingId(), trainingEntity);
        LOGGER.info("Successfully added {} to the in-memory storage", addedTrainingEntity);
        trainingFileStorage.persist(inMemoryStorage);
        return trainingEntity;
    }

    @Override
    public boolean remove(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Removing a TrainingEntity with an id of {} from the in-memory storage", id);
        TrainingEntity removedTrainingEntity = inMemoryStorage.remove(id);
        LOGGER.info("Successfully removed {} from the in-memory storage", removedTrainingEntity);
        trainingFileStorage.persist(inMemoryStorage);
        return true;
    }

    @Override
    public TrainingEntity update(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity id must noe be null");
        LOGGER.info("Updating a TrainingEntity with an id of {}", trainingEntity.getTrainingId());
        TrainingEntity updatedTrainingEntity = inMemoryStorage.put(trainingEntity.getTrainingId(), trainingEntity);
        LOGGER.info("Successfully updated a TrainingEntity with an id of {}, result - {}", trainingEntity.getTrainingId(),
            updatedTrainingEntity);
        trainingFileStorage.persist(inMemoryStorage);
        return trainingEntity;
    }

    @Override
    public Optional<TrainingEntity> findById(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainingEntity with an id of {}", id);
        Optional<TrainingEntity> optionalTraining = Optional.ofNullable(inMemoryStorage.get(id));
        LOGGER.info("Successfully retrieved an optional TrainingEntity with an id of {}", id);
        return optionalTraining;
    }

    @Autowired
    public void setTrainingFileStorage(FileStorage<TrainingEntity> trainingFileStorage) {
        this.trainingFileStorage = trainingFileStorage;
    }

    @PostConstruct
    public void init() {
        this.inMemoryStorage = trainingFileStorage.parseMemoryFile();
    }
}
