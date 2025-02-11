package org.example.mapper.trainer;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingEntity;
import org.example.entity.training.TrainingType;
import org.example.entity.training.TrainingTypeEntity;
import org.example.entity.user.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class TrainerMapperImpl implements TrainerMapper {

    @Override
    public TrainerCreationResponseDto mapTrainerDtoToTrainerCreationResponseDto(TrainerDto trainer) {
        Assert.notNull(trainer, "Trainer Dto must not be null");
        return new TrainerCreationResponseDto(
            trainer.getUserDto().getUsername(),
            trainer.getUserDto().getPassword()
        );
    }

    @Override
    public TrainerRetrievalResponseDto mapTrainerEntityToTrainerRetrievalResponseDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        List<TrainingEntity> trainings = trainerEntity.getTrainingEntityList();
        List<TraineeDto> trainees = null;
        if (trainings != null) {
            trainees = trainings.stream()
                .map(TrainingEntity::getTrainee)
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
                )).collect(Collectors.toSet()).stream().toList();
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
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
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
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");
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
