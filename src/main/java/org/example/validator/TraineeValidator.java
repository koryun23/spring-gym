package org.example.validator;

import java.time.LocalDateTime;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeRetrievalByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeSwitchActivationStateResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.service.core.TraineeService;
import org.example.service.core.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeValidator {

    private final UserService userService;
    private final TraineeService traineeService;

    /**
     * Constructor.
     */
    public TraineeValidator(UserService userService, TraineeService traineeService) {
        this.userService = userService;
        this.traineeService = traineeService;
    }

    /**
     * Validate Trainee Creation Request Dto.
     */
    public RestResponse<TraineeCreationResponseDto> validateCreateTrainee(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();

        if (firstName == null || firstName.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("first name is required"));
        }
        if (lastName == null || lastName.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("last name is required"));
        }

        return null;
    }

    /**
     * Validate Trainee Update Request Dto.
     */
    public RestResponse<TraineeUpdateResponseDto> validateUpdateTrainee(TraineeUpdateRequestDto requestDto) {

        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        String firstName = requestDto.getFirstName();
        if (firstName == null || firstName.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("First name is required"));
        }

        String lastName = requestDto.getLastName();
        if (lastName == null || lastName.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Last name is required"));
        }

        Boolean isActive = requestDto.getIsActive();
        if (isActive == null) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("is-active field is required"));
        }

        if (userService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("User does not exist"));
        }

        return null;
    }

    /**
     * Validate Trainee Retrieval by Username Request Dto.
     */
    public RestResponse<TraineeRetrievalResponseDto> validateRetrieveTrainee(
        TraineeRetrievalByUsernameRequestDto requestDto) {

        Assert.notNull(requestDto, "TraineeRetrievalByUsernameRequestDto must not be null");
        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(
                null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("Trainee with a username of %s not found", username)));
        }

        return null;
    }

    /**
     * Validate Trainee Deletion by Username Request Dto.
     */
    public RestResponse<TraineeDeletionResponseDto> validateDeleteTrainee(
        TraineeDeletionByUsernameRequestDto requestDto) {

        Assert.notNull(requestDto, "TraineeDeletionByUsernameRequestDto must not be null");

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(
                null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("Trainee with a username of %s not found", username)));
        }

        return null;
    }

    /**
     * Validate Trainee Switch Activation State Request Dto.
     */
    public RestResponse<TraineeSwitchActivationStateResponseDto> validateSwitchActivationState(
        TraineeSwitchActivationStateRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(
                null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("Trainee with a username of %s not found", username)));
        }

        return null;
    }
}
