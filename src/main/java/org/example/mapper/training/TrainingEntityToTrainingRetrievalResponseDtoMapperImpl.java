package org.example.mapper.training;

import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.TrainingEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainingEntityToTrainingRetrievalResponseDtoMapperImpl
    implements TrainingEntityToTrainingRetrievalResponseDtoMapper {
    @Override
    public TrainingRetrievalResponseDto map(TrainingEntity training) {
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
