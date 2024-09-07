package org.example.mapper.trainer;

import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainerEntityToTrainerUpdateResponseDtoMapperImpl
    implements TrainerEntityToTrainerUpdateResponseDtoMapper {
    @Override
    public TrainerUpdateResponseDto map(TrainerEntity trainer) {
        return new TrainerUpdateResponseDto(
            trainer.getUserId(),
            trainer.getFirstName(),
            trainer.getLastName(),
            trainer.getUsername(),
            trainer.getPassword(),
            trainer.isActive(),
            trainer.getSpecialization()
        );
    }
}
