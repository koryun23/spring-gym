package org.example.mapper.trainee;

import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.entity.TraineeEntity;
import org.example.mapper.Mapper;

public interface TraineeEntityToTraineeRetrievalResponseDtoMapper extends
    Mapper<TraineeEntity, TraineeRetrievalResponseDto> {
    TraineeRetrievalResponseDto map(TraineeEntity trainee);
}
