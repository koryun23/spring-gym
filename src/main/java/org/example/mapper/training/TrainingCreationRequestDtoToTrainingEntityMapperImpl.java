package org.example.mapper.training;

import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.TrainingEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingCreationRequestDtoToTrainingEntityMapperImpl
    implements TrainingCreationRequestDtoToTrainingEntityMapper {

    @Override
    public TrainingEntity map(TrainingCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingCreationRequestDto must not be null");
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
