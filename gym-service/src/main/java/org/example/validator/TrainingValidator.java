package org.example.validator;

import java.sql.Date;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
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
    public void validateCreateTraining(TrainingCreationRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainingCreationRequestDto must not be null");
        log.info("Validating the operation of creating a training");

        String traineeUsername = requestDto.getTraineeUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            log.error("Trainee username is missing");
            throw new CustomIllegalArgumentException("Trainee username is required");
        }

        String trainerUsername = requestDto.getTrainerUsername();
        if (trainerUsername == null || trainerUsername.isEmpty()) {
            log.error("Trainer username is missing");
            throw new CustomIllegalArgumentException("Trainer username is required");
        }

        String trainingName = requestDto.getTrainingName();
        if (trainingName == null || trainingName.isEmpty()) {
            log.error("Training name is missing");
            throw new CustomIllegalArgumentException("Training name is required");
        }

        Long trainingDuration = requestDto.getTrainingDuration();
        if (trainingDuration == null) {
            log.error("Training duration is missing");
            throw new CustomIllegalArgumentException("Training duration is required");
        }

        if (trainingDuration <= 0) {
            log.error("Training duration is {}", trainingDuration);
            throw new CustomIllegalArgumentException("Training duration must be greater than 0");
        }

        Date trainingDate = requestDto.getTrainingDate();
        if (trainingDate == null) {
            log.error("Training date is missing");
            throw new CustomIllegalArgumentException("Training date is required");
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            log.error("Trainee with the given username does not exist");
            throw new CustomIllegalArgumentException("Trainee does not exist");
        }

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            log.error("Trainer with the given username does not exist");
            throw new CustomIllegalArgumentException("Trainer does not exist");
        }
    }

    /**
     * Validate Training List Retrieval By Trainer Request Dto.
     */
    public void validateRetrieveTrainingListByTrainer(
        TrainingListRetrievalByTrainerRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainingListRetrievalByTrainerRequestDto must not be null");
        log.info("Validating the operation of retrieving training list by trainer");
        String trainerUsername = requestDto.getTrainerUsername();
        if (trainerUsername == null || trainerUsername.isEmpty()) {
            log.error("Trainer username is missing");
            throw new CustomIllegalArgumentException("Trainer username is required");
        }

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            log.error("Trainer with the given username does not exist");
            throw new TrainerNotFoundException("Trainer does not exist");
        }

        if (requestDto.getFrom() != null) {
            String[] from = requestDto.getFrom().split("-");
            if (from.length != 3 || from[0].length() != 4 || from[1].length() != 2 || from[2].length() != 2) {
                log.error("Date {} is of wrong format", requestDto.getFrom());
                throw new CustomIllegalArgumentException("Date format is wrong for the from argument");
            }
        }
        if (requestDto.getTo() != null) {
            String[] to = requestDto.getTo().split("-");
            if (to.length != 3 || to[0].length() != 4 || to[1].length() != 2 || to[2].length() != 2) {
                log.error("Date {} is of wrong format", requestDto.getTo());
                throw new CustomIllegalArgumentException("Date format is wrong for the to argument");
            }
        }
        log.info("Successfully validated the operation of retrieving training list by trainer");

    }

    /**
     * Validate Training List Retrieval By Trainee Request Dto.
     */
    public void validateRetrieveTrainingListByTrainee(
        TrainingListRetrievalByTraineeRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainingListRetrievalByTraineeRequestDto must not be null");
        log.info("Validating the operation of retrieving training list by trainee");

        String traineeUsername = requestDto.getTraineeUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            log.error("Trainee username is missing");
            throw new CustomIllegalArgumentException("Trainee username is required");
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            log.error("Trainee with the given username does not exist");
            throw new TraineeNotFoundException(traineeUsername);
        }

        if (requestDto.getFrom() != null) {
            String[] from = requestDto.getFrom().split("-");
            if (from.length != 3 || from[0].length() != 4 || from[1].length() != 2 || from[2].length() != 2) {
                log.error("Date {} is of wrong format", requestDto.getFrom());
                throw new CustomIllegalArgumentException("Date format is wrong for the from argument");
            }
        }
        if (requestDto.getTo() != null) {
            String[] to = requestDto.getTo().split("-");
            if (to.length != 3 || to[0].length() != 4 || to[1].length() != 2 || to[2].length() != 2) {
                log.error("Date {} is of wrong format", requestDto.getTo());
                throw new CustomIllegalArgumentException("Date format is wrong for the to argument");
            }
        }

        log.info("Successfully validated the operation of retrieving training list by trainee");
    }
}
