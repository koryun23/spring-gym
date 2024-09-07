package org.example.facade.impl;

import java.util.List;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    @Qualifier("trainingIdService")
    private IdService idService;

    @Autowired
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

        requestDto.setTrainingId(idService.getId());

        TrainingCreationResponseDto responseDto = trainingEntityToTrainingCreationResponseDtoMapper.map(
            trainingService.create(trainingCreationRequestDtoToTrainingEntityMapper.map(requestDto)));
        idService.autoIncrement();

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
}
