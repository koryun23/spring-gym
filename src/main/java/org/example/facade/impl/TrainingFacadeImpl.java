package org.example.facade.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.dto.request.MultipleTrainingDeletionByTraineeRequestDto;
import org.example.dto.request.MultipleTrainingDeletionByTrainerRequestDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.request.TrainingRetrievalByIdRequestDto;
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
import org.example.mapper.training.TrainingMapper;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.example.service.core.TrainingTypeService;
import org.example.service.core.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    private final TrainingMapper trainingMapper;

    /**
     * Constructor.
     */
    public TrainingFacadeImpl(TrainingService trainingService,
                              TraineeService traineeService,
                              TrainerService trainerService, TrainingTypeService trainingTypeService,
                              UserService userService,
                              TrainingMapper trainingMapper) {
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingTypeService = trainingTypeService;
        this.userService = userService;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public RestResponse<TrainingCreationResponseDto> createTraining(TrainingCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingCreationRequestDto");
        LOGGER.info("Creating a TrainingEntity according to the TrainingCreationRequestDto - {}", requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getCreatorUsername(), requestDto.getCreatorPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        TrainingCreationResponseDto responseDto = trainingMapper.mapTrainingEntityToTrainingCreationResponseDto(
            trainingService.create(trainingMapper.mapTrainingCreationRequestDtoToTrainingEntity(requestDto)));

        RestResponse<TrainingCreationResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        LOGGER.info(
            "Successfully created a TrainingEntity according to the TrainingCreationRequestDto - {}, response - {}",
            requestDto, restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TrainingRetrievalResponseDto> retrieveTraining(TrainingRetrievalByIdRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingRetrievalByIdRequestDto must not be null");
        Long trainingId = requestDto.getId();

        Assert.notNull(trainingId, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving a TrainingEntity with an id of {}", trainingId);

        if (!userService.usernamePasswordMatching(requestDto.getRetrieverUsername(),
            requestDto.getRetrieverPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        if (trainingId <= 0) {
            return new RestResponse<>(
                null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("TrainingEntity id must be positive: %d specified", trainingId)));
        }

        if (trainingService.findById(trainingId).isEmpty()) {
            return new RestResponse<>(
                null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("TrainingEntity with a specified id of %d does not exist", trainingId)));
        }

        TrainingRetrievalResponseDto responseDto =
            trainingMapper.mapTrainingEntityToTrainingRetrievalResponseDto(trainingService.select(trainingId));

        RestResponse<TrainingRetrievalResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        LOGGER.info("Successfully retrieved a TrainingEntity with an id of {}, response - {}", trainingId,
            restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TrainingListRetrievalResponseDto> retrieveTrainingListByTrainer(
        TrainingListRetrievalByTrainerRequestDto requestDto) {
        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retrieving the list of all trainings according to the request dto - {}", requestDto);

        String retrieverUsername = requestDto.getRetrieverUsername();
        String retrieverPassword = requestDto.getRetrieverPassword();

        if (!userService.usernamePasswordMatching(retrieverUsername, retrieverPassword)) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        String trainerUsername = requestDto.getTrainerUsername();
        if (userService.findByUsername(trainerUsername).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_FOUND, LocalDateTime.now(), List.of(String.format(
                "A trainer with a username of %s does not exist", trainerUsername
            )));
        }

        List<TrainingRetrievalResponseDto> all = trainingService.findAllByTrainerUsernameAndCriteria(
            trainerUsername,
            requestDto.getFrom(),
            requestDto.getTo(),
            requestDto.getTraineeUsername()
        ).stream().map(trainingMapper::mapTrainingEntityToTrainingRetrievalResponseDto).toList();

        TrainingListRetrievalResponseDto responseDto = new TrainingListRetrievalResponseDto(trainerUsername, all);

        RestResponse<TrainingListRetrievalResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully retrieved all trainings according to the request dto - {}, result - {}", requestDto,
            restResponse);

        return restResponse;
    }


    @Override
    public RestResponse<TrainingListRetrievalResponseDto> retrieveTrainingListByTrainee(
        TrainingListRetrievalByTraineeRequestDto requestDto) {
        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retrieving the list of trainings according to the request dto - {}", requestDto);

        String retrieverUsername = requestDto.getRetrieverUsername();
        String retrieverPassword = requestDto.getRetrieverPassword();

        if (!userService.usernamePasswordMatching(retrieverUsername, retrieverPassword)) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        if (userService.findByUsername(requestDto.getTraineeUsername()).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_FOUND, LocalDateTime.now(), List.of(String.format(
                "A trainee with a username %s does not exist"
            )));
        }

        List<TrainingRetrievalResponseDto> all = trainingService.findAllByTraineeUsernameAndCriteria(
            requestDto.getTraineeUsername(),
            requestDto.getFrom(),
            requestDto.getTo(),
            requestDto.getTrainerUsername(),
            requestDto.getTrainingTypeDto().getTrainingType()
        ).stream().map(trainingMapper::mapTrainingEntityToTrainingRetrievalResponseDto).toList();

        TrainingListRetrievalResponseDto responseDto =
            new TrainingListRetrievalResponseDto(requestDto.getTraineeUsername(), all);

        RestResponse<TrainingListRetrievalResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully retrieved all trainings according to the request dto - {}, result - {}",
            requestDto, restResponse);

        return restResponse;

    }

    @Override
    public RestResponse<MultipleTrainingDeletionByTraineeResponseDto> deleteMultpileTraineeTraining(
        MultipleTrainingDeletionByTraineeRequestDto requestDto) {

        Assert.notNull(requestDto, "MultipleTrainingDeletionByTraineeRequestDto must not be null");
        String traineeUsername = requestDto.getTraineeUsername();
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        LOGGER.info("Deleting all trainings of a trainee with a username of {}", traineeUsername);

        if (!userService.usernamePasswordMatching(requestDto.getDeleterUsername(), requestDto.getDeleterPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        if (traineeService.findByUsername(traineeUsername).isEmpty()) {
            return new RestResponse<>(
                null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("A trainee with a username of %s does not exist", traineeUsername))
            );
        }

        trainingService.deleteAllByTraineeUsername(traineeUsername);

        MultipleTrainingDeletionByTraineeResponseDto responseDto =
            new MultipleTrainingDeletionByTraineeResponseDto(traineeUsername);

        RestResponse<MultipleTrainingDeletionByTraineeResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully deleted all trainings of a trainee with a username of {}, result - {}",
            traineeUsername, restResponse);

        return restResponse;
    }

    @Override
    public RestResponse<MultipleTrainingDeletionByTrainerResponseDto> deleteMultipleTrainerTraining(
        MultipleTrainingDeletionByTrainerRequestDto requestDto) {

        Assert.notNull(requestDto, "MultipleTrainingDeletionByTrainerRequestDto must not be null");
        String trainerUsername = requestDto.getTrainerUsername();
        Assert.notNull(trainerUsername, "Trainer username must not be null");
        LOGGER.info("Deleting all trainings of a trainer with a username of {}", trainerUsername);

        if (!userService.usernamePasswordMatching(requestDto.getDeleterUsername(), requestDto.getDeleterPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        if (trainerService.findByUsername(trainerUsername).isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_FOUND, LocalDateTime.now(),
                List.of(String.format("A trainer with a username of %s does not exist", trainerUsername)));
        }

        trainingService.deleteAllByTrainerUsername(trainerUsername);

        MultipleTrainingDeletionByTrainerResponseDto responseDto =
            new MultipleTrainingDeletionByTrainerResponseDto(trainerUsername);

        RestResponse<MultipleTrainingDeletionByTrainerResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully deleted all trainings of a trainer with a username of {}, result - {}",
            trainerUsername, restResponse);

        return restResponse;
    }

    @Override
    public RestResponse<TrainingUpdateResponseDto> updateTraining(TrainingUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingUpdateRequestDto must not be null");
        LOGGER.info("Updating a training according to the TrainingUpdateRequestDto - {}", requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
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

        RestResponse<TrainingUpdateResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        LOGGER.info("Successfully updated a training according to the TrainingUpdateRequestDto - {}, result - {}",
            requestDto, restResponse);

        return restResponse;
    }
}
