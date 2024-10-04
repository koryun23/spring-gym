package org.example.mapper.trainer;

import org.example.dto.response.TrainerCreationResponseDto;
import org.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerEntityToTrainerCreationResponseDtoMapperImpl
    implements TrainerEntityToTrainerCreationResponseDtoMapper {

    @Override
    public TrainerCreationResponseDto map(TrainerEntity trainer) {
        Assert.notNull(trainer, "TrainerEntity must not be null");
        return new TrainerCreationResponseDto(
            trainer.getUser().getFirstName(),
            trainer.getUser().getLastName()
        );
    }
}
