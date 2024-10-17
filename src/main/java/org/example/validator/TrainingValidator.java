package org.example.validator;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.example.service.core.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TrainingValidator {

    private final TrainingService trainingService;
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final UserService userService;

    public TrainingValidator(TrainingService trainingService, TrainerService trainerService,
                             TraineeService traineeService, UserService userService) {
        this.trainingService = trainingService;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.userService = userService;
    }

    public RestResponse<TrainingCreationResponseDto> validateCreateTraining(TrainingCreationRequestDto requestDto) {

        String traineeUsername = requestDto.getTraineeUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee username is required"));
        }

        String trainerUsername = requestDto.getTrainerUsername();
        if (trainerUsername == null || trainerUsername.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainer username is required"));
        }

        String trainingName = requestDto.getTrainingName();
        if (trainingName == null || trainingName.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Training name is required"));
        }

        Long trainingDuration = requestDto.getTrainingDuration();
        if (trainingDuration == null) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Training duration is required"));
        }

        if (trainingDuration <= 0) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Training duration must be greater than 0"));
        }

        Date trainingDate = requestDto.getTrainingDate();
        if (trainingDate == null) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Training date is required"));
        }

        if (!userService.usernamePasswordMatching(requestDto.getCreatorUsername(), requestDto.getCreatorPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee does not exist"));
        }

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainer does not exist"));
        }

        return null;
    }

    public RestResponse<TrainingListRetrievalResponseDto> validateRetrieveTrainingListByTrainer(
        TrainingListRetrievalByTrainerRequestDto requestDto) {

        String retrieverUsername = requestDto.getRetrieverUsername();
        String retrieverPassword = requestDto.getRetrieverPassword();

        if (!userService.usernamePasswordMatching(retrieverUsername, retrieverPassword)) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        String trainerUsername = requestDto.getTrainerUsername();
        if (trainerUsername == null || trainerUsername.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainer username is required"));
        }

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainer does not exist"));
        }

        return null;
    }

    public RestResponse<TrainingListRetrievalResponseDto> validateRetrieveTrainingListByTrainee(
        TrainingListRetrievalByTraineeRequestDto requestDto) {

        String retrieverUsername = requestDto.getRetrieverUsername();
        String retrieverPassword = requestDto.getRetrieverPassword();

        if (!userService.usernamePasswordMatching(retrieverUsername, retrieverPassword)) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        String traineeUsername = requestDto.getTraineeUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee username is required"));
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee does not exist"));
        }

        return null;
    }
}
