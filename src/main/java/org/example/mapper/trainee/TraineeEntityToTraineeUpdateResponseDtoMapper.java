package org.example.mapper.trainee;

import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.mapper.Mapper;

public interface TraineeEntityToTraineeUpdateResponseDtoMapper extends Mapper<TraineeEntity, TraineeUpdateResponseDto> {

    TraineeUpdateResponseDto map(TraineeEntity trainee);
}
