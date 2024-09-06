package org.example.mapper.training;

import org.example.dto.response.TrainingCreationResponseDto;
import org.example.entity.TrainingEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainingEntityToTrainingRetrievalResponseDtoMapperImpl
    implements TrainingEntityToTrainingCreationResponseDtoMapper {
    @Override
    public TrainingCreationResponseDto map(TrainingEntity training) {
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
