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
import org.example.dto.request.TrainerPasswordChangeRequestDto;
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
import org.example.exception.TrainerNotFoundException;
import org.example.facade.core.TrainerFacade;
import org.example.mapper.trainer.TrainerMapper;
import org.example.service.core.IdService;
import org.example.service.core.TrainerService;
import org.example.service.core.UserService;
import org.example.service.core.UsernamePasswordService;
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

    /**
     * Constructor.
     */
    public TrainerFacadeImpl(TrainerService trainerService,
                             UserService userService,
                             @Qualifier("trainerUsernamePasswordService")
                             UsernamePasswordService usernamePasswordService,
                             @Qualifier("trainerIdService")
                             IdService idService, TrainerMapper trainerMapper) {
        this.trainerService = trainerService;
        this.userService = userService;
        this.usernamePasswordService = usernamePasswordService;
        this.idService = idService;
        this.trainerMapper = trainerMapper;
    }

    @Override
    public RestResponse<TrainerCreationResponseDto> createTrainer(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        LOGGER.info("Creating a TrainerEntity according to the TrainerCreationRequestDto - {}", requestDto);

        String username = usernamePasswordService.username(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            idService.getId(),
            "trainer"
        );

        String password = usernamePasswordService.password();

        requestDto.setUsername(username);
        requestDto.setPassword(password);

        //TODO: ADD A USER MAPPER
        UserEntity userEntity = userService.create(new UserEntity(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getUsername(),
            requestDto.getPassword(),
            true
        ));
        requestDto.setUserId(userEntity.getId());

        TrainerCreationResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerCreationResponseDto(
            trainerService.create(trainerMapper.mapTrainerCreationRequestDtoToTrainerEntity(requestDto)));
        idService.autoIncrement();

        RestResponse<TrainerCreationResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info(
            "Successfully created a TrainerEntity according to the TrainerCreationRequestDto - {}, response - {}",
            requestDto, restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TrainerUpdateResponseDto> updateTrainer(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");
        LOGGER.info("Updating a TrainerEntity according to the TrainerUpdateRequestDto - {}", requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed."));
        }

        TrainerEntity trainerEntity = trainerService.findByUsername(requestDto.getUsername())
            .orElseThrow(() -> new TrainerNotFoundException(requestDto.getUsername()));
        UserEntity userEntity = trainerEntity.getUser();

        userEntity.setFirstName(requestDto.getFirstName());
        userEntity.setLastName(requestDto.getLastName());
        userEntity.setIsActive(requestDto.getIsActive());

        trainerEntity.setSpecialization(new TrainingTypeEntity(requestDto.getSpecialization().getTrainingType()));

        userService.update(userEntity);
        trainerService.update(trainerEntity);

        TrainerUpdateResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerUpdateResponseDto(trainerEntity);

        RestResponse<TrainerUpdateResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        LOGGER.info("Successfully updated a TrainerEntity according to the TrainerUpdateRequestDto - {}, response - {}",
            requestDto, restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<TrainerSwitchActivationStateResponseDto> switchActivationState(
        TrainerSwitchActivationStateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerSwitchActivationStateRequestDto must not be null");
        LOGGER.info("Switching activation state according to the {}", requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        TrainerEntity trainerEntity = trainerService.selectByUsername(requestDto.getUsername());
        UserEntity userEntity = trainerEntity.getUser();
        userEntity.setIsActive(!userEntity.getIsActive());
        userService.update(userEntity);
        trainerService.update(trainerEntity);

        TrainerSwitchActivationStateResponseDto responseDto =
            new TrainerSwitchActivationStateResponseDto(HttpStatus.OK);

        RestResponse<TrainerSwitchActivationStateResponseDto> restResponse =
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

        if (!userService.usernamePasswordMatching(
            requestDto.getRetrieverUsername(), requestDto.getRetrieverPassword())
        ) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        String username = requestDto.getUsername();
        TrainerEntity trainerEntity =
            trainerService.findByUsername(username).orElseThrow(() -> new TrainerNotFoundException(username));

        TrainerRetrievalResponseDto responseDto =
            trainerMapper.mapTrainerEntityToTrainerRetrievalResponseDto(trainerEntity);

        RestResponse<TrainerRetrievalResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
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

        if (!userService.usernamePasswordMatching(requestDto.getRetrieverUsername(),
            requestDto.getRetrieverPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        List<TrainerDto> all = trainerService.findAllNotAssignedTo(traineeUsername).stream()
            .map(trainerEntity -> new TrainerDto(
                new UserDto(
                    trainerEntity.getUser().getFirstName(),
                    trainerEntity.getUser().getLastName(),
                    trainerEntity.getUser().getUsername(),
                    trainerEntity.getUser().getPassword(),
                    trainerEntity.getUser().getIsActive()
                ),
                new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType())
            ))
            .toList();

        TrainerListRetrievalResponseDto responseDto =
            new TrainerListRetrievalResponseDto(all, traineeUsername);

        RestResponse<TrainerListRetrievalResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        LOGGER.info("Successfully retrieved all trainers not assigned to trainee with a username of {}, result - {}",
            traineeUsername, restResponse);

        return restResponse;
    }

    @Override
    public RestResponse<TraineeTrainerListUpdateResponseDto> updateTraineeTrainerList(TraineeTrainerListUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeTrainerListUpdateRequestDto must not be null");
        LOGGER.info(
            "Updating the list of trainers of a trainee according to the TraineeTrainerListUpdateRequestDto - {}",
            requestDto);

        // TODO: ADD A MAPPER
        List<TrainerEntity> trainerEntities =
            trainerService.updateTrainersAssignedTo(requestDto.getTraineeUsername(),
                requestDto.getTrainerDtoList().stream()
                    .map(trainerDto -> new TrainerEntity(
                        new UserEntity(
                            trainerDto.getUserDto().getFirstName(),
                            trainerDto.getUserDto().getLastName(),
                            trainerDto.getUserDto().getUsername(),
                            trainerDto.getUserDto().getPassword(),
                            trainerDto.getUserDto().getIsActive()
                        ),
                        new TrainingTypeEntity(trainerDto.getTrainingTypeDto().getTrainingType())
                    )).collect(Collectors.toSet())).stream().toList();

        TraineeTrainerListUpdateResponseDto responseDto =
            new TraineeTrainerListUpdateResponseDto(requestDto.getTrainerDtoList());

        RestResponse<TraineeTrainerListUpdateResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        LOGGER.info("Successfully updated the list of trainers of a trainee according to "
                + "the TraineeTrainerListUpdateRequestDto - {}, response - {}",
            requestDto, restResponse);

        return restResponse;
    }
}
