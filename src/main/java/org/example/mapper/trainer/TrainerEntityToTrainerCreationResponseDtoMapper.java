package org.example.mapper.trainer;

import org.example.dto.response.TrainerCreationResponseDto;
import org.example.entity.TrainerEntity;
import org.example.mapper.Mapper;

public interface TrainerEntityToTrainerCreationResponseDtoMapper
    extends Mapper<TrainerEntity, TrainerCreationResponseDto> {

    TrainerCreationResponseDto map(TrainerEntity trainer);
}
