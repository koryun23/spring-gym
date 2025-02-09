package org.example.mapper.training;

import com.example.dto.ActionType;
import com.example.dto.TrainerWorkingHoursRequestDto;
import java.util.List;
import org.example.dto.plain.TrainingDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TraineeTrainingRetrievalResponseDto;
import org.example.dto.response.TrainerTrainingRetrievalResponseDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.entity.training.TrainingEntity;
import org.example.entity.user.UserEntity;
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
    public TraineeTrainingRetrievalResponseDto mapTrainingEntityToTraineeTrainingRetrievalResponseDto(
        TrainingEntity trainingEntity) {
        return new TraineeTrainingRetrievalResponseDto(
            trainingEntity.getName(),
            trainingEntity.getDate(),
            trainingEntity.getTrainer().getSpecialization().getId(),
            trainingEntity.getDuration(),
            trainingEntity.getTrainer().getUser().getUsername()
        );
    }

    @Override
    public List<TraineeTrainingRetrievalResponseDto> mapTrainingEntityListToTraineeTrainingRetrievalResponseDtoList(
        List<TrainingEntity> trainings) {
        return trainings.stream()
            .map(this::mapTrainingEntityToTraineeTrainingRetrievalResponseDto)
            .toList();
    }

    @Override
    public TrainerTrainingRetrievalResponseDto mapTrainingEntityToTrainerTrainingRetrievalResponsedto(
        TrainingEntity trainingEntity) {
        return new TrainerTrainingRetrievalResponseDto(
            trainingEntity.getName(),
            trainingEntity.getDate(),
            trainingEntity.getTrainer().getSpecialization().getId(),
            trainingEntity.getDuration(),
            trainingEntity.getTrainee().getUser().getUsername()
        );
    }

    @Override
    public List<TrainerTrainingRetrievalResponseDto> mapTrainingEntityListToTrainerTrainingRetrievalResponseDtoList(
        List<TrainingEntity> trainings) {
        return trainings.stream()
            .map(this::mapTrainingEntityToTrainerTrainingRetrievalResponsedto)
            .toList();
    }

    @Override
    public TrainerWorkingHoursRequestDto mapTrainingEntityToTrainerWorkingHoursAddRequestDto(
        TrainingEntity trainingEntity) {

        UserEntity trainerUserEntity = trainingEntity.getTrainer().getUser();
        return new TrainerWorkingHoursRequestDto(
            trainingEntity.getTrainer().getId(),
            trainerUserEntity.getUsername(),
            trainerUserEntity.getFirstName(),
            trainerUserEntity.getLastName(),
            trainerUserEntity.getIsActive(),
            trainingEntity.getDate(),
            trainingEntity.getDuration(),
            ActionType.ADD
        );
    }

    @Override
    public TrainerWorkingHoursRequestDto mapTrainingEntityToTrainerWorkingHoursRemoveRequestDto(
        TrainingEntity trainingEntity) {
        UserEntity trainerUserEntity = trainingEntity.getTrainer().getUser();
        return new TrainerWorkingHoursRequestDto(
            trainingEntity.getTrainer().getId(),
            trainerUserEntity.getUsername(),
            trainerUserEntity.getFirstName(),
            trainerUserEntity.getLastName(),
            trainerUserEntity.getIsActive(),
            trainingEntity.getDate(),
            trainingEntity.getDuration(),
            ActionType.DELETE
        );
    }
}
