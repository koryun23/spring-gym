package org.example.mapper.training;

import java.util.List;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.entity.training.TrainingTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeMapperImpl implements TrainingTypeMapper {

    @Override
    public TrainingTypeListRetrievalResponseDto mapTrainingTypeEntityListToTrainingTypeListRetrievalResponseDto(
        List<TrainingTypeEntity> trainingTypeEntityList) {
        return new TrainingTypeListRetrievalResponseDto(
            trainingTypeEntityList.stream()
                .map(TrainingTypeEntity::getTrainingType).toList()
        );
    }
}
