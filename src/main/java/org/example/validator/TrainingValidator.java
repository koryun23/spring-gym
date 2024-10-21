package org.example.validator;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TrainingValidator {

    private final TrainerService trainerService;
    private final TraineeService traineeService;

    /**
     * Constructor.
     */
    public TrainingValidator(TrainerService trainerService,
                             TraineeService traineeService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    /**
     * Validate Training Creation Response Dto.
     */
    public RestResponse validateCreateTraining(TrainingCreationRequestDto requestDto) {

        String traineeUsername = requestDto.getTraineeUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee username is required"));
        }

        String trainerUsername = requestDto.getTrainerUsername();
        if (trainerUsername == null || trainerUsername.isEmpty()) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainer username is required"));
        }

        String trainingName = requestDto.getTrainingName();
        if (trainingName == null || trainingName.isEmpty()) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Training name is required"));
        }

        Long trainingDuration = requestDto.getTrainingDuration();
        if (trainingDuration == null) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Training duration is required"));
        }

        if (trainingDuration <= 0) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Training duration must be greater than 0"));
        }

        Date trainingDate = requestDto.getTrainingDate();
        if (trainingDate == null) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Training date is required"));
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee does not exist"));
        }

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainer does not exist"));
        }

        return null;
    }

    /**
     * Validate Training List Retrieval By Trainer Request Dto.
     */
    public RestResponse validateRetrieveTrainingListByTrainer(
        TrainingListRetrievalByTrainerRequestDto requestDto) {

        String trainerUsername = requestDto.getTrainerUsername();
        if (trainerUsername == null || trainerUsername.isEmpty()) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainer username is required"));
        }

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainer does not exist"));
        }

        return null;
    }

    /**
     * Validate Training List Retrieval By Trainee Request Dto.
     */
    public RestResponse validateRetrieveTrainingListByTrainee(
        TrainingListRetrievalByTraineeRequestDto requestDto) {

        String traineeUsername = requestDto.getTraineeUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee username is required"));
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            return new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Trainee does not exist"));
        }

        return null;
    }
}
