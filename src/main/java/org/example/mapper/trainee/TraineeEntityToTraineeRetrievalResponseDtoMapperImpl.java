package org.example.mapper.trainee;

import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.plain.UserDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeEntityToTraineeRetrievalResponseDtoMapperImpl
    implements TraineeEntityToTraineeRetrievalResponseDtoMapper {

    @Override
    public TraineeRetrievalResponseDto map(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeRetrievalResponseDto(
            trainee.getUser().getFirstName(),
            trainee.getUser().getLastName(),
            trainee.getDateOfBirth(),
            trainee.getAddress(),
            trainee.getUser().getIsActive(),
            trainee.getTrainerEntityList().stream().map(trainerEntity -> new TrainerDto(
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
}
