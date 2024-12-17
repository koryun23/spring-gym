package org.example.validator;

import java.sql.Date;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
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
    public void validateCreateTraining(TrainingCreationRequestDto requestDto) {

        String traineeUsername = requestDto.getTraineeUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            throw new CustomIllegalArgumentException("Trainee username is required");
        }

        String trainerUsername = requestDto.getTrainerUsername();
        if (trainerUsername == null || trainerUsername.isEmpty()) {
            throw new CustomIllegalArgumentException("Trainer username is required");
        }

        String trainingName = requestDto.getTrainingName();
        if (trainingName == null || trainingName.isEmpty()) {
            throw new CustomIllegalArgumentException("Training name is required");
        }

        Long trainingDuration = requestDto.getTrainingDuration();
        if (trainingDuration == null) {
            throw new CustomIllegalArgumentException("Training duration is required");
        }

        if (trainingDuration <= 0) {
            throw new CustomIllegalArgumentException("Training duration must be greater than 0");
        }

        Date trainingDate = requestDto.getTrainingDate();
        if (trainingDate == null) {
            throw new CustomIllegalArgumentException("Training date is required");
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            throw new CustomIllegalArgumentException("Trainee does not exist");
        }

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            throw new CustomIllegalArgumentException("Trainer does not exist");
        }
    }

    /**
     * Validate Training List Retrieval By Trainer Request Dto.
     */
    public void validateRetrieveTrainingListByTrainer(
        TrainingListRetrievalByTrainerRequestDto requestDto) {

        String trainerUsername = requestDto.getTrainerUsername();
        if (trainerUsername == null || trainerUsername.isEmpty()) {
            throw new CustomIllegalArgumentException("Trainer username is required");
        }

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            throw new TrainerNotFoundException("Trainer does not exist");
        }
        if (requestDto.getFrom() != null) {
            String[] from = requestDto.getFrom().split("-");
            if (from.length != 3 || from[0].length() != 4 || from[1].length() != 2 || from[2].length() != 2) {
                throw new CustomIllegalArgumentException("Date format is wrong for the from argument");
            }
        }
        if (requestDto.getTo() != null) {
            String[] to = requestDto.getTo().split("-");
            if (to.length != 3 || to[0].length() != 4 || to[1].length() != 2 || to[2].length() != 2) {
                throw new CustomIllegalArgumentException("Date format is wrong for the to argument");
            }
        }
    }

    /**
     * Validate Training List Retrieval By Trainee Request Dto.
     */
    public void validateRetrieveTrainingListByTrainee(
        TrainingListRetrievalByTraineeRequestDto requestDto) {

        String traineeUsername = requestDto.getTraineeUsername();
        if (traineeUsername == null || traineeUsername.isEmpty()) {
            throw new CustomIllegalArgumentException("Trainee username is required");
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            throw new TraineeNotFoundException(traineeUsername);
        }

        if (requestDto.getFrom() != null) {
            String[] from = requestDto.getFrom().split("-");
            if (from.length != 3 || from[0].length() != 4 || from[1].length() != 2 || from[2].length() != 2) {
                throw new CustomIllegalArgumentException("Date format is wrong for the from argument");
            }
        }
        if (requestDto.getTo() != null) {
            String[] to = requestDto.getTo().split("-");
            if (to.length != 3 || to[0].length() != 4 || to[1].length() != 2 || to[2].length() != 2) {
                throw new CustomIllegalArgumentException("Date format is wrong for the to argument");
            }
        }
    }
}
