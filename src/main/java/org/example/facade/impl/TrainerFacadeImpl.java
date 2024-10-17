package org.example.facade.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TraineeTrainerListUpdateRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TraineeTrainerListUpdateResponseDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerListRetrievalResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerSwitchActivationStateResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.facade.core.TrainerFacade;
import org.example.mapper.trainer.TrainerMapper;
import org.example.service.core.IdService;
import org.example.service.core.TrainerService;
import org.example.service.core.UserService;
import org.example.service.core.UsernamePasswordService;
import org.example.validator.TrainerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerFacadeImpl implements TrainerFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerFacadeImpl.class);

    private final TrainerService trainerService;
    private final UserService userService;
    private final UsernamePasswordService usernamePasswordService;
    private final IdService idService;
    private final TrainerMapper trainerMapper;
    private final TrainerValidator trainerValidator;

    /**
     * Constructor.
     */
    public TrainerFacadeImpl(TrainerService trainerService,
                             UserService userService,
                             @Qualifier("trainerUsernamePasswordService")
                             UsernamePasswordService usernamePasswordService,
                             @Qualifier("trainerIdService")
                             IdService idService,
                             TrainerMapper trainerMapper,
                             TrainerValidator trainerValidator) {
        this.trainerService = trainerService;
        this.userService = userService;
        this.usernamePasswordService = usernamePasswordService;
        this.idService = idService;
        this.trainerMapper = trainerMapper;
        this.trainerValidator = trainerValidator;
    }

    @Override
    public RestResponse<TrainerCreationResponseDto> createTrainer(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        LOGGER.info("Creating a TrainerEntity according to the TrainerCreationRequestDto - {}", requestDto);

        // validations
        RestResponse<TrainerCreationResponseDto> restResponse = trainerValidator.validateCreateTrainer(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        userService.create(trainerMapper.mapTrainerCreationRequestDtoToUserEntity(requestDto));
        TrainerCreationResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerCreationResponseDto(
            trainerService.create(trainerMapper.mapTrainerCreationRequestDtoToTrainerEntity(requestDto)));
        idService.autoIncrement();

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info(
            "Successfully created a TrainerEntity according to the TrainerCreationRequestDto - {}, response - {}",
            requestDto, restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TrainerUpdateResponseDto> updateTrainer(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");
        LOGGER.info("Updating a TrainerEntity according to the TrainerUpdateRequestDto - {}", requestDto);

        // validations
        RestResponse<TrainerUpdateResponseDto> restResponse = trainerValidator.validateUpdateTrainer(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        userService.update(trainerMapper.mapTrainerUpdateRequestDtoToUserEntity(requestDto));
        TrainerUpdateResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerUpdateResponseDto(
            trainerService.update(trainerMapper.mapTrainerUpdateRequestDtoToTrainerEntity(requestDto)));

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully updated a TrainerEntity according to the TrainerUpdateRequestDto - {}, response - {}",
            requestDto, restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TrainerSwitchActivationStateResponseDto> switchActivationState(
        TrainerSwitchActivationStateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerSwitchActivationStateRequestDto must not be null");
        LOGGER.info("Switching activation state according to the {}", requestDto);

        // validations
        RestResponse<TrainerSwitchActivationStateResponseDto> restResponse =
            trainerValidator.validateSwitchActivationState(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        userService.update(trainerMapper.mapSwitchActivationStateRequestDtoToUserEntity(requestDto));

        // response
        TrainerSwitchActivationStateResponseDto responseDto =
            new TrainerSwitchActivationStateResponseDto(HttpStatus.OK);

        restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully switched the activation state according to the {}, result - {}",
            requestDto, restResponse);
        return restResponse;

    }

    @Override
    public RestResponse<TrainerRetrievalResponseDto> retrieveTrainerByUsername(
        TrainerRetrievalByUsernameRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerRetrievalByUsernameRequestDto must not be null");
        LOGGER.info("Retrieving a trainer according to the TrainerRetrievalByUsernameRequestDto - {}", requestDto);

        // validations
        RestResponse<TrainerRetrievalResponseDto> restResponse = trainerValidator.validateRetrieveTrainer(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        TrainerRetrievalResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerRetrievalResponseDto(
                trainerService.findByUsername(requestDto.getUsername()).get());

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        LOGGER.info(
            "Successfully retrieved a trainer according to the TrainerRetrievalByUsernameRequestDto - {}, result - {}",
            requestDto, restResponse);

        return restResponse;
    }

    @Override
    public RestResponse<TrainerListRetrievalResponseDto> retrieveAllTrainersNotAssignedToTrainee(
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto) {

        Assert.notNull(requestDto, "RetrieveAllTrainersNotAssignedToTraineeRequestDto must not be null");
        LOGGER.info("Retrieving trainers not assigned to a trainee according to the {}", requestDto);

        String traineeUsername = requestDto.getUsername();
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        LOGGER.info("Retrieving trainers not assigned to a trainee with a username of {}", traineeUsername);

        // validations
        RestResponse<TrainerListRetrievalResponseDto> restResponse =
            trainerValidator.validateRetrieveAllTrainersNotAssignedToTrainee(requestDto);

        if (restResponse != null) return restResponse;

        // service and mapper calls
        TrainerListRetrievalResponseDto responseDto = new TrainerListRetrievalResponseDto(
            trainerService.findAllNotAssignedTo(traineeUsername).stream()
                .map(trainerMapper::mapTrainerEntityToTrainerDto).toList(),
            traineeUsername
        );

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        LOGGER.info("Successfully retrieved all trainers not assigned to trainee with a username of {}, result - {}",
            traineeUsername, restResponse);

        return restResponse;
    }

    @Override
    public RestResponse<TraineeTrainerListUpdateResponseDto> updateTraineeTrainerList(
        TraineeTrainerListUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeTrainerListUpdateRequestDto must not be null");
        LOGGER.info(
            "Updating the list of trainers of a trainee according to the TraineeTrainerListUpdateRequestDto - {}",
            requestDto);

        // validations
        RestResponse<TraineeTrainerListUpdateResponseDto> restResponse =
            trainerValidator.validateUpdateTraineeTrainerList(requestDto);

        if (restResponse != null) return restResponse;

        // service and mapper calls
        trainerService.updateTrainersAssignedTo(requestDto.getTraineeUsername(),
            requestDto.getTrainerDtoList().stream()
                .map(trainerMapper::mapTrainerDtoToTrainerEntity).collect(Collectors.toSet()));

        // response
        TraineeTrainerListUpdateResponseDto responseDto =
            new TraineeTrainerListUpdateResponseDto(requestDto.getTrainerDtoList());

        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully updated the list of trainers of a trainee according to "
                + "the TraineeTrainerListUpdateRequestDto - {}, response - {}",
            requestDto, restResponse);

        return restResponse;
    }
}
