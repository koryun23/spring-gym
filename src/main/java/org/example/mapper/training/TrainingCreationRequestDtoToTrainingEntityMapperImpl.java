package org.example.mapper.training;

import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingCreationRequestDtoToTrainingEntityMapperImpl
    implements TrainingCreationRequestDtoToTrainingEntityMapper {

    private final TraineeService traineeDao;
    private final TrainerService trainerDao;

    /**
     * Constructor.
     */
    public TrainingCreationRequestDtoToTrainingEntityMapperImpl(TraineeService traineeDao, TrainerService trainerDao) {
        this.traineeDao = traineeDao;
        this.trainerDao = trainerDao;
    }

    @Override
    public TrainingEntity map(TrainingCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingCreationRequestDto must not be null");
        TrainerEntity trainerEntity = trainerDao.findByUsername(requestDto.getTrainerUsername())
            .orElseThrow(() -> new TrainerNotFoundException(requestDto.getTrainerUsername()));
        return new TrainingEntity(
            traineeDao.findByUsername(requestDto.getTraineeUsername())
                .orElseThrow(() -> new TraineeNotFoundException(requestDto.getTraineeUsername())),
            trainerEntity,
            requestDto.getTrainingName(),
            trainerEntity.getSpecialization(),
            requestDto.getTrainingDate(),
            requestDto.getTrainingDuration()
        );
    }
}
