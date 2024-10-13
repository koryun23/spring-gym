package org.example.mapper.trainer;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.exception.TrainingTypeNotFoundException;
import org.example.service.core.TrainingTypeService;
import org.example.service.core.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class TrainerMapperImpl implements TrainerMapper {

    private UserService userService;
    private TrainingTypeService trainingTypeService;

    public TrainerMapperImpl(UserService userService, TrainingTypeService trainingTypeService) {
        this.userService = userService;
        this.trainingTypeService = trainingTypeService;
    }

    @Override
    public TrainerEntity mapTrainerCreationRequestDtoToTrainerEntity(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        return new TrainerEntity(
            userService.getByUsername(requestDto.getUsername()),
            trainingTypeService.get(requestDto.getTrainingTypeId())
        );
    }

    @Override
    public TrainerCreationResponseDto mapTrainerEntityToTrainerCreationResponseDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        return new TrainerCreationResponseDto(
            trainerEntity.getUser().getFirstName(),
            trainerEntity.getUser().getLastName()
        );
    }

    @Override
    public TrainerRetrievalResponseDto mapTrainerEntityToTrainerRetrievalResponseDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        return new TrainerRetrievalResponseDto(
            trainerEntity.getUser().getUsername(),
            trainerEntity.getUser().getFirstName(),
            trainerEntity.getUser().getLastName(),
            new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType()),
            trainerEntity.getUser().getIsActive(),
            trainerEntity.getTraineeEntities().stream()
                .map(traineeEntity -> new TraineeDto(
                    new UserDto(
                        traineeEntity.getUser().getFirstName(),
                        traineeEntity.getUser().getLastName(),
                        traineeEntity.getUser().getUsername(),
                        traineeEntity.getUser().getPassword(),
                        traineeEntity.getUser().getIsActive()
                    ),
                    traineeEntity.getDateOfBirth(),
                    traineeEntity.getAddress()
                )).toList()
        );
    }

    @Override
    public TrainerUpdateResponseDto mapTrainerEntityToTrainerUpdateResponseDto(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        return new TrainerUpdateResponseDto(
            trainerEntity.getUser().getUsername(),
            trainerEntity.getUser().getFirstName(),
            trainerEntity.getUser().getLastName(),
            new TrainingTypeDto(trainerEntity.getSpecialization().getTrainingType()),
            trainerEntity.getUser().getIsActive(),
            trainerEntity.getTraineeEntities().stream()
                .map(traineeEntity -> new TraineeDto(
                    new UserDto(
                        traineeEntity.getUser().getFirstName(),
                        traineeEntity.getUser().getLastName(),
                        traineeEntity.getUser().getUsername(),
                        traineeEntity.getUser().getPassword(),
                        traineeEntity.getUser().getIsActive()
                    ),
                    traineeEntity.getDateOfBirth(),
                    traineeEntity.getAddress()
                )).toList()
        );
    }

    @Override
    public TrainerEntity mapTrainerUpdateRequestDtoToTrainerEntity(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");

        TrainingType trainingType = requestDto.getSpecialization().getTrainingType();
        return new TrainerEntity(
            userService.getByUsername(requestDto.getUsername()),
            trainingTypeService.findByTrainingType(trainingType).orElseThrow(
                () -> new TrainingTypeNotFoundException(trainingType.toString()))
        );
    }
}
