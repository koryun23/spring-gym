package org.example.service.impl;

import org.example.service.core.DatabasePathService;
import org.springframework.beans.factory.annotation.Value;

public class DatabasePathServiceImpl implements DatabasePathService {

    @Value("${trainee.id.path}")
    private String traineeIdPath;

    @Value("${trainer.id.path}")
    private String trainerIdPath;

    @Value("${training.id.path}")
    private String trainingIdPath;

    @Value("${training.path}")
    private String trainingPath;

    @Value("${trainee.path}")
    private String traineePath;

    @Value("${trainer.path}")
    private String trainerPath;


    @Override
    public String getTraineePath() {
        return traineePath;
    }

    @Override
    public String getTrainerPath() {
        return trainerPath;
    }

    @Override
    public String getTrainingPath() {
        return trainingPath;
    }

    @Override
    public String getTraineeIdPath() {
        return traineeIdPath;
    }

    @Override
    public String getTrainerIdPath() {
        return trainerIdPath;
    }

    @Override
    public String getTrainingIdPath() {
        return trainingIdPath;
    }
}
