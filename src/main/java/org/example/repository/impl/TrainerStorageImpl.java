package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.SpecializationType;
import org.example.entity.Trainer;
import org.example.exception.TrainerNotFoundException;
import org.example.repository.core.FileStorage;
import org.example.repository.core.TrainerStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class TrainerStorageImpl implements TrainerStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerStorageImpl.class);

    private Map<Long, Trainer> inMemoryStorage;

    private FileStorage<Trainer> trainerFileStorage;

    public TrainerStorageImpl(Map<Long, Trainer> inMemoryStorage) {
        Assert.notNull(inMemoryStorage, "In-memory storage must not be null");
        this.inMemoryStorage = inMemoryStorage;
    }

    public TrainerStorageImpl() {
        this.inMemoryStorage = new HashMap<>();
    }

    @Override
    public Trainer get(Long id) {
        LOGGER.info("Retrieving a Trainer with an id of {} from the in-memory storage", id);
        Assert.notNull(id, "Trainer id must not be null");
        Trainer trainer = inMemoryStorage.get(id);
        if(trainer == null) throw new TrainerNotFoundException(id);
        LOGGER.info("Successfully retrieved a Trainer with an id of {}, result - {}", id, trainer);
        return trainer;
    }

    @Override
    public Trainer add(Trainer trainer) {
        LOGGER.info("Adding {} to the in-memory storage", trainer);
        Assert.notNull(trainer, "Trainer must not be null");
        Trainer addedTrainer = inMemoryStorage.put(trainer.getUserId(), trainer);
        LOGGER.info("Successfully added {} to the in-memory storage", addedTrainer);
        trainerFileStorage.persist(inMemoryStorage);
        return trainer;
    }

    @Override
    public boolean remove(Long id) {
        Assert.notNull(id, "Trainer id must not be null");
        LOGGER.info("Removing a Trainer with an id of {} from the in-memory storage", id);
        Trainer removedTrainer = inMemoryStorage.remove(id);
        LOGGER.info("Successfully removed {} from the in-memory storage", removedTrainer);
        trainerFileStorage.persist(inMemoryStorage);
        return true;
    }

    @Override
    public Trainer update(Trainer trainer) {
        Assert.notNull(trainer, "Trainer must not be null");
        LOGGER.info("Updating a Trainer with an id of {}", trainer.getUserId());
        Trainer updatedTrainer = inMemoryStorage.put(trainer.getUserId(), trainer);
        LOGGER.info("Successfully updated a Trainer with an id of {}, final result - {}", trainer.getUserId(), updatedTrainer);
        trainerFileStorage.persist(inMemoryStorage);
        return updatedTrainer;
    }

    @Override
    public Trainer getByUsername(String username) {
        Assert.notNull(username, "Trainer username must not be null");
        Assert.hasText(username, "Trainer username must not be empty");
        LOGGER.info("Retrieving a Trainer with a username of {}", username);
        for(Map.Entry<Long, Trainer> pair : inMemoryStorage.entrySet()) {
            Trainer trainer = pair.getValue();
            if(trainer.getUsername().equals(username)) {
                LOGGER.info("Successfully retrieved a Trainer with a username of {}, result - {}", username, trainer);
                return trainer;
            }
        }
        LOGGER.error("Failed to retrieve a Trainer with a username of {}, throwing a TrainerNotFoundException", username);
        throw new TrainerNotFoundException(username);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        Assert.notNull(username, "Trainer username must not be null");
        Assert.hasText(username, "Trainer username must not be empty");
        LOGGER.info("Retrieving an optional Trainer with a username of {}", username);
        for(Map.Entry<Long, Trainer> pair : inMemoryStorage.entrySet()) {
            Trainer trainer = pair.getValue();
            if(trainer.getUsername().equals(username)) {
                Optional<Trainer> optionalTrainer = Optional.of(trainer);
                LOGGER.info("Successfully retrieved an optional Trainer with a username of {}, result - {}", username, optionalTrainer);
                return optionalTrainer;
            }
        }
        Optional<Trainer> optionalTrainer = Optional.empty();
        LOGGER.info("Successfully retrieved an optional Trainer with a username of {}, result - {}", username, optionalTrainer);
        return optionalTrainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        Assert.notNull(id, "Trainer id must not be null");
        LOGGER.info("Retrieving an optional Trainer with an id of {}", id);
        Optional<Trainer> optionalTrainer = Optional.of(inMemoryStorage.get(id));
        LOGGER.info("Successfully retrieved an optional Trainer with an id of {}, result - {}", id, optionalTrainer);
        return optionalTrainer;
    }

    @Autowired
    public void setTrainerFileStorage(FileStorage<Trainer> trainerFileStorage) {
        this.trainerFileStorage = trainerFileStorage;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Initializing TrainerStorageImpl bean");
        this.inMemoryStorage = trainerFileStorage.parseMemoryFile();
    }
}
