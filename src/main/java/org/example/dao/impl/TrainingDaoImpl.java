package org.example.dao.impl;

import java.util.Optional;
import org.example.dao.core.TrainingDao;
import org.example.entity.TrainingEntity;
import org.example.exception.TrainingNotFoundException;
import org.example.repository.core.TrainingEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingDaoImpl implements TrainingDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingDaoImpl.class);

    private TrainingEntityRepository trainingEntityRepository;

    @Autowired
    public void setStorage(TrainingEntityRepository storage) {
        this.trainingEntityRepository = storage;
    }

    @Override
    public TrainingEntity get(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving a TrainingEntity with an id of {}", id);
        TrainingEntity trainingEntity = trainingEntityRepository.findById(id)
            .orElseThrow(() -> new TrainingNotFoundException(id));
        LOGGER.info("Successfully retrieved a TrainingEntity with an id of {}, result - {}", id, trainingEntity);
        return trainingEntity;
    }

    @Override
    public TrainingEntity save(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        LOGGER.info("Saving {}", trainingEntity);
        TrainingEntity addedTrainingEntity = trainingEntityRepository.save(trainingEntity);
        LOGGER.info("Successfully saved {}", addedTrainingEntity);
        return addedTrainingEntity;
    }

    @Override
    public TrainingEntity update(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        LOGGER.info("Updating a TrainingEntity with an id of {}", trainingEntity.getId());
        TrainingEntity updatedTrainingEntity = trainingEntityRepository.save(trainingEntity);
        LOGGER.info("Successfully updated a TrainingEntity with an id of {}, result - {}",
            trainingEntity.getId(),
            updatedTrainingEntity);
        return updatedTrainingEntity;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Deleting a TrainingEntity with an id of {}", id);
        trainingEntityRepository.deleteById(id);
        LOGGER.info("Successfully deleted a TrainingEntity with an id of {}", id);
        return true;
    }

    @Override
    public Optional<TrainingEntity> findById(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainingEntity with an id of {}", id);
        Optional<TrainingEntity> optionalTraining = trainingEntityRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainingEntity with an id of {}, result - {}", id,
            optionalTraining);
        return optionalTraining;
    }
}
