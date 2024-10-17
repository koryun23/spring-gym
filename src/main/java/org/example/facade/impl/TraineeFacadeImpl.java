package org.example.facade.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import org.example.dto.RestResponse;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeRetrievalByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeSwitchActivationStateResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.example.facade.core.TraineeFacade;
import org.example.mapper.trainee.TraineeMapper;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.UserService;
import org.example.validator.TraineeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeFacadeImpl implements TraineeFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeFacadeImpl.class);

    private final TraineeService traineeService;
    private final UserService userService;
    private final TraineeMapper traineeMapper;
    private final IdService idService;
    private final TraineeValidator traineeValidator;

    /**
     * Constructor.
     */
    public TraineeFacadeImpl(TraineeService traineeService,
                             UserService userService,
                             TraineeMapper traineeMapper,
                             @Qualifier("traineeIdService") IdService idService, TraineeValidator traineeValidator) {
        this.userService = userService;
        this.traineeService = traineeService;
        this.traineeMapper = traineeMapper;
        this.idService = idService;
        this.traineeValidator = traineeValidator;
    }

    @Override
    public RestResponse<TraineeCreationResponseDto> createTrainee(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        LOGGER.info("Creating a TraineeEntity based on the TraineeCreationRequestDto - {}", requestDto);

        // validations
        RestResponse<TraineeCreationResponseDto> restResponse =
            traineeValidator.validateCreateTrainee(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        traineeMapper.mapTraineeCreationRequestDto(requestDto);
        userService.create(traineeMapper.mapTraineeCreationRequestDtoToUserEntity(requestDto));
        TraineeCreationResponseDto responseDto = traineeMapper.mapTraineeEntityToTraineeCreationResponseDto(
            traineeService.create(traineeMapper.mapTraineeCreationRequestDtoToTraineeEntity(requestDto)));
        idService.autoIncrement();

        // response
        restResponse = new RestResponse<>(
            responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList()
        );
        LOGGER.info("Successfully created a TraineeEntity based on the TraineeCreationRequestDto - {}, response - {}",
            requestDto, restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TraineeUpdateResponseDto> updateTrainee(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        LOGGER.info("Updating a TraineeEntity based on the TraineeUpdateRequestDto - {}", requestDto);

        // validations
        RestResponse<TraineeUpdateResponseDto> restResponse = traineeValidator.validateUpdateTrainee(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        userService.update(traineeMapper.mapTraineeUpdateRequestDtoToUserEntity(requestDto));
        TraineeEntity traineeEntity =
            traineeService.update(traineeMapper.mapTraineeUpdateRequestDtoToTraineeEntity(requestDto));

        // response
        restResponse = new RestResponse<>(
            traineeMapper.mapTraineeEntityToTraineeUpdateResponseDto(traineeEntity),
            HttpStatus.OK, LocalDateTime.now(), Collections.emptyList()
        );

        LOGGER.info("Successfully updated a TraineeEntity based on the TraineeUpdateRequestDto - {}, response - {}",
            requestDto, restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TraineeRetrievalResponseDto> retrieveTrainee(TraineeRetrievalByUsernameRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeRetrievalByUsernameRequestDto must not be null");
        String username = requestDto.getUsername();
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        LOGGER.info("Retrieving a Trainee with a username of {}", username);

        // validations
        RestResponse<TraineeRetrievalResponseDto> restResponse = traineeValidator.validateRetrieveTrainee(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls + response
        restResponse = new RestResponse<>(
            traineeMapper.mapTraineeEntityToTraineeRetrievalResponseDto(traineeService.findByUsername(username).get()),
            HttpStatus.OK, LocalDateTime.now(), Collections.emptyList()
        );
        LOGGER.info("Successfully retrieved a Trainee with a username of {}, result - {}", username, restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TraineeDeletionResponseDto> deleteTraineeByUsername(
        TraineeDeletionByUsernameRequestDto requestDto) {

        Assert.notNull(requestDto, "TraineeDeletionByUsernameRequestDto must not be null");
        LOGGER.info("Deleting a Trainee according to TraineeDeletionByUsernameRequestDto - {}", requestDto);

        // validations
        RestResponse<TraineeDeletionResponseDto> restResponse = traineeValidator.validateDeleteTrainee(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service calls
        traineeService.delete(requestDto.getUsername());

        // response
        restResponse = new RestResponse<>(
            new TraineeDeletionResponseDto(HttpStatus.OK),
            HttpStatus.OK, LocalDateTime.now(), Collections.emptyList()
        );

        LOGGER.info("Successfully deleted a Trainee with a username of {}, result - {}", requestDto.getUsername(),
            restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TraineeSwitchActivationStateResponseDto> switchActivationState(
        TraineeSwitchActivationStateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeSwitchActivationStateRequestDto must not be null");

        // validations
        RestResponse<TraineeSwitchActivationStateResponseDto> restResponse =
            traineeValidator.validateSwitchActivationState(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        userService.update(traineeMapper.mapSwitchActivationStateRequestDtoToUserEntity(requestDto));

        // response
        TraineeSwitchActivationStateResponseDto responseDto =
            new TraineeSwitchActivationStateResponseDto(HttpStatus.OK);

        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully switched the activation state of a Trainee, request - {}", requestDto);
        return restResponse;
    }
}
