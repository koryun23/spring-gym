package org.example.mapper.training;

import java.util.List;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingMapperImpl implements TrainingMapper {

    private final TrainerService trainerService;
    private final TraineeService traineeService;

    public TrainingMapperImpl(TrainerService trainerService, TraineeService traineeService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    @Override
    public TrainingEntity mapTrainingCreationRequestDtoToTrainingEntity(TrainingCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingCreationRequestDto must not be null");
        TrainerEntity trainerEntity = trainerService.findByUsername(requestDto.getTrainerUsername())
            .orElseThrow(() -> new TrainerNotFoundException(requestDto.getTrainerUsername()));
        return new TrainingEntity(
            traineeService.findByUsername(requestDto.getTraineeUsername())
                .orElseThrow(() -> new TraineeNotFoundException(requestDto.getTraineeUsername())),
            trainerEntity,
            requestDto.getTrainingName(),
            trainerEntity.getSpecialization(),
            requestDto.getTrainingDate(),
            requestDto.getTrainingDuration()
        );
    }

    @Override
    public TrainingCreationResponseDto mapTrainingEntityToTrainingCreationResponseDto(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        return new TrainingCreationResponseDto(
            HttpStatus.OK
        );
    }

    @Override
    public TrainingRetrievalResponseDto mapTrainingEntityToTrainingRetrievalResponseDto(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        return new TrainingRetrievalResponseDto(
            trainingEntity.getId(),
            trainingEntity.getTrainee().getId(),
            trainingEntity.getTrainer().getId(),
            trainingEntity.getName(),
            trainingEntity.getTrainingType().getId(),
            trainingEntity.getDate(),
            trainingEntity.getDuration()
        );
    }

    @Override
    public List<TrainingRetrievalResponseDto> mapTrainingEntityListToTrainingRetrievalResponseDtoList(
        List<TrainingEntity> trainingEntityList) {

        Assert.notNull(trainingEntityList, "Training Entity List must not be null");
        return trainingEntityList.stream().map(this::mapTrainingEntityToTrainingRetrievalResponseDto).toList();
    }
}
