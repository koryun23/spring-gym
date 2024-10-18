package org.example.facade.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.facade.core.TrainingFacade;
import org.example.mapper.training.TrainingMapper;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.example.service.core.TrainingTypeService;
import org.example.service.core.UserService;
import org.example.validator.TrainingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingFacadeImpl implements TrainingFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingFacadeImpl.class);

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;
    private final TrainingValidator trainingValidator;

    /**
     * Constructor.
     */
    public TrainingFacadeImpl(TrainingService trainingService,
                              TrainingMapper trainingMapper,
                              TrainingValidator trainingValidator) {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
        this.trainingValidator = trainingValidator;
    }

    @Override
    public RestResponse<TrainingCreationResponseDto> createTraining(TrainingCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingCreationRequestDto");
        LOGGER.info("Creating a TrainingEntity according to the TrainingCreationRequestDto - {}", requestDto);

        // validations
        RestResponse<TrainingCreationResponseDto> restResponse = trainingValidator.validateCreateTraining(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        TrainingCreationResponseDto responseDto = trainingMapper.mapTrainingEntityToTrainingCreationResponseDto(
            trainingService.create(trainingMapper.mapTrainingCreationRequestDtoToTrainingEntity(requestDto)));

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        LOGGER.info(
            "Successfully created a TrainingEntity according to the TrainingCreationRequestDto - {}, response - {}",
            requestDto, restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TrainingListRetrievalResponseDto> retrieveTrainingListByTrainer(
        TrainingListRetrievalByTrainerRequestDto requestDto) {
        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retrieving the list of all trainings according to the request dto - {}", requestDto);

        // validations
        RestResponse<TrainingListRetrievalResponseDto> restResponse =
            trainingValidator.validateRetrieveTrainingListByTrainer(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        String trainerUsername = requestDto.getTrainerUsername();
        List<TrainingRetrievalResponseDto> all = trainingMapper.mapTrainingEntityListToTrainingRetrievalResponseDtoList(
            trainingService.findAllByTrainerUsernameAndCriteria(
                trainerUsername,
                requestDto.getFrom(),
                requestDto.getTo(),
                requestDto.getTraineeUsername()
            ));

        // response
        restResponse = new RestResponse<>(new TrainingListRetrievalResponseDto(trainerUsername, all), HttpStatus.OK,
            LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully retrieved all trainings according to the request dto - {}, result - {}", requestDto,
            restResponse);

        return restResponse;
    }


    @Override
    public RestResponse<TrainingListRetrievalResponseDto> retrieveTrainingListByTrainee(
        TrainingListRetrievalByTraineeRequestDto requestDto) {
        Assert.notNull(requestDto, "Request Dto must not be null");
        LOGGER.info("Retrieving the list of trainings according to the request dto - {}", requestDto);

        // validation
        RestResponse<TrainingListRetrievalResponseDto> restResponse =
            trainingValidator.validateRetrieveTrainingListByTrainee(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        TrainingListRetrievalResponseDto responseDto =
            new TrainingListRetrievalResponseDto(requestDto.getTraineeUsername(),
                trainingMapper.mapTrainingEntityListToTrainingRetrievalResponseDtoList(
                    trainingService.findAllByTraineeUsernameAndCriteria(
                        requestDto.getTraineeUsername(),
                        requestDto.getFrom(),
                        requestDto.getTo(),
                        requestDto.getTrainerUsername(),
                        requestDto.getTrainingTypeDto() == null ? null :
                            requestDto.getTrainingTypeDto().getTrainingType()
                    )
                )
            );

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully retrieved all trainings according to the request dto - {}, result - {}",
            requestDto, restResponse);

        return restResponse;
    }
}
