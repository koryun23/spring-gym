package org.example.mapper.trainer;

import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.entity.TrainerEntity;
import org.example.repository.core.TrainingTypeEntityRepository;
import org.example.service.core.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerUpdateRequestDtoToTrainerEntityMapperImpl implements TrainerUpdateRequestDtoToTrainerEntityMapper {

    private final UserService userService;
    private final TrainingTypeEntityRepository trainingTypeEntityRepository;

    public TrainerUpdateRequestDtoToTrainerEntityMapperImpl(UserService userService,
                                                            TrainingTypeEntityRepository trainingTypeEntityRepository) {
        this.userService = userService;
        this.trainingTypeEntityRepository = trainingTypeEntityRepository;
    }


    @Override
    public TrainerEntity map(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");

        return new TrainerEntity(
            userService.getByUsername(requestDto.getUsername()),
            trainingTypeEntityRepository.getByTrainingType(requestDto.getSpecialization().getTrainingType())
        );
    }
}
