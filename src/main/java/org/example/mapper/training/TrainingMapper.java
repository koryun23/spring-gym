package org.example.mapper.training;

import java.util.List;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.TrainingEntity;

public interface TrainingMapper {

    TrainingEntity mapTrainingCreationRequestDtoToTrainingEntity(TrainingCreationRequestDto requestDto);

    TrainingCreationResponseDto mapTrainingEntityToTrainingCreationResponseDto(TrainingEntity trainingEntity);

    TrainingRetrievalResponseDto mapTrainingEntityToTrainingRetrievalResponseDto(TrainingEntity trainingEntity);

    List<TrainingRetrievalResponseDto> mapTrainingEntityListToTrainingRetrievalResponseDtoList(
        List<TrainingEntity> trainingEntityList);
}
