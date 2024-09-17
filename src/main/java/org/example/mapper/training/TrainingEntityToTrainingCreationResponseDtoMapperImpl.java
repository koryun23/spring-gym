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
            training.getId(),
            training.getTrainee().getId(),
            training.getTrainer().getId(),
            training.getName(),
            training.getTrainingType().getId(),
            training.getDate(),
            training.getDuration()
        );
    }
}
