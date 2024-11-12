package org.example.validator;

import org.example.dto.RestResponse;
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
    public RestResponse validateCreateTrainer(TrainerCreationRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");

        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();
        Long trainingTypeId = requestDto.getSpecializationId();

        if (firstName == null || firstName.isEmpty()) {
            throw new CustomIllegalArgumentException("First name is required");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new CustomIllegalArgumentException("Last name is required");
        }

        if (trainingTypeId == null) {
            throw new CustomIllegalArgumentException("Specialization is required");
        }

        if (trainingTypeId <= 0) {
            throw new CustomIllegalArgumentException("Specialization id must be a positive integer");
        }

        if (trainingTypeService.findById(trainingTypeId).isEmpty()) {
            throw new CustomIllegalArgumentException("Training Type Id does not exist");
        }
        return null;
    }

    /**
     * Validate Trainer Update Request Dto.
     */
    public RestResponse validateUpdateTrainer(TrainerUpdateRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");
        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        String firstName = requestDto.getFirstName();
        if (firstName == null || firstName.isEmpty()) {
            throw new CustomIllegalArgumentException("First name is required");
        }

        String lastName = requestDto.getLastName();
        if (lastName == null || lastName.isEmpty()) {
            throw new CustomIllegalArgumentException("Last name is required");
        }

        Long specializationId = requestDto.getSpecializationId();
        if (specializationId == null) {
            throw new CustomIllegalArgumentException("Specialization is required");
        }
        if (trainingTypeService.findById(specializationId).isEmpty()) {
            throw new TrainingTypeNotFoundException(specializationId);
        }

        Boolean isActive = requestDto.getIsActive();
        if (isActive == null) {
            throw new CustomIllegalArgumentException("Is-active field is required");
        }

        if (trainerService.findByUsername(username).isEmpty()) {
            throw new TrainerNotFoundException(username);
        }

        return null;
    }

    /**
     * Validate Trainer Retrieval by Username Request Dto.
     */
    public RestResponse validateRetrieveTrainer(
        TrainerRetrievalByUsernameRequestDto requestDto) {

        Assert.notNull(requestDto, "Trainer Retrieval by Username Request Dto must not be null");
        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (trainerService.findByUsername(username).isEmpty()) {
            throw new TrainerNotFoundException(username);
        }

        return null;
    }

    /**
     * Validate Trainer Switch Activation State Request Dto.
     */
    public RestResponse validateSwitchActivationState(
        TrainerSwitchActivationStateRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainerSwitchActivationStateRequestDto must not be null");
        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (trainerService.findByUsername(username).isEmpty()) {
            throw new TrainerNotFoundException(username);
        }

        return null;
    }

    /**
     * Validate Retrieve All Trainers Not Assigned To Trainee Request Dto.
     */
    public RestResponse validateRetrieveAllTrainersNotAssignedToTrainee(
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto) {

        String traineeUsername = requestDto.getUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            throw new CustomIllegalArgumentException("Trainee is required");
        }
        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            throw new TraineeNotFoundException(traineeUsername);
        }

        return null;
    }
}
