package org.example.mapper.trainer;

import org.example.dto.request.TrainerCreationRequestDto;
import org.example.entity.TrainerEntity;
import org.example.mapper.Mapper;

public interface TrainerCreationRequestDtoToTrainerEntityMapper
    extends Mapper<TrainerCreationRequestDto, TrainerEntity> {

    TrainerEntity map(TrainerCreationRequestDto requestDto);
}
