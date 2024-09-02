package org.example.service.impl;

import org.example.entity.Trainee;
import org.example.entity.Trainer;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UsernamePasswordService;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

public class TraineeUsernamePasswordServiceImpl implements UsernamePasswordService {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    public TraineeUsernamePasswordServiceImpl(TraineeService traineeService, TrainerService trainerService) {
        Assert.notNull(traineeService, "Trainee Service must not be null");
        Assert.notNull(trainerService, "Trainer Service must not be null");
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @Override
    public String username(String firstName, String lastName, Long id) {
        String temporaryUsername = firstName + "." + lastName;
        Optional<Trainee> optionalTrainee = traineeService.findByUsername(temporaryUsername);

        if (optionalTrainee.isEmpty()) return temporaryUsername;

        temporaryUsername += ("." + id);
        Optional<Trainer> optionalTrainer = trainerService.findByUsername(temporaryUsername);

        if (optionalTrainer.isEmpty()) return temporaryUsername;
        return temporaryUsername + ".trainee";
    }

    @Override
    public String password() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
