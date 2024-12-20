package org.example.security.service;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingEntity;
import org.example.entity.user.UserEntity;
import org.example.service.core.trainee.TraineeService;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component("permissionService")
public class PermissionService {

    private TraineeService traineeService;

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
        throw new AuthorizationDeniedException("Access Denied.");
    }

    /**
     * Method for evaluating whether the authenticated user can update the given trainee's profile.
     */
    public void canUpdateTrainee(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Trainee username passed via path - {}", username);
        log.info("Authenticated username - {}", authentication.getName());

        if(username.equals(authentication.getName())) {
            return;
        }
        throw new AuthorizationDeniedException("Access Denied.");
    }

    /**
     * Method for evaluating whether the authenticated user can delete the given trainee's profile.
     */
    public void canDeleteTrainee(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Trainee username passed via path - {}", username);
        log.info("Authenticated username - {}", authentication.getName());

        if(username.equals(authentication.getName())) {
            return;
        }
        throw new AuthorizationDeniedException("Access Denied.");
    }
}
