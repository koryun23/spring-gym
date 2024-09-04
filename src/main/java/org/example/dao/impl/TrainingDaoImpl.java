package org.example.dao.impl;

import org.example.dao.core.TrainingDao;
import org.example.entity.TrainingEntity;
import org.example.repository.impl.TrainingStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

@Component
public class TrainingDaoImpl implements TrainingDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDaoImpl.class);

    private TrainingStorageImpl storage;

    @Autowired
    public void setStorage(TrainingStorageImpl storage) {
        this.storage = storage;
    }

    @Override
    public TrainingEntity get(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving a TrainingEntity with an id of {}", id);
        TrainingEntity trainingEntity = storage.get(id);
        LOGGER.info("Successfully retrieved a TrainingEntity with an id of {}, result - {}", id, trainingEntity);
        return trainingEntity;
    }

    @Override
    public TrainingEntity save(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        LOGGER.info("Saving {}", trainingEntity);
        TrainingEntity addedTrainingEntity = storage.add(trainingEntity);
        LOGGER.info("Successfully saved {}", addedTrainingEntity);
        return addedTrainingEntity;
    }

    @Override
    public TrainingEntity update(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        LOGGER.info("Updating a TrainingEntity with an id of {}", trainingEntity.getTrainingId());
        TrainingEntity updatedTrainingEntity = storage.update(trainingEntity);
        LOGGER.info("Successfully updated a TrainingEntity with an id of {}, result - {}", trainingEntity.getTrainingId(),
            updatedTrainingEntity);
        return updatedTrainingEntity;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Deleting a TrainingEntity with an id of {}", id);
        boolean success = storage.remove(id);
        if(success) LOGGER.info("Successfully deleted a TrainingEntity with an id of {}", id);
        else LOGGER.error("Failed to delete a TrainingEntity with an id of {}", id);
        return success;
    }

    @Override
    public Optional<TrainingEntity> findById(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainingEntity with an id of {}", id);
        Optional<TrainingEntity> optionalTraining = storage.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainingEntity with an id of {}, result - {}", id, optionalTraining);
        return optionalTraining;
    }
}
