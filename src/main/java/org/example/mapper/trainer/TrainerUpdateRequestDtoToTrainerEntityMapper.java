package org.example.mapper.trainer;

import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.entity.TrainerEntity;
import org.example.mapper.Mapper;

public interface TrainerUpdateRequestDtoToTrainerEntityMapper extends Mapper<TrainerUpdateRequestDto, TrainerEntity> {

    TrainerEntity map(TrainerUpdateRequestDto obj);
}
