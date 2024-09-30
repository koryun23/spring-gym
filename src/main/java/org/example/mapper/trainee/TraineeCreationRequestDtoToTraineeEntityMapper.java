package org.example.mapper.trainee;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.example.mapper.Mapper;


//TODO - Problem: mapping code across different classes..
// Why: Multiple classes are performing similar mapping operations.
// What I offer: Create a single, unified mapper class to handle the mapping logic
// for TraineeEntity and other related mappings in one place..
public interface TraineeCreationRequestDtoToTraineeEntityMapper extends
    Mapper<TraineeCreationRequestDto, TraineeEntity> {

    TraineeEntity map(TraineeCreationRequestDto requestDto);
}
