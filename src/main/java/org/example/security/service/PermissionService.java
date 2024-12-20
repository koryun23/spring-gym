package org.example.security.service;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingEntity;
import org.example.entity.user.UserEntity;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.training.TrainingService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component("permissionService")
public class PermissionService {

    private TraineeService traineeService;
    private TrainerService trainerService;
    private TrainingService trainingService;

    /**
     * Method for evaluating whether the authenticated user can view the given trainee's profile.
     */
    public boolean canViewTrainee(Authentication authentication, String username) {
        log.info("Trainee username passed via path - {}", username);
        log.info("Authenticated username - {}", authentication.getName());
        //return true;
        Set<String> assignedTrainers = traineeService.selectByUsername(username).getTrainingEntityList().stream()
            .map(TrainingEntity::getTrainer).map(
                TrainerEntity::getUser).map(UserEntity::getUsername).collect(Collectors.toSet());
        return username.equals(authentication.getName()) || assignedTrainers.contains(authentication.getName());
    }
}
