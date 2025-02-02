package com.example.mapper;

import com.example.dto.TrainerDto;
import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapperImpl implements TrainerMapper {

    @Override
    public TrainerEntity mapTrainerWorkingHoursRequestDtoToTrainerEntity(TrainerWorkingHoursRequestDto requestDto) {
        return new TrainerEntity(
            requestDto.getTrainerUsername(),
            requestDto.getTrainerFirstName(),
            requestDto.getTrainerLastName(),
            requestDto.getIsActive(),
            requestDto.getDate().toLocalDate().getYear(),
            requestDto.getDate().toLocalDate().getMonthValue(),
            requestDto.getDuration()
        );
    }

    @Override
    public TrainerDto mapTrainerEntityToTrainerDto(TrainerEntity trainerEntity) {
        return new TrainerDto(
            trainerEntity.getTrainerUsername(),
            trainerEntity.getTrainerFirstName(),
            trainerEntity.getTrainerLastName(),
            trainerEntity.getIsActive(),
            trainerEntity.getTrainingYear(),
            trainerEntity.getTrainingMonth(),
            trainerEntity.getDuration()
        );
    }
}
