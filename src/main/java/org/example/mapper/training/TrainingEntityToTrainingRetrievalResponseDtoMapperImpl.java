package org.example.mapper.training;

import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.TrainingEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingEntityToTrainingRetrievalResponseDtoMapperImpl
    implements TrainingEntityToTrainingRetrievalResponseDtoMapper {
    @Override
    public TrainingRetrievalResponseDto map(TrainingEntity training) {

        Assert.notNull(training, "TrainingEntity must not be null");
        return new TrainingRetrievalResponseDto(
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
