package org.example.mapper.trainee;

import org.example.dto.response.TraineeCreationResponseDto;
import org.example.entity.TraineeEntity;
import org.example.mapper.Mapper;

public interface TraineeEntityToTraineeCreationResponseDtoMapper extends
    Mapper<TraineeEntity, TraineeCreationResponseDto> {

    TraineeCreationResponseDto map(TraineeEntity trainee);
}
