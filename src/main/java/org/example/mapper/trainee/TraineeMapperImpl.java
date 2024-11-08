package org.example.mapper.trainee;

import java.util.List;
import java.util.stream.Collectors;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.entity.UserEntity;
import org.example.service.core.training.TrainingService;
import org.example.service.core.user.UserService;
import org.example.service.core.user.UsernamePasswordService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeMapperImpl implements TraineeMapper {

    @Override
    public TraineeEntity mapTraineeCreationRequestDtoToTraineeEntity(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        return new TraineeEntity(
            new UserEntity(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                null,
                null,
                true
            ),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }

    @Override
    public TraineeCreationResponseDto mapTraineeEntityToTraineeCreationResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeCreationResponseDto(
            trainee.getUser().getUsername(),
            trainee.getUser().getPassword()
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
            trainee.getTrainingEntityList().stream()
                    .map(TrainingEntity::getTrainer)
                    .map(trainerEntity -> new TrainerDto(
                            new UserDto(
                                    trainerEntity.getUser().getFirstName(),
                                    trainerEntity.getUser().getLastName(),
                                    trainerEntity.getUser().getUsername(),
                                    trainerEntity.getUser().getPassword(),
                                    trainerEntity.getUser().getIsActive()
                            ),
                            trainerEntity.getSpecialization().getTrainingType()
                    )).toList()
        );
    }

    @Override
    public TraineeUpdateResponseDto mapTraineeEntityToTraineeUpdateResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        UserEntity userEntity = trainee.getUser();
        List<TrainerDto> trainerDtoList =
            trainee.getTrainingEntityList()
                .stream()
                .map(TrainingEntity::getTrainer)
                .map(trainerEntity -> new TrainerDto(
                    new UserDto(
                        trainerEntity.getUser().getFirstName(),
                        trainerEntity.getUser().getLastName(),
                        trainerEntity.getUser().getUsername(),
                        trainerEntity.getUser().getPassword(),
                        trainerEntity.getUser().getIsActive()
                    ),
                    trainerEntity.getSpecialization().getTrainingType()
                )).collect(Collectors.toSet()).stream().toList();

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
            null,
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
            null,
            requestDto.getIsActive()
        );
    }
}
