package org.example.mapper.trainer;

import org.example.dto.response.TrainerCreationResponseDto;
import org.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainerEntityToTrainerUpdateResponseDtoMapperImpl
    implements TrainerEntityToTrainerCreationResponseDtoMapper {
    @Override
    public TrainerCreationResponseDto map(TrainerEntity trainer) {
        return new TrainerCreationResponseDto(
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
