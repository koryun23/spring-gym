package org.example.mapper.training;

import org.example.dto.response.TrainingCreationResponseDto;
import org.example.entity.TrainingEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingEntityToTrainingCreationResponseDtoMapperImpl
    implements TrainingEntityToTrainingCreationResponseDtoMapper {

    @Override
    public TrainingCreationResponseDto map(TrainingEntity training) {
        Assert.notNull(training, "TrainingEntity must not be null");
        return new TrainingCreationResponseDto(
            training.getTrainingId(),
            training.getTraineeId(),
            training.getTrainerId(),
            training.getName(),
            training.getTrainingType(),
            training.getTrainingDate(),
            training.getDuration()
        );
    }
}
