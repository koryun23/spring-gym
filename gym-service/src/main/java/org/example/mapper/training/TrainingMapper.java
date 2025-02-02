package org.example.mapper.training;

import com.example.dto.TrainerWorkingHoursRequestDto;
import java.util.List;
import org.example.dto.plain.TrainingDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TraineeTrainingRetrievalResponseDto;
import org.example.dto.response.TrainerTrainingRetrievalResponseDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.entity.training.TrainingEntity;

public interface TrainingMapper {

    TrainingCreationResponseDto mapTrainingEntityToTrainingCreationResponseDto(TrainingEntity trainingEntity);

    TrainingDto mapTrainingEntityToTrainingDto(TrainingEntity trainingEntity);

    TrainingDto mapTrainingCreationRequestDtoToTrainingDto(TrainingCreationRequestDto requestDto);

    TraineeTrainingRetrievalResponseDto mapTrainingEntityToTraineeTrainingRetrievalResponseDto(
        TrainingEntity trainingEntity);

    List<TraineeTrainingRetrievalResponseDto> mapTrainingEntityListToTraineeTrainingRetrievalResponseDtoList(
        List<TrainingEntity> trainings);

    TrainerTrainingRetrievalResponseDto mapTrainingEntityToTrainerTrainingRetrievalResponsedto(
        TrainingEntity trainingEntity);

    List<TrainerTrainingRetrievalResponseDto> mapTrainingEntityListToTrainerTrainingRetrievalResponseDtoList(
        List<TrainingEntity> trainings);

    TrainerWorkingHoursRequestDto mapTrainingEntityToTrainerWorkingHoursAddRequestDto(TrainingEntity trainingEntity);

    TrainerWorkingHoursRequestDto mapTrainingEntityToTrainerWorkingHoursRemoveRequestDto(TrainingEntity trainingEntity);

}
