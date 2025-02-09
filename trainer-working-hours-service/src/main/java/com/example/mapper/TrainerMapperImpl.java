package com.example.mapper;

import com.example.dto.TrainerDto;
import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursResponseDto;
import com.example.entity.TrainerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class TrainerMapperImpl implements TrainerMapper {

    @Override
    public TrainerEntity mapTrainerWorkingHoursRequestDtoToTrainerEntity(TrainerWorkingHoursRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerWorkingHoursRequestDto must not be null");
        log.info("Mapping a TrainerWorkingHoursRequestDto to a TrainerEntity");
        TrainerEntity trainerEntity = new TrainerEntity(
            requestDto.getTrainerUsername(),
            requestDto.getTrainerFirstName(),
            requestDto.getTrainerLastName(),
            requestDto.getIsActive(),
            requestDto.getDate().toLocalDate().getYear(),
            requestDto.getDate().toLocalDate().getMonthValue(),
            requestDto.getDuration()
        );
        log.info("Successfully mapped a TrainerWorkingHoursRequestDto to a TrainerEntity");
        return trainerEntity;
    }

    @Override
    public TrainerDto mapTrainerEntityToTrainerDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer Entity must not be null");
        log.info("Mapping a TrainerEntity to TrainerDto");
        TrainerDto trainerDto = new TrainerDto(
            trainerEntity.getTrainerId(),
            trainerEntity.getTrainerUsername(),
            trainerEntity.getTrainerFirstName(),
            trainerEntity.getTrainerLastName(),
            trainerEntity.getIsActive(),
            trainerEntity.getTrainingYear(),
            trainerEntity.getTrainingMonth(),
            trainerEntity.getDuration()
        );
        log.info("Successfully mapped a TrainerEntity to TrainerDto");
        return trainerDto;
    }

    @Override
    public TrainerWorkingHoursResponseDto mapTrainerEntityToTrainerWorkingHoursResponseDto(
        TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer Entity must not be null");
        log.info("Mapping a TrainerEntity to TrainerWorkingHoursResponseDto");
        TrainerWorkingHoursResponseDto responseDto =
            new TrainerWorkingHoursResponseDto(trainerEntity.getTrainerUsername(),
                trainerEntity.getDuration());
        log.info("Successfully mapped a TrainerEntity to TrainerWorkingHoursResponseDto");
        return responseDto;
    }
}
