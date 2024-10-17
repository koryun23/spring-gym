package org.example.mapper.training;

import java.util.List;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.entity.TrainingTypeEntity;

public interface TrainingTypeMapper {

    TrainingTypeListRetrievalResponseDto mapTrainingTypeEntityListToTrainingTypeListRetrievalResponseDto(
        List<TrainingTypeEntity> trainingTypeEntityList);
}
