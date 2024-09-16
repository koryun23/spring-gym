package org.example.dao.impl;

import java.util.Optional;
import org.example.dao.core.TrainerDao;
import org.example.entity.TrainerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerDaoImpl implements TrainerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDaoImpl.class);

    private TrainerStorageImpl storage;

    @Autowired
    public void setStorage(TrainerStorageImpl storage) {
        this.storage = storage;
    }

    @Override
    public TrainerEntity get(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Retrieving a TrainerEntity with an id of {}", id);
        TrainerEntity trainerEntity = storage.get(id);
        LOGGER.info("Successfully retrieved a TrainerEntity with an id of {}, result - {}", id, trainerEntity);
        return trainerEntity;
    }

    @Override
    public TrainerEntity save(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        LOGGER.info("Saving {}", trainerEntity);
        TrainerEntity addedTrainerEntity = storage.add(trainerEntity);
        LOGGER.info("Successfully added {}", addedTrainerEntity);
        return addedTrainerEntity;
    }

    @Override
    public TrainerEntity update(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        LOGGER.info("Updating a TrainerEntity with an id of {}", trainerEntity.getUserId());
        TrainerEntity updatedTrainerEntity = storage.update(trainerEntity);
        LOGGER.info("Successfully updated a TrainerEntity with an id of {}, result - {}", trainerEntity.getUserId(),
            updatedTrainerEntity);
        return updatedTrainerEntity;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Deleting a TrainerEntity with an id of {}", id);
        boolean success = storage.remove(id);
        if (success) {
            LOGGER.info("Successfully deleted a TrainerEntity with an id of {}", id);
        } else {
            LOGGER.error("Failed to delete a TrainerEntity with an id of {}", id);
        }
        return success;
    }

    @Override
    public TrainerEntity getByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Retrieving a TrainerEntity with a username of {}", username);
        TrainerEntity trainerEntity = storage.getByUsername(username);
        LOGGER.info("Successfully retrieved a TrainerEntity with a username of {}, result - {}", username,
            trainerEntity);
        return trainerEntity;
    }

    @Override
    public Optional<TrainerEntity> findByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Retrieving an optional TrainerEntity with a username of {}", username);
        Optional<TrainerEntity> optionalTrainer = storage.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional TrainerEntity with a username of {}, result - {}", username,
            optionalTrainer);
        return optionalTrainer;
    }

    @Override
    public Optional<TrainerEntity> findById(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainerEntity with an id of {}", id);
        Optional<TrainerEntity> optionalTrainer = storage.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainerEntity with an id od {}, result - {}", id,
            optionalTrainer);
        return optionalTrainer;
    }
}
