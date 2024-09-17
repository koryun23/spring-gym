package org.example.service.impl;

import java.util.Optional;
import org.example.dao.core.TrainingTypeDao;
import org.example.entity.TrainingTypeEntity;
import org.example.service.core.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class TrainingTypeServiceImpl implements TrainingTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

    private final TrainingTypeDao trainingTypeDao;

    public TrainingTypeServiceImpl(TrainingTypeDao trainingTypeDao) {
        this.trainingTypeDao = trainingTypeDao;
    }

    @Override
    public TrainingTypeEntity create(TrainingTypeEntity trainingType) {
        Assert.notNull(trainingType, "Training Type Entity must not be null");
        LOGGER.info("Creating a Training Type Entity {}", trainingType);
        TrainingTypeEntity savedTrainingTypeEntity = trainingTypeDao.save(trainingType);
        LOGGER.info("Successfully created a Training Type Entity {}", trainingType);
        return savedTrainingTypeEntity;
    }

    @Override
    public TrainingTypeEntity update(TrainingTypeEntity trainingType) {
        Assert.notNull(trainingType, "Training Type Entity must not be null");
        LOGGER.info("Updating a Training Type Entity with an id of {}", trainingType.getId());
        TrainingTypeEntity updatedTrainingTypeEntity = trainingTypeDao.update(trainingType);
        LOGGER.info("Successfully updated a Training Type Entity with an id of {}, result - {}",
            trainingType.getId(), updatedTrainingTypeEntity);
        return updatedTrainingTypeEntity;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Deleting a Training Type Entity with an id of {}", id);
        trainingTypeDao.delete(id);
        LOGGER.info("Successfully deleted a Training Type Entity with an id of {}", id);
        return true;
    }

    @Override
    public TrainingTypeEntity get(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving a Training Type Entity with an id of {}", id);
        TrainingTypeEntity trainingTypeEntity = trainingTypeDao.get(id);
        LOGGER.info("Successfully retrieved a Training Type Entity with an id of {}, result - {}",
            id, trainingTypeEntity);
        return trainingTypeEntity;
    }

    @Override
    public Optional<TrainingTypeEntity> findById(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving an optional Training Type Entity with an id of {}", id);
        Optional<TrainingTypeEntity> optionalTrainingType = trainingTypeDao.findById(id);
        LOGGER.info("Successfully retrieved an optional Training Type Entity with an id of {}, result - {}",
            id, optionalTrainingType);
        return optionalTrainingType;
    }
}
