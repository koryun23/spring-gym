package org.example.service.impl;

import org.example.dao.impl.TrainingDao;
import org.example.entity.Training;
import org.example.service.core.TrainingService;
import org.example.service.params.TrainingCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDao trainingDao;

    @Override
    public Training create(TrainingCreateParams params) {
        return trainingDao.save(new Training(
                params.getTrainingId(),
                params.getTraineeId(),
                params.getTrainerId(),
                params.getName(),
                params.getTrainingType(),
                params.getTrainingDate(),
                params.getDuration()
        ));
    }

    @Override
    public Training select(Long id) {
        return trainingDao.get(id);
    }
}
