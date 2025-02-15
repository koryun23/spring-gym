package com.example.mapper;

import com.example.dto.TrainerDto;
import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursResponseDto;
import com.example.entity.TrainerEntity;

public interface TrainerMapper {

    TrainerEntity mapTrainerWorkingHoursRequestDtoToTrainerEntity(TrainerWorkingHoursRequestDto requestDto);

    TrainerDto mapTrainerEntityToTrainerDto(TrainerEntity trainerEntity);

    TrainerWorkingHoursResponseDto mapTrainerEntityToTrainerWorkingHoursResponseDto(TrainerEntity trainerEntity);
}
