package org.example.mapper.trainer;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.exception.TrainingTypeNotFoundException;
import org.example.service.core.IdService;
import org.example.service.core.TrainingTypeService;
import org.example.service.core.UserService;
import org.example.service.core.UsernamePasswordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class TrainerMapperImpl implements TrainerMapper {

    private final UserService userService;
    private final TrainingTypeService trainingTypeService;
    private final UsernamePasswordService usernamePasswordService;
    private final IdService idService;

    /**
     * Constructor.
     */
    public TrainerMapperImpl(UserService userService,
                             TrainingTypeService trainingTypeService,
                             @Qualifier("trainerUsernamePasswordService")
                             UsernamePasswordService usernamePasswordService,
                             @Qualifier("trainerIdService")
                             IdService idService) {
        this.userService = userService;
        this.trainingTypeService = trainingTypeService;
        this.usernamePasswordService = usernamePasswordService;
        this.idService = idService;
    }

    @Override
    public TrainerEntity mapTrainerCreationRequestDtoToTrainerEntity(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        return new TrainerEntity(
            new UserEntity(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                usernamePasswordService.username(requestDto.getFirstName(), requestDto.getLastName(), idService.getId(),
                    "trainer"),
                usernamePasswordService.password(),
                true
            ),
            trainingTypeService.get(requestDto.getTrainingTypeId())
        );
    }

    @Override
    public TrainerCreationResponseDto mapTrainerEntityToTrainerCreationResponseDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        return new TrainerCreationResponseDto(
            trainerEntity.getUser().getUsername(),
            trainerEntity.getUser().getPassword()
        );
    }

    @Override
    public TrainerRetrievalResponseDto mapTrainerEntityToTrainerRetrievalResponseDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        return new TrainerRetrievalResponseDto(
            trainerEntity.getUser().getUsername(),
            trainerEntity.getUser().getFirstName(),
            trainerEntity.getUser().getLastName(),
            new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType()),
            trainerEntity.getUser().getIsActive(),
            trainerEntity.getTraineeEntities().stream()
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
    }

    @Override
    public TrainerUpdateResponseDto mapTrainerEntityToTrainerUpdateResponseDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        List<TraineeDto> traineeDtoList = Collections.emptyList();
        if (trainerEntity.getTraineeEntities() != null) {
            traineeDtoList = trainerEntity.getTraineeEntities().stream()
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
                )).toList();
        }
        return new TrainerUpdateResponseDto(
            trainerEntity.getUser().getUsername(),
            trainerEntity.getUser().getFirstName(),
            trainerEntity.getUser().getLastName(),
            new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType()),
            trainerEntity.getUser().getIsActive(),
            traineeDtoList
        );
    }

    @Override
    public TrainerEntity mapTrainerUpdateRequestDtoToTrainerEntity(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");

        TrainingType trainingType = requestDto.getSpecialization().getTrainingType();
        return new TrainerEntity(
            userService.getByUsername(requestDto.getUsername()),
            trainingTypeService.findByTrainingType(trainingType).orElseThrow(
                () -> new TrainingTypeNotFoundException(trainingType.toString()))
        );
    }

    @Override
    public UserEntity mapTrainerCreationRequestDtoToUserEntity(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");

        return new UserEntity(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            usernamePasswordService.username(requestDto.getFirstName(), requestDto.getLastName(), idService.getId(),
                "trainer"),
            usernamePasswordService.password(),
            true
        );
    }

    @Override
    public UserEntity mapTrainerUpdateRequestDtoToUserEntity(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");

        return new UserEntity(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getUsername(),
            userService.findByUsername(requestDto.getUsername()).get().getPassword(),
            requestDto.getIsActive()
        );
    }

    @Override
    public UserEntity mapSwitchActivationStateRequestDtoToUserEntity(
        TrainerSwitchActivationStateRequestDto requestDto) {

        Assert.notNull(requestDto, "TrainerSwitchActivationStateRequestDto must not be null");

        UserEntity userEntity = userService.getByUsername(requestDto.getUsername());
        userEntity.setIsActive(!userEntity.getIsActive());
        return userEntity;
    }

    @Override
    public TrainerDto mapTrainerEntityToTrainerDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer Entity must not be null");

        return new TrainerDto(
            mapTrainerEntityToUserDto(trainerEntity),
            new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType())
        );
    }

    @Override
    public TrainerEntity mapTrainerDtoToTrainerEntity(TrainerDto trainerDto) {
        Assert.notNull(trainerDto, "Trainer dto must not be null");

        return new TrainerEntity(
            mapTrainerDtoToUserEntity(trainerDto),
            new TrainingTypeEntity(trainerDto.getTrainingTypeDto().getTrainingType())
        );
    }

    @Override
    public UserEntity mapTrainerDtoToUserEntity(TrainerDto trainerDto) {
        Assert.notNull(trainerDto, "Trainer Dto must not be null");
        return new UserEntity(
            trainerDto.getUserDto().getFirstName(),
            trainerDto.getUserDto().getLastName(),
            trainerDto.getUserDto().getUsername(),
            trainerDto.getUserDto().getPassword(),
            trainerDto.getUserDto().getIsActive()
        );
    }

    @Override
    public UserDto mapTrainerEntityToUserDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer Entity must not be null");

        return new UserDto(
            trainerEntity.getUser().getFirstName(),
            trainerEntity.getUser().getLastName(),
            trainerEntity.getUser().getUsername(),
            trainerEntity.getUser().getPassword(),
            trainerEntity.getUser().getIsActive()
        );
    }

    @Override
    public TrainerCreationRequestDto mapTrainerCreationRequestDto(TrainerCreationRequestDto requestDto) {
//        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
//
//        requestDto.setUsername(
//            usernamePasswordService.username(requestDto.getFirstName(), requestDto.getLastName(), idService.getId(),
//                "trainer"));
//        requestDto.setPassword(usernamePasswordService.password());

        return requestDto;
    }


}
