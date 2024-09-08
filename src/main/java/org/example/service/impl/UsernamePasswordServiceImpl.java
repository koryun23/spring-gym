package org.example.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UsernamePasswordService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("usernamePasswordService")
public class UsernamePasswordServiceImpl implements UsernamePasswordService {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    /**
     * Constructor.
     */
    public UsernamePasswordServiceImpl(TraineeService traineeService, TrainerService trainerService) {
        Assert.notNull(traineeService, "TraineeEntity Service must not be null");
        Assert.notNull(trainerService, "TrainerEntity Service must not be null");
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @Override
    public String username(String firstName, String lastName, Long id, String uniqueSuffix) {
        String temporaryUsername = firstName + "." + lastName;
        Optional<TraineeEntity> optionalTrainee = traineeService.findByUsername(temporaryUsername);
        Optional<TrainerEntity> optionalTrainer = trainerService.findByUsername(temporaryUsername);

        if (optionalTrainee.isEmpty() && optionalTrainer.isEmpty()) {
            return temporaryUsername;
        }

        temporaryUsername += ("." + id);
        optionalTrainer = trainerService.findByUsername(temporaryUsername);

        if (optionalTrainer.isEmpty()) {
            return temporaryUsername;
        }
        return temporaryUsername + uniqueSuffix;
    }

    @Override
    public String password() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
