package org.example.validator;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.TraineeNotFoundException;
import org.example.service.core.trainee.TraineeService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeValidator {

    private final TraineeService traineeService;

    /**
     * Constructor.
     */
    public TraineeValidator(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    /**
     * Validate Trainee Creation Request Dto.
     */
    public void validateCreateTrainee(TraineeCreationRequestDto requestDto) {

        if (requestDto == null) {
            throw new CustomIllegalArgumentException("Request body is missing");
        }
        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();

        if (firstName == null || firstName.isEmpty()) {
            throw new CustomIllegalArgumentException("First name is required");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new CustomIllegalArgumentException("Last name is required");
        }
    }

    /**
     * Validate Trainee Update Request Dto.
     */
    public void validateUpdateTrainee(String pathUsername, TraineeUpdateRequestDto requestDto) {

        if (pathUsername == null || pathUsername.isEmpty()) {
            throw new CustomIllegalArgumentException("Username must be passed in the path");
        }

        if (requestDto == null) {
            throw new CustomIllegalArgumentException("Request body is missing");
        }

        String bodyUsername = requestDto.getUsername();
        if (bodyUsername == null || bodyUsername.isEmpty()) {
            throw new CustomIllegalArgumentException("Username must be passed in the body");
        }

        if (!pathUsername.equals(bodyUsername)) {
            throw new CustomIllegalArgumentException("Usernames passed in body and path must match");
        }

        String firstName = requestDto.getFirstName();
        if (firstName == null || firstName.isEmpty()) {
            throw new CustomIllegalArgumentException("First name is required");
        }

        String lastName = requestDto.getLastName();
        if (lastName == null || lastName.isEmpty()) {
            throw new CustomIllegalArgumentException("Last name is required");
        }

        Boolean isActive = requestDto.getIsActive();
        if (isActive == null) {
            throw new CustomIllegalArgumentException("is-active field is required");
        }

        if (traineeService.findByUsername(bodyUsername).isEmpty()) {
            throw new TraineeNotFoundException(bodyUsername);
        }
    }

    /**
     * Validate Trainee Retrieval by Username Request Dto.
     */
    public void validateRetrieveTrainee(String username) {

        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            throw new TraineeNotFoundException(username);
        }
    }

    /**
     * Validate Trainee Deletion by Username Request Dto.
     */
    public void validateDeleteTrainee(
        TraineeDeletionByUsernameRequestDto requestDto) {

        Assert.notNull(requestDto, "TraineeDeletionByUsernameRequestDto must not be null");

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            throw new TraineeNotFoundException(username);
        }
    }

    /**
     * Validate Trainee Switch Activation State Request Dto.
     */
    public void validateSwitchActivationState(
        TraineeSwitchActivationStateRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (requestDto.getState() == null) {
            throw new CustomIllegalArgumentException("State is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            throw new TraineeNotFoundException(username);
        }
    }
}
