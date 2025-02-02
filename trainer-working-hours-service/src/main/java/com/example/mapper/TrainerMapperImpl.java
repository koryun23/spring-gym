package com.example.mapper;

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
}
