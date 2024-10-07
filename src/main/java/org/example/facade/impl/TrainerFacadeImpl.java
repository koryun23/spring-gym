package org.example.facade.impl;

import java.util.List;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerPasswordChangeRequestDto;
import org.example.dto.request.TrainerRetrievalByIdRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerListRetrievalResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.exception.TrainerNotFoundException;
import org.example.facade.core.TrainerFacade;
import org.example.mapper.trainer.TrainerCreationRequestDtoToTrainerEntityMapper;
import org.example.mapper.trainer.TrainerEntityToTrainerCreationResponseDtoMapper;
import org.example.mapper.trainer.TrainerEntityToTrainerRetrievalResponseDtoMapper;
import org.example.mapper.trainer.TrainerEntityToTrainerUpdateResponseDtoMapper;
import org.example.mapper.trainer.TrainerUpdateRequestDtoToTrainerEntityMapper;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingTypeService;
import org.example.service.core.UserService;
import org.example.service.core.UsernamePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerFacadeImpl implements TrainerFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerFacadeImpl.class);

    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingTypeService trainingTypeService;
    private final UserService userService;
    private final TrainerCreationRequestDtoToTrainerEntityMapper trainerCreationRequestDtoToTrainerEntityMapper;
    private final TrainerEntityToTrainerCreationResponseDtoMapper trainerEntityToTrainerCreationResponseDtoMapper;
    private final TrainerUpdateRequestDtoToTrainerEntityMapper trainerUpdateRequestDtoToTrainerEntityMapper;
    private final TrainerEntityToTrainerUpdateResponseDtoMapper trainerEntityToTrainerUpdateResponseDtoMapper;
    private final TrainerEntityToTrainerRetrievalResponseDtoMapper trainerEntityToTrainerRetrievalResponseDtoMapper;
    private final UsernamePasswordService usernamePasswordService;
    private final IdService idService;

    /**
     * Constructor.
     */
    public TrainerFacadeImpl(TrainerService trainerService, TraineeService traineeService,
                             TrainingTypeService trainingTypeService, UserService userService,
                             TrainerCreationRequestDtoToTrainerEntityMapper
                                 trainerCreationRequestDtoToTrainerEntityMapper,
                             TrainerEntityToTrainerCreationResponseDtoMapper
                                 trainerEntityToTrainerCreationResponseDtoMapper,
                             TrainerUpdateRequestDtoToTrainerEntityMapper trainerUpdateRequestDtoToTrainerEntityMapper,
                             TrainerEntityToTrainerUpdateResponseDtoMapper
                                 trainerEntityToTrainerUpdateResponseDtoMapper,
                             TrainerEntityToTrainerRetrievalResponseDtoMapper
                                 trainerEntityToTrainerRetrievalResponseDtoMapper,
                             @Qualifier("trainerUsernamePasswordService")
                             UsernamePasswordService usernamePasswordService,
                             @Qualifier("trainerIdService")
                             IdService idService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingTypeService = trainingTypeService;
        this.userService = userService;
        this.trainerCreationRequestDtoToTrainerEntityMapper = trainerCreationRequestDtoToTrainerEntityMapper;
        this.trainerEntityToTrainerCreationResponseDtoMapper = trainerEntityToTrainerCreationResponseDtoMapper;
        this.trainerUpdateRequestDtoToTrainerEntityMapper = trainerUpdateRequestDtoToTrainerEntityMapper;
        this.trainerEntityToTrainerUpdateResponseDtoMapper = trainerEntityToTrainerUpdateResponseDtoMapper;
        this.trainerEntityToTrainerRetrievalResponseDtoMapper = trainerEntityToTrainerRetrievalResponseDtoMapper;
        this.usernamePasswordService = usernamePasswordService;
        this.idService = idService;
    }

    @Override
    public TrainerCreationResponseDto createTrainer(TrainerCreationRequestDto requestDto) {
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

        UserEntity userEntity = userService.create(new UserEntity(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getUsername(),
            requestDto.getPassword(),
            requestDto.getIsActive()
        ));
        requestDto.setUserId(userEntity.getId());

        TrainerCreationResponseDto responseDto = trainerEntityToTrainerCreationResponseDtoMapper.map(
            trainerService.create(trainerCreationRequestDtoToTrainerEntityMapper.map(requestDto)));
        idService.autoIncrement();

        LOGGER.info(
            "Successfully created a TrainerEntity according to the TrainerCreationRequestDto - {}, response - {}",
            requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainerUpdateResponseDto updateTrainer(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");
        LOGGER.info("Updating a TrainerEntity according to the TrainerUpdateRequestDto - {}", requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new TrainerUpdateResponseDto(List.of("Authentication failed."));
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

        TrainerUpdateResponseDto responseDto = new TrainerUpdateResponseDto(
            userEntity.getUsername(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType()),
            userEntity.getIsActive(),
            trainerEntity.getTraineeEntityList().stream()
                .map(traineeEntity -> new TraineeDto(
                    new UserDto(
                        traineeEntity.getUser().getFirstName(),
                        traineeEntity.getUser().getLastName(),
                        traineeEntity.getUser().getUsername(),
                        traineeEntity.getUser().getPassword(),
                        traineeEntity.getUser().getIsActive()
                    ),
                    traineeEntity.getDateOfBirth(),
                    traineeEntity.getAddress()
                )).toList()
        );

        LOGGER.info("Successfully updated a TrainerEntity according to the TrainerUpdateRequestDto - {}, response - {}",
            requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainerUpdateResponseDto switchActivationState(TrainerSwitchActivationStateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerSwitchActivationStateRequestDto must not be null");
        LOGGER.info("Switching activation state according to the {}", requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new TrainerUpdateResponseDto(List.of("Authentication failed"));
        }

        TrainerEntity trainerEntity = trainerService.selectByUsername(requestDto.getUsername());
        UserEntity userEntity = trainerEntity.getUser();
        userEntity.setIsActive(!userEntity.getIsActive());
        userService.update(userEntity);
        trainerService.update(trainerEntity);

        TrainerUpdateResponseDto responseDto = new TrainerUpdateResponseDto(
            userEntity.getUsername(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType()),
            userEntity.getIsActive(),
            trainerEntity.getTraineeEntityList().stream()
                .map(traineeEntity -> new TraineeDto(
                    new UserDto(
                        traineeEntity.getUser().getFirstName(),
                        traineeEntity.getUser().getLastName(),
                        traineeEntity.getUser().getUsername(),
                        traineeEntity.getUser().getPassword(),
                        traineeEntity.getUser().getIsActive()
                    ),
                    traineeEntity.getDateOfBirth(),
                    traineeEntity.getAddress()
                )).toList()
        );

        LOGGER.info("Successfully switched the activation state according to the {}, result - {}",
            requestDto, responseDto);
        return responseDto;

    }

    @Override
    public TrainerUpdateResponseDto changePassword(TrainerPasswordChangeRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerPasswordChangeRequestDto must not be null");
        LOGGER.info("Changing password of a trainer according to the TrainerPasswordChangeRequestDto - {}", requestDto);

        TrainerEntity trainerEntity = trainerService.findById(requestDto.getTrainerId())
            .orElseThrow(() -> new TrainerNotFoundException(requestDto.getTrainerId()));

        UserEntity userEntity = trainerEntity.getUser();
        userEntity.setPassword(requestDto.getNewPassword());
        userService.update(userEntity);

        TrainerUpdateResponseDto responseDto = new TrainerUpdateResponseDto(
            userEntity.getUsername(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType()),
            userEntity.getIsActive(),
            trainerEntity.getTraineeEntityList().stream()
                .map(traineeEntity -> new TraineeDto(
                    new UserDto(
                        traineeEntity.getUser().getFirstName(),
                        traineeEntity.getUser().getLastName(),
                        traineeEntity.getUser().getUsername(),
                        traineeEntity.getUser().getPassword(),
                        traineeEntity.getUser().getIsActive()
                    ),
                    traineeEntity.getDateOfBirth(),
                    traineeEntity.getAddress()
                )).toList()
        );

        LOGGER.info(
            "Successfully changed the password of a trainer according to the TrainerPasswordChangeRequestDto - {}, "
                + "result - {}",
            requestDto, responseDto);

        return responseDto;
    }

    @Override
    public TrainerRetrievalResponseDto retrieveTrainerByUsername(TrainerRetrievalByUsernameRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerRetrievalByUsernameRequestDto must not be null");
        LOGGER.info("Retrieving a trainer according to the TrainerRetrievalByUsernameRequestDto - {}", requestDto);

        if (!userService.usernamePasswordMatching(
            requestDto.getRetrieverUsername(), requestDto.getRetrieverPassword())
        ) {
            return new TrainerRetrievalResponseDto(List.of("Authentication failed"));
        }

        String username = requestDto.getUsername();
        TrainerEntity trainerEntity =
            trainerService.findByUsername(username).orElseThrow(() -> new TrainerNotFoundException(username));

        TrainerRetrievalResponseDto responseDto = new TrainerRetrievalResponseDto(
            trainerEntity.getUser().getUsername(),
            trainerEntity.getUser().getFirstName(),
            trainerEntity.getUser().getLastName(),
            new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType()),
            trainerEntity.getUser().getIsActive(),
            trainerEntity.getTraineeEntityList().stream()
                .map(traineeEntity -> new TraineeDto(
                    new UserDto(
                        traineeEntity.getUser().getFirstName(),
                        traineeEntity.getUser().getLastName(),
                        traineeEntity.getUser().getUsername(),
                        traineeEntity.getUser().getPassword(),
                        traineeEntity.getUser().getIsActive()
                    ),
                    traineeEntity.getDateOfBirth(),
                    traineeEntity.getAddress()
                )).toList()
        );

        LOGGER.info(
            "Successfully retrieved a trainer according to the TrainerRetrievalByUsernameRequestDto - {}, result - {}",
            requestDto, responseDto);

        return responseDto;
    }

    @Override
    public TrainerListRetrievalResponseDto retrieveAllTrainersNotAssignedToTrainee(
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto) {

        Assert.notNull(requestDto, "RetrieveAllTrainersNotAssignedToTraineeRequestDto must not be null");
        LOGGER.info("Retrieving trainers not assigned to a trainee according to the {}", requestDto);

        String traineeUsername = requestDto.getUsername();
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        LOGGER.info("Retrieving trainers not assigned to a trainee with a username of {}", traineeUsername);

        if (!userService.usernamePasswordMatching(requestDto.getRetrieverUsername(),
            requestDto.getRetrieverPassword())) {
            return new TrainerListRetrievalResponseDto(List.of("Authentication failed"));
        }

        List<TrainerRetrievalResponseDto> all = trainerService.findAllNotAssignedTo(traineeUsername).stream()
            .map(trainerEntityToTrainerRetrievalResponseDtoMapper::map)
            .toList();

        TrainerListRetrievalResponseDto responseDto =
            new TrainerListRetrievalResponseDto(all, traineeUsername);

        LOGGER.info("Successfully retrieved all trainers not assigned to trianee with a username of {}, result - {}",
            traineeUsername, responseDto);

        return responseDto;
    }
}
