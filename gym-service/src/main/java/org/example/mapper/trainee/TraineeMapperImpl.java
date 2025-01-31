package org.example.mapper.trainee;

import java.util.List;
import java.util.stream.Collectors;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.training.TrainingEntity;
import org.example.entity.user.UserEntity;
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
    public TraineeCreationResponseDto mapTraineeDtoToTraineeCreationResponseDto(TraineeDto trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeCreationResponseDto(
            trainee.getUserDto().getUsername(),
            trainee.getUserDto().getPassword()
        );
    }

    @Override
    public TraineeRetrievalResponseDto mapTraineeEntityToTraineeRetrievalResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        List<TrainingEntity> trainingEntityList = trainee.getTrainingEntityList();

        List<TrainerDto> assignedTrainers = null;
        if (trainingEntityList != null && !trainingEntityList.isEmpty()) {
            assignedTrainers = trainingEntityList.stream()
                .map(TrainingEntity::getTrainer)
                .map(trainerEntity -> new TrainerDto(
                    new UserDto(
                        trainerEntity.getUser().getFirstName(),
                        trainerEntity.getUser().getLastName(),
                        trainerEntity.getUser().getUsername(),
                        trainerEntity.getUser().getPassword(),
                        trainerEntity.getUser().getIsActive()
                    ),
                    trainerEntity.getSpecialization().getId()
                )).collect(Collectors.toSet()).stream().toList();
        }
        return new TraineeRetrievalResponseDto(
            trainee.getUser().getFirstName(),
            trainee.getUser().getLastName(),
            trainee.getDateOfBirth(),
            trainee.getAddress(),
            trainee.getUser().getIsActive(),
            assignedTrainers
        );
    }

    @Override
    public TraineeUpdateResponseDto mapTraineeEntityToTraineeUpdateResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        UserEntity userEntity = trainee.getUser();
        List<TrainingEntity> trainingEntityList = trainee.getTrainingEntityList();
        List<TrainerDto> assignedTrainers = null;
        if (trainingEntityList != null && !trainingEntityList.isEmpty()) {
            assignedTrainers =
                trainingEntityList
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
                        trainerEntity.getSpecialization().getId()
                    )).collect(Collectors.toSet()).stream().toList();
        }

        return new TraineeUpdateResponseDto(
            userEntity.getUsername(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            trainee.getDateOfBirth(),
            trainee.getAddress(),
            userEntity.getIsActive(),
            assignedTrainers
        );
    }

    @Override
    public TraineeEntity mapTraineeUpdateRequestDtoToTraineeEntity(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        return new TraineeEntity(
            new UserEntity(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUsername(),
                null,
                requestDto.getIsActive()
            ),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }
}
