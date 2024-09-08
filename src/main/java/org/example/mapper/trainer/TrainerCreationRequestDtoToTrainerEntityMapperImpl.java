package org.example.mapper.trainer;

import org.example.dto.request.TrainerCreationRequestDto;
import org.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerCreationRequestDtoToTrainerEntityMapperImpl
    implements TrainerCreationRequestDtoToTrainerEntityMapper {

    @Override
    public TrainerEntity map(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        return new TrainerEntity(
            requestDto.getUserId(),
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getUsername(),
            requestDto.getPassword(),
            requestDto.isActive(),
            requestDto.getSpecializationType()
        );
    }
}
