package org.example.service.impl;

import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.exception.InvalidIdException;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UsernamePasswordService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
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
        Assert.notNull(firstName, "First Name must not be null");
        Assert.hasText(firstName, "First name must not be empty");
        Assert.notNull(lastName, "Last Name must not be null");
        Assert.hasText(lastName, "Last Name must not be empty");
        Assert.notNull(uniqueSuffix, "Unique Suffix must not be null");

        if (id <= 0) {
            throw new InvalidIdException(id);
        }

        String temporaryUsername = firstName + "." + lastName;
        Optional<TraineeEntity> optionalTrainee = traineeService.findByUsername(temporaryUsername);
        Optional<TrainerEntity> optionalTrainer = trainerService.findByUsername(temporaryUsername);

        log.info("Optional trainee - {}", optionalTrainee);

        if (optionalTrainee.isEmpty() && optionalTrainer.isEmpty()) {
            return temporaryUsername;
        }

        temporaryUsername += ("." + id);
        optionalTrainer = trainerService.findByUsername(temporaryUsername);
        optionalTrainee = traineeService.findByUsername(temporaryUsername);

        if (optionalTrainer.isEmpty() && optionalTrainee.isEmpty()) {
            return temporaryUsername;
        }
        return temporaryUsername + "." + uniqueSuffix;
    }

    @Override
    public String password() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
