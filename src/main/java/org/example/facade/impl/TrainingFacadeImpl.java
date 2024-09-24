package org.example.facade.impl;

import java.util.List;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.request.TrainingUpdateRequestDto;
import org.example.dto.response.MultipleTrainingDeletionByTraineeResponseDto;
import org.example.dto.response.MultipleTrainingDeletionByTrainerResponseDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.dto.response.TrainingUpdateResponseDto;
import org.example.entity.TrainingEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.facade.core.TrainingFacade;
import org.example.mapper.training.TrainingCreationRequestDtoToTrainingEntityMapper;
import org.example.mapper.training.TrainingEntityToTrainingCreationResponseDtoMapper;
import org.example.mapper.training.TrainingEntityToTrainingRetrievalResponseDtoMapper;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.example.service.core.TrainingTypeService;
import org.example.service.core.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingFacadeImpl implements TrainingFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingFacadeImpl.class);

    private final TrainingService trainingService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingTypeService trainingTypeService;
    private final UserService userService;
    private final TrainingCreationRequestDtoToTrainingEntityMapper trainingCreationRequestDtoToTrainingEntityMapper;
    private final TrainingEntityToTrainingCreationResponseDtoMapper trainingEntityToTrainingCreationResponseDtoMapper;
    private final TrainingEntityToTrainingRetrievalResponseDtoMapper trainingEntityToTrainingRetrievalResponseDtoMapper;

    /**
     * Constructor.
     */
    public TrainingFacadeImpl(TrainingService trainingService,
                              TraineeService traineeService,
                              TrainerService trainerService, TrainingTypeService trainingTypeService,
                              UserService userService,
                              TrainingCreationRequestDtoToTrainingEntityMapper
                                  trainingCreationRequestDtoToTrainingEntityMapper,
                              TrainingEntityToTrainingCreationResponseDtoMapper
                                  trainingEntityToTrainingCreationResponseDtoMapper,
                              TrainingEntityToTrainingRetrievalResponseDtoMapper
                                  trainingEntityToTrainingRetrievalResponseDtoMapper) {
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingTypeService = trainingTypeService;
        this.userService = userService;
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
    public TrainingListRetrievalResponseDto retrieveTrainingListByTrainer(
        TrainingListRetrievalByTrainerRequestDto requestDto) {
        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retrieving the list of all trainings according to the request dto - {}", requestDto);

        String retrieverUsername = requestDto.getRetrieverUsername();
        String retrieverPassword = requestDto.getRetrieverPassword();

        if (!userService.usernamePasswordMatching(retrieverUsername, retrieverPassword)) {
            return new TrainingListRetrievalResponseDto(List.of(String.format(
                "The retriever's username(%s) does not match the retriever's password(%s)", retrieverUsername,
                retrieverPassword
            )));
        }

        String trainerUsername = requestDto.getTrainerUsername();
        if (userService.findByUsername(trainerUsername).isEmpty()) {
            return new TrainingListRetrievalResponseDto(List.of(String.format(
                "A trainer with a username of %s does not exist", trainerUsername
            )));
        }

        List<TrainingRetrievalResponseDto> all = trainingService.findAllByTrainerUsernameAndCriteria(
            trainerUsername,
            requestDto.getFrom(),
            requestDto.getTo(),
            requestDto.getTraineeUsername()
        ).stream().map(trainingEntityToTrainingRetrievalResponseDtoMapper::map).toList();

        TrainingListRetrievalResponseDto responseDto = new TrainingListRetrievalResponseDto(trainerUsername, all);

        LOGGER.info("Successfully retrieved all trainings according to the request dto - {}, result - {}", requestDto,
            responseDto);

        return responseDto;
    }


    @Override
    public TrainingListRetrievalResponseDto retrieveTrainingListByTrainee(
        TrainingListRetrievalByTraineeRequestDto requestDto) {
        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retrieving the list of trainings according to the request dto - {}", requestDto);

        String retrieverUsername = requestDto.getRetrieverUsername();
        String retrieverPassword = requestDto.getRetrieverPassword();

        if (!userService.usernamePasswordMatching(retrieverUsername, retrieverPassword)) {
            return new TrainingListRetrievalResponseDto(List.of(String.format(
                "The retriever's username(%s) does not match the retriever's password(%s)", retrieverUsername,
                retrieverPassword
            )));
        }

        if (userService.findByUsername(requestDto.getTraineeUsername()).isEmpty()) {
            return new TrainingListRetrievalResponseDto(List.of(String.format(
                "A trainee with a username %s does not exist"
            )));
        }

        List<TrainingRetrievalResponseDto> all = trainingService.findAllByTraineeUsernameAndCriteria(
            requestDto.getTraineeUsername(),
            requestDto.getFrom(),
            requestDto.getTo(),
            requestDto.getTrainerUsername(),
            requestDto.getTrainingTypeId()
        ).stream().map(trainingEntityToTrainingRetrievalResponseDtoMapper::map).toList();

        TrainingListRetrievalResponseDto responseDto =
            new TrainingListRetrievalResponseDto(requestDto.getTraineeUsername(), all);

        LOGGER.info("Successfully retrieved all trainings according to the request dto - {}, result - {}",
            requestDto, responseDto);

        return responseDto;

    }

    @Override
    public MultipleTrainingDeletionByTraineeResponseDto deleteMultpileTraineeTraining(String traineeUsername) {
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        LOGGER.info("Deleting all trainings of a trainee with a username of {}", traineeUsername);

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            return new MultipleTrainingDeletionByTraineeResponseDto(
                List.of(String.format("A trainee with a username of %s does not exist", traineeUsername))
            );
        }

        trainingService.deleteAllByTraineeUsername(traineeUsername);

        MultipleTrainingDeletionByTraineeResponseDto responseDto =
            new MultipleTrainingDeletionByTraineeResponseDto(traineeUsername);

        LOGGER.info("Successfully deleted all trainings of a trainee with a username of {}, result - {}",
            traineeUsername, responseDto);

        return responseDto;
    }

    @Override
    public MultipleTrainingDeletionByTrainerResponseDto deleteMultipleTrainerTraining(String trainerUsername) {
        Assert.notNull(trainerUsername, "Trainer username must not be null");
        LOGGER.info("Deleting all trainings of a trainer with a username of {}", trainerUsername);

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            return new MultipleTrainingDeletionByTrainerResponseDto(
                List.of(String.format("A trainer with a username of %s does not exist", trainerUsername)));
        }

        trainingService.deleteAllByTrainerUsername(trainerUsername);

        MultipleTrainingDeletionByTrainerResponseDto responseDto =
            new MultipleTrainingDeletionByTrainerResponseDto(trainerUsername);

        LOGGER.info("Successfully deleted all trainings of a trainer with a username of {}, result - {}",
            trainerUsername, responseDto);

        return responseDto;
    }

    @Override
    public TrainingUpdateResponseDto updateTraining(TrainingUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingUpdateRequestDto must not be null");
        LOGGER.info("Updating a training according to the TrainingUpdateRequestDto - {}", requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new TrainingUpdateResponseDto(List.of("Authentication failed"));
        }

        TrainingEntity updatedTrainingEntity = trainingService.update(new TrainingEntity(
            traineeService.findById(requestDto.getTraineeId())
                .orElseThrow(() -> new TraineeNotFoundException(requestDto.getTraineeId())),
            trainerService.findById(requestDto.getTrainerId())
                .orElseThrow(() -> new TrainerNotFoundException(requestDto.getTrainerId())),
            requestDto.getName(),
            trainingTypeService.get(requestDto.getTrainingTypeId()),
            requestDto.getDate(),
            requestDto.getDuration()
        ));
        TrainingUpdateResponseDto responseDto = new TrainingUpdateResponseDto(
            updatedTrainingEntity.getId(),
            updatedTrainingEntity.getTrainee().getId(),
            updatedTrainingEntity.getTrainer().getId(),
            updatedTrainingEntity.getName(),
            updatedTrainingEntity.getTrainingType().getId(),
            updatedTrainingEntity.getDate(),
            updatedTrainingEntity.getDuration()
        );

        LOGGER.info("Successfully updated a training according to the TrainingUpdateRequestDto - {}, result - {}",
            requestDto, responseDto);
        return null;
    }
}
