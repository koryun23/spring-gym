package org.example.service.impl;

import org.example.dao.impl.TrainingDaoImpl;
import org.example.entity.Training;
import org.example.service.core.TrainingService;
import org.example.service.params.TrainingCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    @Autowired
    private TrainingDaoImpl trainingDao;

    @Override
    public Training create(TrainingCreateParams params) {
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
        LOGGER.info("Selecting a Training with an id of {}", id);
        Training training = trainingDao.get(id);
        LOGGER.info("Successfully selected a Training with an id of {}, result - {}", id, training);
        return training;
    }
}
