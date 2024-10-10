package org.example.mapper.trainer;

import org.example.dto.request.TrainerCreationRequestDto;
import org.example.entity.TrainerEntity;
import org.example.service.core.TrainingTypeService;
import org.example.service.core.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerCreationRequestDtoToTrainerEntityMapperImpl
    implements TrainerCreationRequestDtoToTrainerEntityMapper {

    private final UserService userService;
    private final TrainingTypeService trainingTypeService;

    public TrainerCreationRequestDtoToTrainerEntityMapperImpl(UserService userService,
                                                              TrainingTypeService trainingTypeService) {
        this.userService = userService;
        this.trainingTypeService = trainingTypeService;
    }

    @Override
    public TrainerEntity map(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        return new TrainerEntity(
            userService.getByUsername(requestDto.getUsername()),
            trainingTypeService.get(requestDto.getTrainingTypeId())
        );
    }
}
