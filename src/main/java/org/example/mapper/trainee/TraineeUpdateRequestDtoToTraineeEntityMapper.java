package org.example.mapper.trainee;

import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.entity.TraineeEntity;
import org.example.mapper.Mapper;

public interface TraineeUpdateRequestDtoToTraineeEntityMapper extends Mapper<TraineeUpdateRequestDto, TraineeEntity> {
    TraineeEntity map(TraineeUpdateRequestDto requestDto);
}
