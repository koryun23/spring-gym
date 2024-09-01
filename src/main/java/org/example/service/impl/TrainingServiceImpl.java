package org.example.service.impl;

import org.example.dao.core.TrainingDao;
import org.example.dao.impl.TrainingDaoImpl;
import org.example.entity.Training;
import org.example.service.core.TrainingService;
import org.example.service.params.TrainingCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    @Autowired
    private TrainingDao trainingDao;

    @Override
    public Training create(TrainingCreateParams params) {
        Assert.notNull(params, "TrainingCreateParams must not be null");
        LOGGER.info("Creating a Training according to the TrainingCreateParams - {}", params);
        Training training = trainingDao.save(new Training(
                params.getTrainingId(),
                params.getTraineeId(),
                params.getTrainerId(),
                params.getName(),
                params.getTrainingType(),
                params.getTrainingDate(),
                params.getDuration()
        ));
        LOGGER.info("Successfully created a Training according to the Training Create Params - {}, result - {}", params, training);
        return training;
    }

    @Override
    public Training select(Long id) {
        Assert.notNull(id, "Training Id must not be null");
        LOGGER.info("Selecting a Training with an id of {}", id);
        Training training = trainingDao.get(id);
        LOGGER.info("Successfully selected a Training with an id of {}, result - {}", id, training);
        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        Assert.notNull(id, "Training id must not be null");
        LOGGER.info("Retrieving an optional Training with an id of {}", id);
        Optional<Training> optionalTraining = trainingDao.findById(id);
        LOGGER.info("Successfully retrieved an optional Training with an id of {}, result - {}", id, optionalTraining);
        return optionalTraining;
    }
}
