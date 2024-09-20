package org.example.dao.impl;

import java.util.List;
import java.util.Optional;
import org.example.dao.core.TrainingTypeDao;
import org.example.entity.TrainingTypeEntity;
import org.example.exception.TrainingTypeNotFoundException;
import org.example.repository.core.TrainingTypeEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingTypeDaoImpl implements TrainingTypeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeDaoImpl.class);

    private TrainingTypeEntityRepository repository;

    public TrainingTypeDaoImpl(TrainingTypeEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public TrainingTypeEntity get(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving a training type with an id of {}", id);
        TrainingTypeEntity trainingTypeEntity =
            repository.findById(id).orElseThrow(() -> new TrainingTypeNotFoundException(id));
        LOGGER.info("Successfully retrieved a training type with an id of {}, result - {}",
            id, trainingTypeEntity);
        return trainingTypeEntity;
    }

    @Override
    public TrainingTypeEntity save(TrainingTypeEntity trainingType) {
        Assert.notNull(trainingType, "Training type must not be null");
        LOGGER.info("Saving {}", trainingType);
        TrainingTypeEntity savedTrainingType = repository.save(trainingType);
        LOGGER.info("Successfully saved {}, result - {}", trainingType, savedTrainingType);
        return savedTrainingType;
    }

    @Override
    public TrainingTypeEntity update(TrainingTypeEntity trainingType) {
        Assert.notNull(trainingType, "Training type myst not be null");
        LOGGER.info("Updating a training type with an id of {}", trainingType.getId());
        TrainingTypeEntity updatedTrainingType = repository.save(trainingType);
        LOGGER.info("Successfully updated a training type with an id of {}, result - {}",
            trainingType.getId(), updatedTrainingType);
        return updatedTrainingType;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Deleting a training type with an id of {}", id);
        repository.deleteById(id);
        LOGGER.info("Successfully deleted a training type with an id of {}", id);
        return true;
    }

    @Override
    public Optional<TrainingTypeEntity> findById(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving an optional training type with an id of {}", id);
        Optional<TrainingTypeEntity> optionalTrainingType = repository.findById(id);
        LOGGER.info("Successfully retrieved an optional training type with an id of {}, result - {}",
            id, optionalTrainingType);
        return optionalTrainingType;
    }

    @Override
    public List<TrainingTypeEntity> findAll() {
        LOGGER.info("Retrieving a list of all Training Type Entities");
        List<TrainingTypeEntity> all = repository.findAll();
        LOGGER.info("Successfully retrieved a list of all Training Type Entities, result - {}", all);
        return all;
    }
}
