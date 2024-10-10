package org.example.mapper.trainer;

import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.plain.UserDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerEntityToTrainerUpdateResponseDtoMapperImpl
    implements TrainerEntityToTrainerUpdateResponseDtoMapper {

    @Override
    public TrainerUpdateResponseDto map(TrainerEntity trainer) {
        Assert.notNull(trainer, "TrainerEntity must not be null");
        return new TrainerUpdateResponseDto(
            trainer.getUser().getUsername(),
            trainer.getUser().getFirstName(),
            trainer.getUser().getLastName(),
            new TrainingTypeDto(trainer.getSpecialization().getTrainingType()),
            trainer.getUser().getIsActive(),
            trainer.getTraineeEntities().stream()
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
}
