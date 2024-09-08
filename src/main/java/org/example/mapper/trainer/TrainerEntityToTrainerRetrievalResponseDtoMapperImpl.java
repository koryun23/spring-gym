package org.example.mapper.trainer;

import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerEntityToTrainerRetrievalResponseDtoMapperImpl
    implements TrainerEntityToTrainerRetrievalResponseDtoMapper {

    @Override
    public TrainerRetrievalResponseDto map(TrainerEntity trainer) {
        Assert.notNull(trainer, "TrainerEntity must not be null");
        return new TrainerRetrievalResponseDto(
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
