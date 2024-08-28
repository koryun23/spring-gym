package org.example.dao.impl;

import org.example.dao.core.Dao;
import org.example.entity.Training;
import org.example.repository.impl.TrainingStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingDao implements Dao<Training> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDao.class);

    @Autowired
    private TrainingStorageImpl storage;

    @Override
    public Training get(Long id) {
        LOGGER.info("Retrieving a Training with an id of {}", id);
        Training training = storage.get(id);
        LOGGER.info("Successfully retrieved a Training with an id of {}, result - {}", id, training);
        return training;
    }

    @Override
    public Training save(Training training) {
        LOGGER.info("Saving {}", training);
        Training addedTraining = storage.add(training);
        LOGGER.info("Successfully saved {}", addedTraining);
        return addedTraining;
    }

    @Override
    public Training update(Training training) {
        LOGGER.info("Updating a Training with an id of {}", training.getTrainingId());
        Training updatedTraining = storage.update(training);
        LOGGER.info("Successfully updated a Training with an id of {}, result - {}", training.getTrainingId(), updatedTraining);
        return updatedTraining;
    }

    @Override
    public boolean delete(Long id) {
        LOGGER.info("Deleting a Training with an id of {}", id);
        boolean success = storage.remove(id);
        if(success) LOGGER.info("Successfully deleted a Training with an id of {}", id);
        else LOGGER.error("Failed to delete a Training with an id of {}", id);
        return success;
    }
}
