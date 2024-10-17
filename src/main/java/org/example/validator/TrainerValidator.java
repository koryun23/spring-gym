package org.example.validator;

import java.time.LocalDateTime;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TraineeTrainerListUpdateRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TraineeTrainerListUpdateResponseDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerListRetrievalResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerSwitchActivationStateResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TrainerValidator {

    private final TrainerService trainerService;
    private final UserService userService;
    private final TraineeService traineeService;

    public TrainerValidator(TrainerService trainerService, UserService userService, TraineeService traineeService) {
        this.trainerService = trainerService;
        this.userService = userService;
        this.traineeService = traineeService;
    }

    public RestResponse<TrainerCreationResponseDto> validateCreateTrainer(TrainerCreationRequestDto requestDto) {

        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();
        Long trainingTypeId = requestDto.getTrainingTypeId();

        if (firstName == null || firstName.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("First name is required"));
        }
        if (lastName == null || lastName.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Last name is required"));
        }

        if (trainingTypeId == null) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Specialization is required"));
        }

        if (trainingTypeId <= 0) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Specialization id must be a positive integer"));
        }

        return null;
    }

    public RestResponse<TrainerUpdateResponseDto> validateUpdateTrainer(TrainerUpdateRequestDto requestDto) {

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

        TrainingTypeDto specialization = requestDto.getSpecialization();
        if (specialization == null) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Specialization is required"));
        }

        Boolean isActive = requestDto.getIsActive();
        if (isActive == null) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Is-active field is required"));
        }

        if (userService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("User does not exist"));
        }

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        return null;
    }

    public RestResponse<TrainerRetrievalResponseDto> validateRetrieveTrainer(
        TrainerRetrievalByUsernameRequestDto requestDto) {

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

        if (trainerService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("Trainee with a username of %s not found", username)));
        }

        return null;
    }

    public RestResponse<TrainerSwitchActivationStateResponseDto> validateSwitchActivationState(
        TrainerSwitchActivationStateRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        if (trainerService.findByUsername(username).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("Trainee with a username of %s not found", username)));
        }

        return null;
    }

    public RestResponse<TrainerListRetrievalResponseDto> validateRetrieveAllTrainersNotAssignedToTrainee(
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto) {

        String traineeUsername = requestDto.getUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee is required"));
        }
        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee does not exist"));
        }
        if (!userService.usernamePasswordMatching(requestDto.getRetrieverUsername(),
            requestDto.getRetrieverPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        return null;
    }

    public RestResponse<TraineeTrainerListUpdateResponseDto> validateUpdateTraineeTrainerList(
        TraineeTrainerListUpdateRequestDto requestDto) {

        String traineeUsername = requestDto.getTraineeUsername();

        if (traineeUsername == null || traineeUsername.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee does not exist"));
        }

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        return null;
    }
}
