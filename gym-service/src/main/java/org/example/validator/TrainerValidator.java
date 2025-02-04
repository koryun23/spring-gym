package org.example.validator;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.exception.TrainingTypeNotFoundException;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.training.TrainingTypeService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class TrainerValidator {

    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingTypeService trainingTypeService;

    /**
     * Constructor.
     */
    public TrainerValidator(TrainerService trainerService, TraineeService traineeService,
                            TrainingTypeService trainingTypeService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingTypeService = trainingTypeService;
    }

    /**
     * Validate Trainer Creation Request Dto.
     */
    public void validateCreateTrainer(TrainerCreationRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        log.info("Validating the operation of creating a trainer");

        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();
        Long trainingTypeId = requestDto.getSpecializationId();


        if (firstName == null || firstName.isEmpty()) {
            log.error("First name is missing");
            throw new CustomIllegalArgumentException("First name is required");
        }
        if (lastName == null || lastName.isEmpty()) {
            log.error("Last name is missing");
            throw new CustomIllegalArgumentException("Last name is required");
        }

        if (trainingTypeId == null) {
            log.error("Training type id is missing");
            throw new CustomIllegalArgumentException("Specialization is required");
        }

        if (trainingTypeId <= 0) {
            log.error("Training type id is {}", trainingTypeId);
            throw new CustomIllegalArgumentException("Specialization id must be a positive integer");
        }

        if (trainingTypeService.findById(trainingTypeId).isEmpty()) {
            log.error("Training Type Id does not exist");
            throw new CustomIllegalArgumentException("Training Type Id does not exist");
        }

        log.info("Successfully validated the operation of trainer creation");
    }

    /**
     * Validate Trainer Update Request Dto.
     */
    public void validateUpdateTrainer(String pathUsername, TrainerUpdateRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");
        Assert.notNull(pathUsername, "Username must not be null");
        Assert.hasText(pathUsername, "Username must not be empty");

        log.info("Validating the operation of updating a trainer");

        String username = requestDto.getUsername();

        if (username == null || username.isEmpty()) {
            log.error("Username is missing");
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (!pathUsername.equals(username)) {
            log.error("Username provided in path does not match the one in the body");
            throw new CustomIllegalArgumentException("Path and Body usernames are not identical");
        }

        String firstName = requestDto.getFirstName();
        if (firstName == null || firstName.isEmpty()) {
            log.error("First name is missing");
            throw new CustomIllegalArgumentException("First name is required");
        }

        String lastName = requestDto.getLastName();
        if (lastName == null || lastName.isEmpty()) {
            log.error("Last name is missing");
            throw new CustomIllegalArgumentException("Last name is required");
        }

        Long specializationId = requestDto.getSpecializationId();
        if (specializationId == null) {
            log.error("Specialization id is missing");
            throw new CustomIllegalArgumentException("Specialization is required");
        }
        if (trainingTypeService.findById(specializationId).isEmpty()) {
            log.error("Training Type Id does not exist");
            throw new TrainingTypeNotFoundException(specializationId);
        }

        Boolean isActive = requestDto.getIsActive();
        if (isActive == null) {
            log.error("Is active field is missing");
            throw new CustomIllegalArgumentException("Is-active field is required");
        }

        if (trainerService.findByUsername(username).isEmpty()) {
            log.error("Trainer with the given username does not exist");
            throw new TrainerNotFoundException(username);
        }

        log.info("Successfully validated the operation of updating a trainer");
    }

    /**
     * Validate Trainer Retrieval by Username Request Dto.
     */
    public void validateRetrieveTrainer(
        TrainerRetrievalByUsernameRequestDto requestDto) {

        Assert.notNull(requestDto, "Trainer Retrieval by Username Request Dto must not be null");
        log.info("Validating the operation of retrieving a trainer");

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            log.error("Username is missing");
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (trainerService.findByUsername(username).isEmpty()) {
            log.error("Trainer with the given username does not exist");
            throw new TrainerNotFoundException(username);
        }

        log.info("Successfully validated the operation of retrieving a trainer");
    }

    /**
     * Validate Trainer Switch Activation State Request Dto.
     */
    public void validateSwitchActivationState(
        String pathUsername,
        TrainerSwitchActivationStateRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainerSwitchActivationStateRequestDto must not be null");
        Assert.notNull(pathUsername, "Path username must not be null");
        Assert.hasText(pathUsername, "Path username must not be empty");

        log.info("Validating the operation of switching activation state of trainer");

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            log.error("Username is missing");
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (!username.equals(pathUsername)) {
            log.error("Username provided in the path does not match the one in the request body");
            throw new CustomIllegalArgumentException("Body and Path usernames are not identical");
        }

        if (requestDto.getState() == null) {
            log.error("Trainer state is null");
            throw new CustomIllegalArgumentException("State is required");
        }

        if (trainerService.findByUsername(username).isEmpty()) {
            log.error("Trainer with the given username does not exist");
            throw new TrainerNotFoundException(username);
        }

        log.info("Successfully validated the operation of switching the activation state of trainer");
    }

    /**
     * Validate Retrieve All Trainers Not Assigned To Trainee Request Dto.
     */
    public void validateRetrieveAllTrainersNotAssignedToTrainee(
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto) {

        Assert.notNull(requestDto, "RetrieveAllTrainersNotAssignedToTraineeRequestDto must not be null");
        log.info("Validating the operation of retrieving all trainers not assigned to trainee");

        String traineeUsername = requestDto.getUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            log.error("Trainee username is missing");
            throw new CustomIllegalArgumentException("Trainee is required");
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            log.error("Trainee with the given usrename does not exist");
            throw new TraineeNotFoundException(traineeUsername);
        }
        log.info("Successfully validated the operation of retrieving all trainers not assigned to trainee");

    }
}
