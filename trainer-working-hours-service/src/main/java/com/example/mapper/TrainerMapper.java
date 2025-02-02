package com.example.mapper;

import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.entity.TrainerEntity;

public interface TrainerMapper {

    TrainerEntity mapTrainerWorkingHoursRequestDtoToTrainerEntity(TrainerWorkingHoursRequestDto requestDto);
}
