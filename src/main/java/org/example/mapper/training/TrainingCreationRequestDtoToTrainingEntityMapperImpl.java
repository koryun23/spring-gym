package org.example.mapper.training;

import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.TrainingEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainingCreationRequestDtoToTrainingEntityMapperImpl
    implements TrainingCreationRequestDtoToTrainingEntityMapper {

    @Override
    public TrainingEntity map(TrainingCreationRequestDto requestDto) {
        return new TrainingEntity(
            requestDto.getTrainingId(),
            requestDto.getTraineeId(),
            requestDto.getTrainerId(),
            requestDto.getName(),
            requestDto.getTrainingType(),
            requestDto.getTrainingDate(),
            requestDto.getDuration()
        );
    }
}
