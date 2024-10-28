package org.example.validator;

import org.example.dto.RestResponse;
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
    public RestResponse validateCreateTrainee(TraineeCreationRequestDto requestDto) {

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

        return null;
    }

    /**
     * Validate Trainee Update Request Dto.
     */
    public RestResponse validateUpdateTrainee(TraineeUpdateRequestDto requestDto) {

        if (requestDto == null) {
            throw new CustomIllegalArgumentException("Request body is missing");
        }

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

        Boolean isActive = requestDto.getIsActive();
        if (isActive == null) {
            throw new CustomIllegalArgumentException("is-active field is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            throw new TraineeNotFoundException(username);
        }

        return null;
    }

    /**
     * Validate Trainee Retrieval by Username Request Dto.
     */
    public RestResponse validateRetrieveTrainee(String username) {

        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            throw new TraineeNotFoundException(username);
        }

        return null;
    }

    /**
     * Validate Trainee Deletion by Username Request Dto.
     */
    public RestResponse validateDeleteTrainee(
        TraineeDeletionByUsernameRequestDto requestDto) {

        Assert.notNull(requestDto, "TraineeDeletionByUsernameRequestDto must not be null");

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            throw new TraineeNotFoundException(username);
        }

        return null;
    }

    /**
     * Validate Trainee Switch Activation State Request Dto.
     */
    public RestResponse validateSwitchActivationState(
        TraineeSwitchActivationStateRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            throw new TraineeNotFoundException(username);
        }

        return null;
    }
}
