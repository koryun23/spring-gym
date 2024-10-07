package org.example.facade.impl;

import java.util.List;
import java.util.Optional;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineePasswordChangeRequestDto;
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
import org.example.exception.TraineeNotFoundException;
import org.example.facade.core.TraineeFacade;
import org.example.mapper.trainee.TraineeCreationRequestDtoToTraineeEntityMapper;
import org.example.mapper.trainee.TraineeEntityToTraineeCreationResponseDtoMapper;
import org.example.mapper.trainee.TraineeEntityToTraineeRetrievalResponseDtoMapper;
import org.example.mapper.trainee.TraineeEntityToTraineeUpdateResponseDtoMapperImpl;
import org.example.mapper.trainee.TraineeUpdateRequestDtoToTraineeEntityMapper;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.example.service.core.UserService;
import org.example.service.core.UsernamePasswordService;
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
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final UserService userService;
    private final TraineeCreationRequestDtoToTraineeEntityMapper traineeCreationRequestDtoToTraineeEntityMapper;
    private final TraineeEntityToTraineeCreationResponseDtoMapper traineeToTraineeCreationResponseDtoMapper;
    private final TraineeUpdateRequestDtoToTraineeEntityMapper traineeUpdateRequestDtoToTraineeEntityMapper;
    private final TraineeEntityToTraineeUpdateResponseDtoMapperImpl traineeEntityToTraineeUpdateResponseDtoMapper;
    private final TraineeEntityToTraineeRetrievalResponseDtoMapper traineeEntityToTraineeRetrievalResponseDtoMapper;
    private final UsernamePasswordService usernamePasswordService;
    private final IdService idService;

    /**
     * Constructor.
     */
    public TraineeFacadeImpl(TraineeService traineeService, TrainerService trainerService,
                             TrainingService trainingService, UserService userService,
                             TraineeCreationRequestDtoToTraineeEntityMapper
                                 traineeCreationRequestDtoToTraineeEntityMapper,
                             TraineeEntityToTraineeCreationResponseDtoMapper traineeToTraineeCreationResponseDtoMapper,
                             TraineeUpdateRequestDtoToTraineeEntityMapper traineeUpdateRequestDtoToTraineeEntityMapper,
                             TraineeEntityToTraineeUpdateResponseDtoMapperImpl
                                 traineeEntityToTraineeUpdateResponseDtoMapper,
                             TraineeEntityToTraineeRetrievalResponseDtoMapper
                                 traineeEntityToTraineeRetrievalResponseDtoMapper,
                             @Qualifier("traineeUsernamePasswordService")
                             UsernamePasswordService usernamePasswordService,
                             @Qualifier("traineeIdService") IdService idService) {
        this.trainingService = trainingService;
        this.userService = userService;
        this.traineeToTraineeCreationResponseDtoMapper = traineeToTraineeCreationResponseDtoMapper;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.traineeCreationRequestDtoToTraineeEntityMapper = traineeCreationRequestDtoToTraineeEntityMapper;
        this.traineeUpdateRequestDtoToTraineeEntityMapper = traineeUpdateRequestDtoToTraineeEntityMapper;
        this.traineeEntityToTraineeUpdateResponseDtoMapper = traineeEntityToTraineeUpdateResponseDtoMapper;
        this.traineeEntityToTraineeRetrievalResponseDtoMapper = traineeEntityToTraineeRetrievalResponseDtoMapper;
        this.usernamePasswordService = usernamePasswordService;
        this.idService = idService;
    }

    @Override
    public TraineeCreationResponseDto createTrainee(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        LOGGER.info("Creating a TraineeEntity based on the TraineeCreationRequestDto - {}", requestDto);

        requestDto.setUsername(
            usernamePasswordService.username(requestDto.getFirstName(), requestDto.getLastName(), idService.getId(),
                "trainee"));
        requestDto.setPassword(usernamePasswordService.password());

        UserEntity userEntity = userService.create(
            new UserEntity(requestDto.getFirstName(), requestDto.getLastName(), requestDto.getUsername(),
                requestDto.getPassword(), requestDto.getIsActive()));
        LOGGER.info("currently added user - {}", userEntity);
        requestDto.setUserId(userEntity.getId());

        TraineeCreationResponseDto responseDto = traineeToTraineeCreationResponseDtoMapper.map(
            traineeService.create(traineeCreationRequestDtoToTraineeEntityMapper.map(requestDto)));

        idService.autoIncrement();

        LOGGER.info("Successfully created a TraineeEntity based on the TraineeCreationRequestDto - {}, response - {}",
            requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TraineeUpdateResponseDto updateTrainee(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        LOGGER.info("Updating a TraineeEntity based on the TraineeUpdateRequestDto - {}", requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new TraineeUpdateResponseDto(List.of("Authentication failed"));
        }

        TraineeEntity traineeEntity = traineeService.findByUsername(requestDto.getUsername())
            .orElseThrow(() -> new TraineeNotFoundException(requestDto.getUsername()));

        UserEntity userEntity = traineeEntity.getUser();

        userEntity.setFirstName(requestDto.getFirstName());
        userEntity.setLastName(requestDto.getLastName());
        userEntity.setIsActive(requestDto.getIsActive());

        traineeEntity.setAddress(requestDto.getAddress());
        traineeEntity.setAddress(requestDto.getAddress());

        traineeService.update(traineeEntity);
        userService.update(userEntity);

        TraineeUpdateResponseDto responseDto = new TraineeUpdateResponseDto(
            userEntity.getUsername(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            traineeEntity.getDateOfBirth(),
            traineeEntity.getAddress(),
            userEntity.getIsActive(),
            traineeEntity.getTrainerEntityList().stream()
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
                .toList()
        );
        LOGGER.info("Successfully updated a TraineeEntity based on the TraineeUpdateRequestDto - {}, response - {}",
            requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TraineeRetrievalResponseDto retrieveTrainee(TraineeRetrievalByUsernameRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeRetrievalByUsernameRequestDto must not be null");
        String username = requestDto.getUsername();
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        LOGGER.info("Retrieving a Trainee with a username of {}", username);

        if (!userService.usernamePasswordMatching(requestDto.getRetrieverUsername(),
            requestDto.getRetrieverPassword())) {
            return new TraineeRetrievalResponseDto(List.of("Authentication failed"));
        }
        Optional<TraineeEntity> optionalTrainee = traineeService.findByUsername(username);

        if (optionalTrainee.isEmpty()) {
            return new TraineeRetrievalResponseDto(
                List.of(String.format("Trainee with a username of %s not found", username)));
        }

        TraineeEntity traineeEntity = optionalTrainee.get();
        TraineeRetrievalResponseDto responseDto = traineeEntityToTraineeRetrievalResponseDtoMapper.map(traineeEntity);

        LOGGER.info("Successfully retrieved a Trainee with a username of {}, result - {}", username, responseDto);
        return responseDto;
    }

    @Override
    public TraineeDeletionResponseDto deleteTraineeByUsername(TraineeDeletionByUsernameRequestDto requestDto) {

        String username = requestDto.getUsername();
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        LOGGER.info("Deleting a Trainee with a username of {}", username);

        if (!userService.usernamePasswordMatching(requestDto.getDeleterUsername(), requestDto.getDeleterPassword())) {
            return new TraineeDeletionResponseDto(List.of("Authentication failed"));
        }

        traineeService.delete(username);

        TraineeDeletionResponseDto responseDto = new TraineeDeletionResponseDto(HttpStatus.OK);

        LOGGER.info("Successfully deleted a Trainee with a username of {}, result - {}", username, responseDto);
        return responseDto;
    }

    @Override
    public TraineeSwitchActivationStateResponseDto switchActivationState(TraineeSwitchActivationStateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeSwitchActivationStateRequestDto must not be null");

        String username = requestDto.getUsername();
        Assert.notNull(username, "Username must not be null");
        LOGGER.info("Switching the activation state of a trainee with a username of {}", username);

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new TraineeSwitchActivationStateResponseDto(List.of("Authentication failed"));
        }

        TraineeEntity traineeEntity =
            traineeService.findByUsername(username).orElseThrow(() -> new TraineeNotFoundException(username));
        UserEntity user = traineeEntity.getUser();

        user.setIsActive(!user.getIsActive());
        userService.update(user);

        TraineeSwitchActivationStateResponseDto responseDto =
            new TraineeSwitchActivationStateResponseDto(HttpStatus.OK);

        LOGGER.info("Successfully switched the activation state of a Trainee with a username of {}", username);
        return responseDto;
    }

    @Override
    public TraineeUpdateResponseDto changePassword(TraineePasswordChangeRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineePasswordChangeRequestDto must not be null");
        LOGGER.info("Changing the password of a trainee according to the TraineePasswordChangeRequestDto - {}",
            requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getUpdaterUsername(), requestDto.getUpdaterPassword())) {
            return new TraineeUpdateResponseDto(List.of("Authentication failed"));
        }

        TraineeEntity traineeEntity = traineeService.findById(requestDto.getTraineeId())
            .orElseThrow(() -> new TraineeNotFoundException(requestDto.getTraineeId()));

        UserEntity user = traineeEntity.getUser();
        user.setPassword(requestDto.getNewPassword());
        userService.update(user);

        TraineeUpdateResponseDto responseDto = new TraineeUpdateResponseDto(
            user.getUsername(),
            user.getFirstName(),
            user.getLastName(),
            traineeEntity.getDateOfBirth(),
            traineeEntity.getAddress(),
            user.getIsActive(),
            traineeEntity.getTrainerEntityList().stream()
                .map(trainerEntity -> new TrainerDto(
                    new UserDto(
                        trainerEntity.getUser().getFirstName(),
                        trainerEntity.getUser().getPassword(),
                        trainerEntity.getUser().getUsername(),
                        trainerEntity.getUser().getPassword(),
                        trainerEntity.getUser().getIsActive()
                    ),
                    new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType())
                )).toList()
        );

        LOGGER.info("Successfully changed the password of a Trainee according to the request dto - {}, result - {}",
            requestDto, responseDto);
        return responseDto;
    }
}
