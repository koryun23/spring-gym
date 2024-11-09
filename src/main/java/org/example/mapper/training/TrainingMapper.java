package org.example.mapper.training;

import java.util.List;
import org.example.dto.plain.TrainingDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.entity.TrainingEntity;

public interface TrainingMapper {

    TrainingCreationResponseDto mapTrainingEntityToTrainingCreationResponseDto(TrainingEntity trainingEntity);

    TrainingDto mapTrainingEntityToTrainingDto(TrainingEntity trainingEntity);

    TrainingDto mapTrainingCreationRequestDtoToTrainingDto(TrainingCreationRequestDto requestDto);

    List<TrainingDto> mapTrainingEntityListToTrainingDtoList(
        List<TrainingEntity> trainingEntityList);
}
