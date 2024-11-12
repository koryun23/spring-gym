package org.example.service.impl.training;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.exception.TrainingTypeNotFoundException;
import org.example.repository.TrainingTypeEntityRepository;
import org.example.service.core.training.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Transactional
@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

    private final TrainingTypeEntityRepository trainingTypeDao;

    public TrainingTypeServiceImpl(TrainingTypeEntityRepository trainingTypeDao) {
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
        TrainingTypeEntity updatedTrainingTypeEntity = trainingTypeDao.save(trainingType);
        LOGGER.info("Successfully updated a Training Type Entity with an id of {}, result - {}",
            trainingType.getId(), updatedTrainingTypeEntity);
        return updatedTrainingTypeEntity;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Deleting a Training Type Entity with an id of {}", id);
        trainingTypeDao.deleteById(id);
        LOGGER.info("Successfully deleted a Training Type Entity with an id of {}", id);
        return true;
    }

    @Override
    public TrainingTypeEntity get(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving a Training Type Entity with an id of {}", id);
        TrainingTypeEntity trainingTypeEntity =
            trainingTypeDao.findById(id).orElseThrow(() -> new TrainingTypeNotFoundException(id));
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

    @Override
    public List<TrainingTypeEntity> findAll() {
        LOGGER.info("Retrieving all training types");

        List<TrainingTypeEntity> all = trainingTypeDao.findAll();

        LOGGER.info("Successfully retrieved all training types, result - {}", all);
        return all;
    }

    @Override
    public Optional<TrainingTypeEntity> findByTrainingType(TrainingType trainingType) {
        Assert.notNull(trainingType, "Training Type must not be null");
        LOGGER.info("Retrieving an optional Training Type entity of type {}", trainingType);

        Optional<TrainingTypeEntity> optionalTrainingType =
            Optional.ofNullable(trainingTypeDao.getByTrainingType(trainingType));

        LOGGER.info("Successfully retrieved an optional Training Type {}", optionalTrainingType);

        return optionalTrainingType;

    }
}
