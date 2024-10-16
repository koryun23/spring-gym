package org.example.validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
import org.example.entity.TraineeEntity;
import org.example.service.core.TraineeService;
import org.example.service.core.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TraineeValidator {

    private final UserService userService;
    private final TraineeService traineeService;

    public TraineeValidator(UserService userService, TraineeService traineeService) {
        this.userService = userService;
        this.traineeService = traineeService;
    }

    public RestResponse<TraineeCreationResponseDto> validateTraineeCreation(TraineeCreationRequestDto requestDto) {

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

    public RestResponse<TraineeUpdateResponseDto> validateTraineeUpdate(TraineeUpdateRequestDto requestDto) {

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

        if (userService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("User does not exist"));
        }

        Optional<TraineeEntity> optionalTrainee = traineeService.findByUsername(username);
        if (optionalTrainee.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of(String.format("Username '%s' is already occupied", username)));
        }

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new RestResponse<>(
                null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(), List.of("Authentication failed")
            );
        }

        return null;
    }

    public RestResponse<TraineeRetrievalResponseDto> validateRetrieveTrainee(
        TraineeRetrievalByUsernameRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        if (!userService.usernamePasswordMatching(requestDto.getRetrieverUsername(),
            requestDto.getRetrieverPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(
                null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("Trainee with a username of %s not found", username)));
        }

        return null;
    }

    public RestResponse<TraineeDeletionResponseDto> validateDeleteTrainee(
        TraineeDeletionByUsernameRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        if (!userService.usernamePasswordMatching(requestDto.getDeleterUsername(), requestDto.getDeleterPassword())) {
            return new RestResponse<>(
                null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(
                null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("Trainee with a username of %s not found", username)));
        }

        return null;
    }

    public RestResponse<TraineeSwitchActivationStateResponseDto> validateSwitchActivationState(
        TraineeSwitchActivationStateRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new RestResponse<>(
                null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        if (traineeService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(
                null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("Trainee with a username of %s not found", username)));
        }

        return null;
    }
}
