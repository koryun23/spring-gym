package org.example.mapper.trainee;

import java.util.Collections;
import java.util.List;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.example.service.core.IdService;
import org.example.service.core.UserService;
import org.example.service.core.UsernamePasswordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeMapperImpl implements TraineeMapper {

    private final UserService userService;
    private final UsernamePasswordService usernamePasswordService;
    private final IdService idService;

    public TraineeMapperImpl(UserService userService,
                             @Qualifier("traineeUsernamePasswordService")
                             UsernamePasswordService usernamePasswordService,
                             @Qualifier("traineeIdService")
                             IdService idService) {
        this.userService = userService;
        this.usernamePasswordService = usernamePasswordService;
        this.idService = idService;
    }

    @Override
    public TraineeEntity mapTraineeCreationRequestDtoToTraineeEntity(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        return new TraineeEntity(
            userService.getByUsername(requestDto.getUsername()),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }

    @Override
    public TraineeCreationResponseDto mapTraineeEntityToTraineeCreationResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeCreationResponseDto(
            trainee.getUser().getFirstName(),
            trainee.getUser().getLastName()
        );
    }

    @Override
    public TraineeRetrievalResponseDto mapTraineeEntityToTraineeRetrievalResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeRetrievalResponseDto(
            trainee.getUser().getFirstName(),
            trainee.getUser().getLastName(),
            trainee.getDateOfBirth(),
            trainee.getAddress(),
            trainee.getUser().getIsActive(),
            trainee.getTrainerEntities().stream().map(trainerEntity -> new TrainerDto(
                new UserDto(
                    trainerEntity.getUser().getFirstName(),
                    trainerEntity.getUser().getLastName(),
                    trainerEntity.getUser().getUsername(),
                    trainerEntity.getUser().getPassword(),
                    trainerEntity.getUser().getIsActive()),
                new TrainingTypeDto(
                    trainerEntity.getSpecialization().getTrainingType()
                )
            )).toList()
        );
    }

    @Override
    public TraineeUpdateResponseDto mapTraineeEntityToTraineeUpdateResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        UserEntity userEntity = trainee.getUser();
        List<TrainerDto> trainerDtoList = Collections.emptyList();
        if (trainee.getTrainerEntities() != null) {
            trainerDtoList = trainee.getTrainerEntities().stream()
                .map(trainerEntity -> new TrainerDto(
                    new UserDto(
                        trainerEntity.getUser().getFirstName(),
                        trainerEntity.getUser().getLastName(),
                        trainerEntity.getUser().getUsername(),
                        trainerEntity.getUser().getPassword(),
                        trainerEntity.getUser().getIsActive()),
                    new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType())
                ))
                .toList();
        }
        return new TraineeUpdateResponseDto(
            userEntity.getUsername(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            trainee.getDateOfBirth(),
            trainee.getAddress(),
            userEntity.getIsActive(),
            trainerDtoList
        );
    }

    @Override
    public TraineeEntity mapTraineeUpdateRequestDtoToTraineeEntity(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        return new TraineeEntity(
            userService.getByUsername(requestDto.getUsername()),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }

    @Override
    public UserEntity mapTraineeUpdateRequestDtoToUserEntity(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        return new UserEntity(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getUsername(),
            userService.getByUsername(requestDto.getUsername()).getPassword(),
            requestDto.getIsActive()
        );
    }

    @Override
    public UserEntity mapTraineeCreationRequestDtoToUserEntity(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        return new UserEntity(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getUsername(),
            requestDto.getPassword(),
            true
        );
    }

    @Override
    public UserEntity mapSwitchActivationStateRequestDtoToUserEntity(
        TraineeSwitchActivationStateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeSwitchActivationStateRequestDto must not be null");

        UserEntity user = userService.getByUsername(requestDto.getUsername());
        user.setIsActive(!user.getIsActive());
        return user;
    }

    @Override
    public TraineeCreationRequestDto mapTraineeCreationRequestDto(TraineeCreationRequestDto requestDto) {
        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();
        requestDto.setUsername(usernamePasswordService.username(firstName, lastName, idService.getId(), "trainee"));
        requestDto.setPassword(usernamePasswordService.password());
        return requestDto;
    }
}
