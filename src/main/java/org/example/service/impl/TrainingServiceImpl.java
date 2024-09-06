package org.example.service.impl;

import java.util.Optional;
import org.example.dao.core.TrainingDao;
import org.example.entity.TrainingEntity;
import org.example.service.core.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    @Autowired
    private TrainingDao trainingDao;

    @Override
    public TrainingEntity create(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingCreateParams must not be null");
        LOGGER.info("Creating a TrainingEntity according to the TrainingCreateParams - {}", trainingEntity);
        TrainingEntity createdTrainingEntity = trainingDao.save(new TrainingEntity(
            trainingEntity.getTrainingId(),
            trainingEntity.getTraineeId(),
            trainingEntity.getTrainerId(),
            trainingEntity.getName(),
            trainingEntity.getTrainingType(),
            trainingEntity.getTrainingDate(),
            trainingEntity.getDuration()
        ));
        LOGGER.info(
            "Successfully created a TrainingEntity according to the TrainingEntity Create Params - {}, result - {}",
            trainingEntity, createdTrainingEntity);
        return createdTrainingEntity;
    }

    @Override
    public TrainingEntity select(Long id) {
        Assert.notNull(id, "TrainingEntity Id must not be null");
        LOGGER.info("Selecting a TrainingEntity with an id of {}", id);
        TrainingEntity trainingEntity = trainingDao.get(id);
        LOGGER.info("Successfully selected a TrainingEntity with an id of {}, result - {}", id, trainingEntity);
        return trainingEntity;
    }

    @Override
    public Optional<TrainingEntity> findById(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainingEntity with an id of {}", id);
        Optional<TrainingEntity> optionalTraining = trainingDao.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainingEntity with an id of {}, result - {}", id,
            optionalTraining);
        return optionalTraining;
    }
}
