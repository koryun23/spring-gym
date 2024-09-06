package org.example.mapper.trainer;

import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainerUpdateRequestDtoToTrainerEntityMapperImpl implements TrainerUpdateRequestDtoToTrainerEntityMapper {
    @Override
    public TrainerEntity map(TrainerUpdateRequestDto requestDto) {
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
