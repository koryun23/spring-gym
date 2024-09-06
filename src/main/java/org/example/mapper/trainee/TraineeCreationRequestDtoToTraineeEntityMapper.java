package org.example.mapper.trainee;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.example.mapper.Mapper;

public interface TraineeCreationRequestDtoToTraineeEntityMapper extends
    Mapper<TraineeCreationRequestDto, TraineeEntity> {

    TraineeEntity map(TraineeCreationRequestDto requestDto);
}
