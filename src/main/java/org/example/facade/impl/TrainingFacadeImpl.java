package org.example.facade.impl;

import java.util.Date;
import java.util.List;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainingEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.facade.core.TrainingFacade;
import org.example.mapper.training.TrainingCreationRequestDtoToTrainingEntityMapper;
import org.example.mapper.training.TrainingEntityToTrainingCreationResponseDtoMapper;
import org.example.mapper.training.TrainingEntityToTrainingRetrievalResponseDtoMapper;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingFacadeImpl implements TrainingFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingFacadeImpl.class);

    private final TrainingService trainingService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingCreationRequestDtoToTrainingEntityMapper trainingCreationRequestDtoToTrainingEntityMapper;
    private final TrainingEntityToTrainingCreationResponseDtoMapper trainingEntityToTrainingCreationResponseDtoMapper;
    private final TrainingEntityToTrainingRetrievalResponseDtoMapper trainingEntityToTrainingRetrievalResponseDtoMapper;

    /**
     * Constructor.
     */
    public TrainingFacadeImpl(TrainingService trainingService,
                              TraineeService traineeService,
                              TrainerService trainerService,
                              TrainingCreationRequestDtoToTrainingEntityMapper
                                  trainingCreationRequestDtoToTrainingEntityMapper,
                              TrainingEntityToTrainingCreationResponseDtoMapper
                                  trainingEntityToTrainingCreationResponseDtoMapper,
                              TrainingEntityToTrainingRetrievalResponseDtoMapper
                                  trainingEntityToTrainingRetrievalResponseDtoMapper) {
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingCreationRequestDtoToTrainingEntityMapper = trainingCreationRequestDtoToTrainingEntityMapper;
        this.trainingEntityToTrainingCreationResponseDtoMapper = trainingEntityToTrainingCreationResponseDtoMapper;
        this.trainingEntityToTrainingRetrievalResponseDtoMapper = trainingEntityToTrainingRetrievalResponseDtoMapper;
    }

    @Override
    public TrainingCreationResponseDto createTraining(TrainingCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingCreationRequestDto");
        LOGGER.info("Creating a TrainingEntity according to the TrainingCreationRequestDto - {}", requestDto);

        if (requestDto.getTraineeId() <= 0) {
            return new TrainingCreationResponseDto(
                List.of(String.format("The trainee id must be positive: %d specified", requestDto.getTraineeId())));
        }

        if (requestDto.getTrainerId() <= 0) {
            return new TrainingCreationResponseDto(
                List.of(String.format("The trainer id must be positive: %d specified", requestDto.getTrainerId())));

        }
        if (traineeService.findById(requestDto.getTraineeId()).isEmpty()) {
            return new TrainingCreationResponseDto(List.of(
                String.format("Cannot create a trainingEntity: a trainee with an id of %d does not exist",
                    requestDto.getTraineeId())));
        }

        if (trainerService.findById(requestDto.getTrainerId()).isEmpty()) {
            return new TrainingCreationResponseDto(List.of(
                String.format("Cannot create a trainingEntity: a trainer with an id of %d does not exist",
                    requestDto.getTrainerId())));
        }

        TrainingCreationResponseDto responseDto = trainingEntityToTrainingCreationResponseDtoMapper.map(
            trainingService.create(trainingCreationRequestDtoToTrainingEntityMapper.map(requestDto)));

        LOGGER.info(
            "Successfully created a TrainingEntity according to the TrainingCreationRequestDto - {}, response - {}",
            requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainingRetrievalResponseDto retrieveTraining(Long trainingId) {
        Assert.notNull(trainingId, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving a TrainingEntity with an id of {}", trainingId);

        if (trainingId <= 0) {
            return new TrainingRetrievalResponseDto(
                List.of(String.format("TrainingEntity id must be positive: %d specified", trainingId)));
        }

        if (trainingService.findById(trainingId).isEmpty()) {
            return new TrainingRetrievalResponseDto(
                List.of(String.format("TrainingEntity with a specified id of %d does not exist", trainingId)));
        }

        TrainingRetrievalResponseDto responseDto =
            trainingEntityToTrainingRetrievalResponseDtoMapper.map(trainingService.select(trainingId));

        LOGGER.info("Successfully retrieved a TrainingEntity with an id of {}, response - {}", trainingId, responseDto);
        return responseDto;
    }


    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTrainee(String traineeUsername) {
        Assert.notNull(traineeUsername, "Username must not be null");
        Assert.hasText(traineeUsername, "Username must not be empty");
        LOGGER.info("Retrieving the list of trainings of a trainee with a username of {}", traineeUsername);

        TraineeEntity traineeEntity = traineeService.findByUsername(traineeUsername)
            .orElseThrow(() -> new TraineeNotFoundException(traineeUsername));
        List<TrainingEntity> trainingEntityList = traineeEntity.getTrainingEntityList().stream()
            .filter((trainingEntity) -> trainingEntity.getTrainee().getUser().getUsername().equals(traineeUsername))
            .toList();
        List<TrainingRetrievalResponseDto> trainingRetrievalResponseDtoList = trainingEntityList.stream()
            .map((trainingEntity) -> new TrainingRetrievalResponseDto(
                trainingEntity.getId(),
                trainingEntity.getTrainee().getId(),
                trainingEntity.getTrainer().getId(),
                trainingEntity.getName(),
                trainingEntity.getTrainingType().getId(),
                trainingEntity.getDate(),
                trainingEntity.getDuration()
            )).toList();

        TrainingListRetrievalResponseDto responseDto =
            new TrainingListRetrievalResponseDto(true, trainingRetrievalResponseDtoList);

        LOGGER.info("Successfully retrieved the list of trainings of a trainee with a username of {}, result - {}",
            traineeUsername, responseDto);
        return responseDto;
    }

    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTraineeDate(String traineeUsername, Date from, Date to) {
        Assert.notNull(traineeUsername, "Username must not be null");
        Assert.hasText(traineeUsername, "Username must not be empty");
        Assert.notNull(from, "The starting date must not be null");
        Assert.notNull(to, "Ending date must not be null");

        LOGGER.info("Retrieving a list of Trainings of a trainee with a username of {} between {} and to", from, to);
        TraineeEntity traineeEntity = traineeService.findByUsername(traineeUsername)
            .orElseThrow(() -> new TraineeNotFoundException(traineeUsername));
        List<TrainingRetrievalResponseDto> trainingEntityList = traineeEntity.getTrainingEntityList()
            .stream()
            .filter((trainingEntity) -> trainingEntity.getDate().compareTo(from) >= 0 && trainingEntity.getDate().compareTo(to) <= 0)
            .map((trainingEntity) -> new TrainingRetrievalResponseDto(
                trainingEntity.getId(),
                trainingEntity.getTrainee().getId(),
                trainingEntity.getTrainer().getId(),
                trainingEntity.getName(),
                trainingEntity.getTrainingType().getId(),
                trainingEntity.getDate(),
                trainingEntity.getDuration()
            ))
            .toList();

        TrainingListRetrievalResponseDto responseDto =
            new TrainingListRetrievalResponseDto(true, trainingEntityList);

        LOGGER.info(
            "Successfully retrieved a list of Trainings of a trainee with a username of {} between {} and {}, result - {}",
            traineeUsername, from, to, responseDto
        );
        return responseDto;
    }

    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTraineeTrainerDate(String traineeUsername, Date from, Date to,
                                                                 String trainerUsername) {
        Assert.notNull(traineeUsername, "Trainee Username must not be null");
        Assert.hasText(traineeUsername, "Trainee Username must not be empty");
        Assert.notNull(trainerUsername, "Trainer username must not be null");
        Assert.hasText(trainerUsername, "Trainer username must not be empty");
        Assert.notNull(from, "Starting date must not be null");
        Assert.notNull(to, "Ending date must not be null");

        LOGGER.info(
            "Retrieving a list of Trainings of a trainee with a username of {} between {} and {} with a trainer with a username of {}",
            traineeUsername, from, to, trainerUsername
        );

        TraineeEntity traineeEntity = traineeService.findByUsername(traineeUsername)
            .orElseThrow(() -> new TraineeNotFoundException(traineeUsername));

        List<TrainingRetrievalResponseDto> trainingResponseDtoList = traineeEntity.getTrainingEntityList().stream()
            .filter((trainingEntity) ->
                trainingEntity.getTrainer().getUser().getUsername().equals(trainerUsername) &&
                    trainingEntity.getDate().compareTo(from) >= 0 &&
                    trainingEntity.getDate().compareTo(to) <= 0
            )
            .map((trainingEntity -> new TrainingRetrievalResponseDto(
                trainingEntity.getId(),
                trainingEntity.getTrainee().getId(),
                trainingEntity.getTrainer().getId(),
                trainingEntity.getName(),
                trainingEntity.getTrainingType().getId(),
                trainingEntity.getDate(),
                trainingEntity.getDuration()
            )))
            .toList();
        TrainingListRetrievalResponseDto responseDto =
            new TrainingListRetrievalResponseDto(true, trainingResponseDtoList);

        LOGGER.info("Successfully retrieved all trainings of a trainee with a username of {} between {} and {} with a trainer with a usrename of {}",
            traineeUsername, from, to, trainerUsername);

        return responseDto;

    }

    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTraineeTrainer(String traineeUsername, String trainerUsername) {
        Assert.notNull(traineeUsername, "Trainee Username must not be null");
        Assert.notNull(trainerUsername, "Trainer Username must not be null");
        Assert.hasText(traineeUsername, "Trainee Username must not be empty");
        Assert.hasText(trainerUsername, "Trainer Username must not be empty");

        LOGGER.info("Retrieving a list of Trainings of a trainee with a username of {} with a trainer with a username of {}",
            traineeUsername, trainerUsername);

        TraineeEntity traineeEntity =
            traineeService.findByUsername(traineeUsername).orElseThrow(() -> new TraineeNotFoundException(traineeUsername));

        List<TrainingRetrievalResponseDto> trainingRetrievalResponseDtoList = traineeEntity.getTrainingEntityList().stream()
            .filter((trainingEntity) -> trainingEntity.getTrainer().getUser().getUsername().equals(trainerUsername))
            .map((trainingEntity) -> new TrainingRetrievalResponseDto(
                trainingEntity.getId(),
                trainingEntity.getTrainee().getId(),
                trainingEntity.getTrainer().getId(),
                trainingEntity.getName(),
                trainingEntity.getTrainingType().getId(),
                trainingEntity.getDate(),
                trainingEntity.getDuration()
            ))
            .toList();

        TrainingListRetrievalResponseDto responseDto =
            new TrainingListRetrievalResponseDto(true, trainingRetrievalResponseDtoList);

        LOGGER.info("Successfully retrieved a list of Trainings of a trainee with a username of {} with a trainer with a username of {}", traineeUsername, trainerUsername);

        return responseDto;
    }
}
