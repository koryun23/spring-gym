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
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component("permissionService")
public class PermissionService {

    private static final AuthorizationResult FAILED_AUTHORIZATION = new AuthorizationDecision(false);

    private TraineeService traineeService;
    private TrainerService trainerService;
    private TrainingService trainingService;

    /**
     * Method for evaluating whether the authenticated user can view the given trainee's profile.
     */
    public void canViewTrainee(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Trainee username passed via path - {}", username);
        log.info("Authenticated username - {}", authentication.getName());
        Set<String> assignedTrainers = traineeService.selectByUsername(username).getTrainingEntityList().stream()
            .map(TrainingEntity::getTrainer).map(
                TrainerEntity::getUser).map(UserEntity::getUsername).collect(Collectors.toSet());
        if (username.equals(authentication.getName()) || assignedTrainers.contains(authentication.getName())) {
            return;
        }
        throw new AuthorizationDeniedException("Access Denied.", FAILED_AUTHORIZATION);
    }

    /**
     * Method for evaluating whether the authenticated user can update the given trainee's profile.
     */
    public void canUpdateTrainee(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Trainee username passed via path - {}", username);
        log.info("Authenticated username - {}", authentication.getName());

        if (username.equals(authentication.getName())) {
            return;
        }
        throw new AuthorizationDeniedException("Access Denied.", FAILED_AUTHORIZATION);
    }

    /**
     * Method for evaluating whether the authenticated user can delete the given trainee's profile.
     */
    public void canDeleteTrainee(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Trainee username passed via path - {}", username);
        log.info("Authenticated username - {}", authentication.getName());

        if (username.equals(authentication.getName())) {
            return;
        }
        throw new AuthorizationDeniedException("Access Denied.", FAILED_AUTHORIZATION);
    }

    /**
     * Permission evaluator.
     */
    public void canViewTrainersNotAssignedOnTrainee(String traineeUsername) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Trainee username passed via path - {}", traineeUsername);
        log.info("Authenticated username - {}", authentication.getName());

        if (traineeUsername.equals(authentication.getName())) {
            return;
        }
        throw new AuthorizationDeniedException("Access Denied.", FAILED_AUTHORIZATION);
    }

    /**
     * Permission evaluator.
     */
    public void canUpdateTrainer(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Trainer username passed via path - {}", username);
        log.info("Authenticated username - {}", authentication.getName());

        if (username.equals(authentication.getName())) {
            return;
        }
        throw new AuthorizationDeniedException("Access Denied.", FAILED_AUTHORIZATION);
    }

    /**
     * Permission evaluator.
     */
    public void canViewTrainer(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Trainer username passed via path - {}", username);
        log.info("Authenticated username - {}", authentication.getName());

        if (username.equals(authentication.getName())) {
            return;
        }
        throw new AuthorizationDeniedException("Access Denied.", FAILED_AUTHORIZATION);
    }

    /**
     * Permission evaluator.
     */
    public void canViewTrainingsOfTrainee(String traineeUsername) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName().equals(traineeUsername)) {
            return;
        }

        throw new AuthorizationDeniedException("Access Denied.", FAILED_AUTHORIZATION);
    }

    /**
     * Permission evaluator.
     */
    public void canViewTrainingsOfTrainer(String trainerUsername) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName().equals(trainerUsername)) {
            return;
        }

        throw new AuthorizationDeniedException("Access Denied.", FAILED_AUTHORIZATION);
    }

    /**
     * Permission evaluator.
     */
    public void canChangePassword(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName().equals(username)) {
            return;
        }
        throw new AuthorizationDeniedException("Access Denied", FAILED_AUTHORIZATION);
    }
}
