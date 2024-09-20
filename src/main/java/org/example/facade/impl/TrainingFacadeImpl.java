package org.example.facade.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeDateRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeTrainerDateRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeTrainerRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerDateRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
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
    public TrainingListRetrievalResponseDto retrieveTrainingListByTrainer(TrainingListRetrievalByTrainerRequestDto requestDto) {
        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retrieving the list of all trainings according to the request dto - {}", requestDto);

        String retrieverUsername = requestDto.getRetrieverUsername();
        String retrieverPassword = requestDto.getRetrieverPassword();

        String trainerUsername = requestDto.getTrainerUsername();

        if(!retrieverUsername.equals(trainerUsername)) {
            return new TrainingListRetrievalResponseDto(
                List.of("Authentication failed")
            );
        }

        Optional<TrainerEntity> optionalRetriever = trainerService.findByUsername(retrieverUsername);
        if(optionalRetriever.isEmpty()) {
            return new TrainingListRetrievalResponseDto(
                List.of(String.format("Trainer with a username of %s was not found", retrieverUsername))
            );
        }

        TrainerEntity retriever = optionalRetriever.get();
        if(!retriever.getUser().getPassword().equals(retrieverPassword)) {
            return new TrainingListRetrievalResponseDto(
                List.of("Authentication failed")
            );
        }

        List<TrainingRetrievalResponseDto> all = trainingService.findAllByTrainer(trainerUsername).stream()
            .map(trainingEntityToTrainingRetrievalResponseDtoMapper::map)
            .toList();

        TrainingListRetrievalResponseDto responseDto = new TrainingListRetrievalResponseDto(true, all);

        LOGGER.info("Successfully retrieved the list of all trainings of a trainer with a username of {}, result - {}",
            trainerUsername, responseDto
        );
        return responseDto;
    }


    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTrainee(TrainingListRetrievalByTraineeRequestDto requestDto) {
        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retrieving the list of trainings according to the request dto - {}", requestDto);

        String retrieverUsername = requestDto.getRetrieverUsername();
        String retrieverPassword = requestDto.getRetrieverPassword();

        String traineeUsername = requestDto.getTraineeUsername();

        if(!retrieverUsername.equals(traineeUsername)) {
            return new TrainingListRetrievalResponseDto(
                List.of("Authentication failed")
            );
        }

        Optional<TraineeEntity> optionalTrainee = traineeService.findByUsername(retrieverUsername);
        if(optionalTrainee.isEmpty()) {
            return new TrainingListRetrievalResponseDto(
                List.of(String.format("Trainee with a username of %s was not found", retrieverUsername))
            );
        }
        TraineeEntity traineeEntity = optionalTrainee.get();
        if(!traineeEntity.getUser().getPassword().equals(retrieverPassword)) {
            return new TrainingListRetrievalResponseDto(
                List.of("Authentication failed")
            );
        }
        List<TrainingRetrievalResponseDto> all = trainingService.findAllByTrainee(traineeUsername).stream()
            .map(trainingEntityToTrainingRetrievalResponseDtoMapper::map)
            .toList();

        TrainingListRetrievalResponseDto responseDto = new TrainingListRetrievalResponseDto(true, all);
        LOGGER.info("Successfully retrieved the list of trainings of a trainee with a username of {}, result - {}",
            traineeUsername, responseDto);
        return responseDto;
    }

    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTraineeDate(
        TrainingListRetrievalByTraineeDateRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainingListRetrievalByTraineeDateRequestDto must not be null");
        LOGGER.info("Retrieving a list of all trainings according to the TrainingListRetrievalByTraineeDateRequestDto - {}", requestDto);

        List<TrainingRetrievalResponseDto> all =
            trainingService.findAllByTraineeDate(requestDto.getTraineeUsername(), requestDto.getFrom(),
                    requestDto.getTo())
                .stream()
                .map(trainingEntityToTrainingRetrievalResponseDtoMapper::map)
                .toList();

        TrainingListRetrievalResponseDto responseDto = new TrainingListRetrievalResponseDto(true, all);
        LOGGER.info("Successfully retrieved a list of all trainings according to the TrainingListRetrievalByTraineeDateRequestDto - {}, result - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTrainerDate(
        TrainingListRetrievalByTrainerDateRequestDto requestDto) {

        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retreiving a training list according to the request dto - {}", requestDto);

        List<TrainingRetrievalResponseDto> all =
            trainingService.findAllByTrainerDate(requestDto.getTrainerUsername(), requestDto.getFrom(),
                    requestDto.getTo())
                .stream()
                .map(trainingEntityToTrainingRetrievalResponseDtoMapper::map)
                .toList();

        TrainingListRetrievalResponseDto responseDto = new TrainingListRetrievalResponseDto(true, all);
        LOGGER.info("Successfully retrieved a training list according to the request dto - {}, result - {}", requestDto, responseDto);

        return responseDto;
    }

    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTraineeTrainerDate(
        TrainingListRetrievalByTraineeTrainerDateRequestDto requestDto) {
        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retrieving a training list according to the request dto - {}", requestDto);

        List<TrainingRetrievalResponseDto> all = trainingService.findAllByTraineeTrainerDate(
                requestDto.getTraineeUsername(), requestDto.getFrom(), requestDto.getTo(), requestDto.getTrainerUsername()
                )
            .stream()
            .map(trainingEntityToTrainingRetrievalResponseDtoMapper::map)
            .toList();

        TrainingListRetrievalResponseDto responseDto = new TrainingListRetrievalResponseDto(true, all);

        LOGGER.info("Successfully retrieved a training list according to the request dto - {}, result - {}", requestDto, responseDto);
        return responseDto;
    }


    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTraineeTrainer(
        TrainingListRetrievalByTraineeTrainerRequestDto requestDto) {

        Assert.notNull(requestDto, "Request dto must not be null");
        LOGGER.info("Retrieving a training list according to the request dto - {}", requestDto);

        List<TrainingRetrievalResponseDto> all =
            trainingService.findAllByTraineeTrainer(requestDto.getTraineeUsername(), requestDto.getTrainerUsername())
                .stream()
                .map(trainingEntityToTrainingRetrievalResponseDtoMapper::map)
                .toList();

        TrainingListRetrievalResponseDto responseDto = new TrainingListRetrievalResponseDto(true, all);

        LOGGER.info("Successfully retrieved a training list according to the request dto - {}, result - {}", requestDto, responseDto);
        return responseDto;
    }
}
