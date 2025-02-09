package org.example.validator;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.TraineeNotFoundException;
import org.example.service.core.trainee.TraineeService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
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
            log.error("TraineeCreationRequestDto is missing");
            throw new CustomIllegalArgumentException("Request body is missing");
        }
        log.info("Validating the operation of creating a trainee, request dto - {}", requestDto);
        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();

        if (firstName == null || firstName.isEmpty()) {
            log.error("First name is missing");
            throw new CustomIllegalArgumentException("First name is required");
        }
        if (lastName == null || lastName.isEmpty()) {
            log.error("Last name is missing");
            throw new CustomIllegalArgumentException("Last name is required");
        }

        log.info("Successfully validated the operation of creating a trainee");
    }

    /**
     * Validate Trainee Update Request Dto.
     */
    public void validateUpdateTrainee(String pathUsername, TraineeUpdateRequestDto requestDto) {

        if (pathUsername == null || pathUsername.isEmpty()) {
            log.error("pathUsername is missing");
            throw new CustomIllegalArgumentException("Username must be passed in the path");
        }

        if (requestDto == null) {
            log.error("TraineeUpdateRequestDto is missing");
            throw new CustomIllegalArgumentException("Request body is missing");
        }

        String bodyUsername = requestDto.getUsername();
        if (bodyUsername == null || bodyUsername.isEmpty()) {
            log.error("No username was provided in the request body");
            throw new CustomIllegalArgumentException("Username must be passed in the body");
        }

        if (!pathUsername.equals(bodyUsername)) {
            log.error("Username provided in the request body did not match the one in the path");
            throw new CustomIllegalArgumentException("Usernames passed in body and path must match");
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

        Boolean isActive = requestDto.getIsActive();
        if (isActive == null) {
            log.error("The field indicating the state of trainee is missing");
            throw new CustomIllegalArgumentException("is-active field is required");
        }

        if (traineeService.findByUsername(bodyUsername).isEmpty()) {
            log.error("Trainee with the given username does not exist");
            throw new TraineeNotFoundException(bodyUsername);
        }
        log.info("Successfully validated the operation of updating a trainee");
    }

    /**
     * Validate Trainee Retrieval by Username Request Dto.
     */
    public void validateRetrieveTrainee(String username) {

        if (username == null || username.isEmpty()) {
            log.error("Username is missing");
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            log.error("Trainee with the given username does not exist");
            throw new TraineeNotFoundException(username);
        }
        log.info("Successfully validated the operation of retrieving a trainee");
    }

    /**
     * Validate Trainee Deletion by Username Request Dto.
     */
    public void validateDeleteTrainee(
        TraineeDeletionByUsernameRequestDto requestDto) {

        Assert.notNull(requestDto, "TraineeDeletionByUsernameRequestDto must not be null");

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            log.error("Username is missing");
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            log.error("Trainee with the given username does not exist");
            throw new TraineeNotFoundException(username);
        }

        log.info("Successfully validated the operation of deleting a trainee");
    }

    /**
     * Validate Trainee Switch Activation State Request Dto.
     */
    public void validateSwitchActivationState(
        TraineeSwitchActivationStateRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            log.error("Username is missing");
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (requestDto.getState() == null) {
            log.error("State of trainee is missing");
            throw new CustomIllegalArgumentException("State is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            log.error("Trainee with the given username does not exist");
            throw new TraineeNotFoundException(username);
        }
        log.info("Successfully validated the operation of switching activation state");
    }
}
