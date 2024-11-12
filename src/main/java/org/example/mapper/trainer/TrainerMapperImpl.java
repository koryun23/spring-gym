package org.example.mapper.trainer;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class TrainerMapperImpl implements TrainerMapper {


    @Override
    public TrainerEntity mapTrainerCreationRequestDtoToTrainerEntity(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        return new TrainerEntity(
            new UserEntity(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                null, null,
                true
            ),
            new TrainingTypeEntity(TrainingType.values()[Math.toIntExact(requestDto.getSpecializationId()) - 1])
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
        List<TrainingEntity> trainings = trainerEntity.getTrainingEntityList();
        List<TraineeDto> trainees = Collections.emptyList();
        if (trainings != null) {
            trainees = trainings.stream()
                .map(TrainingEntity::getTrainee)
                .map(traineeEntity -> new TraineeDto(
                    new UserDto(
                        traineeEntity.getUser().getFirstName(),
                        trainerEntity.getUser().getLastName(),
                        traineeEntity.getUser().getUsername(),
                        traineeEntity.getUser().getPassword(),
                        traineeEntity.getUser().getIsActive()
                    ),
                    traineeEntity.getDateOfBirth(),
                    traineeEntity.getAddress()
                )).toList();
        }
        return new TrainerRetrievalResponseDto(
            trainerEntity.getUser().getUsername(),
            trainerEntity.getUser().getFirstName(),
            trainerEntity.getUser().getLastName(),
            trainerEntity.getSpecialization().getTrainingType(),
            trainerEntity.getUser().getIsActive(),
            trainees
        );
    }

    @Override
    public TrainerUpdateResponseDto mapTrainerEntityToTrainerUpdateResponseDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        return new TrainerUpdateResponseDto(
            trainerEntity.getUser().getUsername(),
            trainerEntity.getUser().getFirstName(),
            trainerEntity.getUser().getLastName(),
            trainerEntity.getSpecialization().getTrainingType(),
            trainerEntity.getUser().getIsActive(),
            trainerEntity.getTrainingEntityList().stream()
                .map(TrainingEntity::getTrainee)
                .map(traineeEntity -> new TraineeDto(
                    new UserDto(
                        traineeEntity.getUser().getFirstName(),
                        trainerEntity.getUser().getLastName(),
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
    public TrainerEntity mapTrainerUpdateRequestDtoToTrainerEntity(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");

        TrainingType trainingType = TrainingType.values()[Math.toIntExact(requestDto.getSpecializationId()) - 1];
        return new TrainerEntity(
            new UserEntity(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUsername(),
                null,
                requestDto.getIsActive()
            ),
            new TrainingTypeEntity(trainingType)
        );
    }

    @Override
    public UserEntity mapTrainerUpdateRequestDtoToUserEntity(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");

        return new UserEntity(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getUsername(),
            null,
            requestDto.getIsActive()
        );
    }

    @Override
    public TrainerDto mapTrainerEntityToTrainerDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer Entity must not be null");

        return new TrainerDto(
            mapTrainerEntityToUserDto(trainerEntity),
            trainerEntity.getSpecialization().getId()
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
    public TrainerDto mapTrainerCreationRequestDtoToTrainerDto(TrainerCreationRequestDto requestDto) {
        return new TrainerDto(
            new UserDto(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                null,
                null,
                true
            ),
            requestDto.getSpecializationId()
        );
    }

    @Override
    public TrainerDto mapTrainerUpdateRequestDtoToTrainerDto(TrainerUpdateRequestDto requestDto) {
        return new TrainerDto(
            new UserDto(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUsername(),
                null,
                requestDto.getIsActive()
            ),
            requestDto.getSpecializationId()
        );
    }
}
