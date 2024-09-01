package org.example.dao.impl;

import org.example.dao.core.TrainerDao;
import org.example.entity.Trainer;
import org.example.repository.impl.TrainerStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class TrainerDaoImpl implements TrainerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDaoImpl.class);

    private TrainerStorageImpl storage;

    @Autowired
    public void setStorage(TrainerStorageImpl storage) {
        this.storage = storage;
    }

    @Override
    public Trainer get(Long id) {
        Assert.notNull(id, "Trainer id must not be null");
        LOGGER.info("Retrieving a Trainer with an id of {}", id);
        Trainer trainer = storage.get(id);
        LOGGER.info("Successfully retrieved a Trainer with an id of {}, result - {}", id, trainer);
        return trainer;
    }

    @Override
    public Trainer save(Trainer trainer) {
        Assert.notNull(trainer, "Trainer must not be null");
        LOGGER.info("Saving {}", trainer);
        Trainer addedTrainer = storage.add(trainer);
        LOGGER.info("Successfully added {}", addedTrainer);
        return addedTrainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        Assert.notNull(trainer, "Trainer must not be null");
        LOGGER.info("Updating a Trainer with an id of {}", trainer.getUserId());
        Trainer updatedTrainer = storage.update(trainer);
        LOGGER.info("Successfully updated a Trainer with an id of {}, result - {}", trainer.getUserId(), updatedTrainer);
        return updatedTrainer;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "Trainer id must not be null");
        LOGGER.info("Deleting a Trainer with an id of {}", id);
        boolean success = storage.remove(id);
        if(success) LOGGER.info("Successfully deleted a Trainer with an id of {}", id);
        else LOGGER.error("Failed to delete a Trainer with an id of {}", id);
        return success;
    }

    @Override
    public Trainer getByUsername(String username) {
        Assert.notNull(username, "Trainer username must not be null");
        Assert.hasText(username, "Trainer username must not be empty");
        LOGGER.info("Retrieving a Trainer with a username of {}", username);
        Trainer trainer = storage.getByUsername(username);
        LOGGER.info("Successfully retrieved a Trainer with a username of {}, result - {}", username, trainer);
        return trainer;
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        Assert.notNull(username, "Trainer username must not be null");
        Assert.hasText(username, "Trainer username must not be empty");
        LOGGER.info("Retrieving an optional Trainer with a username of {}", username);
        Optional<Trainer> optionalTrainer = storage.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional Trainer with a username of {}, result - {}", username, optionalTrainer);
        return optionalTrainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        Assert.notNull(id, "Trainer id must not be null");
        LOGGER.info("Retrieving an optional Trainer with an id of {}", id);
        Optional<Trainer> optionalTrainer = storage.findById(id);
        LOGGER.info("Successfully retrieved an optional Trainer with an id od {}, result - {}", id, optionalTrainer);
        return optionalTrainer;
    }
}
