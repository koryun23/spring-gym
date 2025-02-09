package org.example.mapper.training;

import java.util.List;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.entity.training.TrainingTypeEntity;

public interface TrainingTypeMapper {

    TrainingTypeListRetrievalResponseDto mapTrainingTypeEntityListToTrainingTypeListRetrievalResponseDto(
        List<TrainingTypeEntity> trainingTypeEntityList);
}
