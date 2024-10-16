package org.example.mapper.trainee;

import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.example.service.core.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeMapperImpl implements TraineeMapper {

    private final UserService userService;

    public TraineeMapperImpl(UserService userService) {
        this.userService = userService;
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
        return new TraineeUpdateResponseDto(
            userEntity.getUsername(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            trainee.getDateOfBirth(),
            trainee.getAddress(),
            userEntity.getIsActive(),
            trainee.getTrainerEntities().stream()
                .map(trainerEntity -> new TrainerDto(
                    new UserDto(
                        trainerEntity.getUser().getFirstName(),
                        trainerEntity.getUser().getLastName(),
                        trainerEntity.getUser().getUsername(),
                        trainerEntity.getUser().getPassword(),
                        trainerEntity.getUser().getIsActive()),
                    new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType())
                ))
                .toList()
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
}
