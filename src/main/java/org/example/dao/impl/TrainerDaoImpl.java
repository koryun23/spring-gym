package org.example.dao.impl;

import org.example.dao.core.TrainerDao;
import org.example.entity.Trainer;
import org.example.repository.impl.TrainerStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
        LOGGER.info("Retrieving a Trainer with an id of {}", id);
        Trainer trainer = storage.get(id);
        LOGGER.info("Successfully retrieved a Trainer with an id of {}, result - {}", id, trainer);
        return trainer;
    }

    @Override
    public Trainer save(Trainer trainer) {
        LOGGER.info("Saving {}", trainer);
        Trainer addedTrainer = storage.add(trainer);
        LOGGER.info("Successfully added {}", addedTrainer);
        return addedTrainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        LOGGER.info("Updating a Trainer with an id of {}", trainer.getUserId());
        Trainer updatedTrainer = storage.update(trainer);
        LOGGER.info("Successfully updated a Trainer with an id of {}, result - {}", trainer.getUserId(), updatedTrainer);
        return updatedTrainer;
    }

    @Override
    public boolean delete(Long id) {
        LOGGER.info("Deleting a Trainer with an id of {}", id);
        boolean success = storage.remove(id);
        if(success) LOGGER.info("Successfully deleted a Trainer with an id of {}", id);
        else LOGGER.error("Failed to delete a Trainer with an id of {}", id);
        return success;
    }

    @Override
    public Trainer getByUsername(String username) {
        return null;
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.empty();
    }
}
