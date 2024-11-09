package org.example.mapper.training;

import java.util.List;
import org.example.dto.plain.TrainingDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.entity.TrainingEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingMapperImpl implements TrainingMapper {

    @Override
    public TrainingCreationResponseDto mapTrainingEntityToTrainingCreationResponseDto(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        return new TrainingCreationResponseDto(
            HttpStatus.OK
        );
    }

    @Override
    public TrainingDto mapTrainingEntityToTrainingDto(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "TrainingEntity must not be null");
        return new TrainingDto(
            trainingEntity.getTrainee().getUser().getUsername(),
            trainingEntity.getTrainer().getUser().getUsername(),
            trainingEntity.getName(),
            trainingEntity.getDate(),
            trainingEntity.getDuration()
        );
    }

    @Override
    public TrainingDto mapTrainingCreationRequestDtoToTrainingDto(TrainingCreationRequestDto requestDto) {
        return new TrainingDto(
            requestDto.getTraineeUsername(),
            requestDto.getTrainerUsername(),
            requestDto.getTrainingName(),
            requestDto.getTrainingDate(),
            requestDto.getTrainingDuration()
        );
    }

    @Override
    public List<TrainingDto> mapTrainingEntityListToTrainingDtoList(
        List<TrainingEntity> trainingEntityList) {

        Assert.notNull(trainingEntityList, "Training Entity List must not be null");
        return trainingEntityList.stream().map(this::mapTrainingEntityToTrainingDto).toList();
    }
}
