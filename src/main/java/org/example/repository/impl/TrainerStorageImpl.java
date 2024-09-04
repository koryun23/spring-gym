package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.TrainerEntity;
import org.example.exception.TrainerNotFoundException;
import org.example.repository.core.FileStorage;
import org.example.repository.core.TrainerStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TrainerStorageImpl implements TrainerStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerStorageImpl.class);

    private Map<Long, TrainerEntity> inMemoryStorage;

    private FileStorage<TrainerEntity> trainerFileStorage;

    public TrainerStorageImpl(Map<Long, TrainerEntity> inMemoryStorage) {
        Assert.notNull(inMemoryStorage, "In-memory storage must not be null");
        this.inMemoryStorage = inMemoryStorage;
    }

    public TrainerStorageImpl() {
        this.inMemoryStorage = new HashMap<>();
    }

    @Override
    public TrainerEntity get(Long id) {
        LOGGER.info("Retrieving a TrainerEntity with an id of {} from the in-memory storage", id);
        Assert.notNull(id, "TrainerEntity id must not be null");
        TrainerEntity trainerEntity = inMemoryStorage.get(id);
        if(trainerEntity == null) throw new TrainerNotFoundException(id);
        LOGGER.info("Successfully retrieved a TrainerEntity with an id of {}, result - {}", id, trainerEntity);
        return trainerEntity;
    }

    @Override
    public TrainerEntity add(TrainerEntity trainerEntity) {
        LOGGER.info("Adding {} to the in-memory storage", trainerEntity);
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        TrainerEntity addedTrainerEntity = inMemoryStorage.put(trainerEntity.getUserId(), trainerEntity);
        LOGGER.info("Successfully added {} to the in-memory storage", addedTrainerEntity);
        trainerFileStorage.persist(inMemoryStorage);
        return trainerEntity;
    }

    @Override
    public boolean remove(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Removing a TrainerEntity with an id of {} from the in-memory storage", id);
        TrainerEntity removedTrainerEntity = inMemoryStorage.remove(id);
        LOGGER.info("Successfully removed {} from the in-memory storage", removedTrainerEntity);
        trainerFileStorage.persist(inMemoryStorage);
        return true;
    }

    @Override
    public TrainerEntity update(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        LOGGER.info("Updating a TrainerEntity with an id of {}", trainerEntity.getUserId());
        TrainerEntity updatedTrainerEntity = inMemoryStorage.put(trainerEntity.getUserId(), trainerEntity);
        LOGGER.info("Successfully updated a TrainerEntity with an id of {}, final result - {}", trainerEntity.getUserId(),
            updatedTrainerEntity);
        trainerFileStorage.persist(inMemoryStorage);
        return updatedTrainerEntity;
    }

    @Override
    public TrainerEntity getByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Retrieving a TrainerEntity with a username of {}", username);
        for(Map.Entry<Long, TrainerEntity> pair : inMemoryStorage.entrySet()) {
            TrainerEntity trainerEntity = pair.getValue();
            if(trainerEntity.getUsername().equals(username)) {
                LOGGER.info("Successfully retrieved a TrainerEntity with a username of {}, result - {}", username,
                    trainerEntity);
                return trainerEntity;
            }
        }
        LOGGER.error("Failed to retrieve a TrainerEntity with a username of {}, throwing a TrainerNotFoundException", username);
        throw new TrainerNotFoundException(username);
    }

    @Override
    public Optional<TrainerEntity> findByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Retrieving an optional TrainerEntity with a username of {}", username);
        for(Map.Entry<Long, TrainerEntity> pair : inMemoryStorage.entrySet()) {
            TrainerEntity trainerEntity = pair.getValue();
            if(trainerEntity.getUsername().equals(username)) {
                Optional<TrainerEntity> optionalTrainer = Optional.of(trainerEntity);
                LOGGER.info("Successfully retrieved an optional TrainerEntity with a username of {}, result - {}", username, optionalTrainer);
                return optionalTrainer;
            }
        }
        Optional<TrainerEntity> optionalTrainer = Optional.empty();
        LOGGER.info("Successfully retrieved an optional TrainerEntity with a username of {}, result - {}", username, optionalTrainer);
        return optionalTrainer;
    }

    @Override
    public Optional<TrainerEntity> findById(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainerEntity with an id of {}", id);
        Optional<TrainerEntity> optionalTrainer = Optional.ofNullable(inMemoryStorage.get(id));
        LOGGER.info("Successfully retrieved an optional TrainerEntity with an id of {}, result - {}", id, optionalTrainer);
        return optionalTrainer;
    }

    @Autowired
    public void setTrainerFileStorage(FileStorage<TrainerEntity> trainerFileStorage) {
        this.trainerFileStorage = trainerFileStorage;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Initializing TrainerStorageImpl bean");
        this.inMemoryStorage = trainerFileStorage.parseMemoryFile();
    }
}
