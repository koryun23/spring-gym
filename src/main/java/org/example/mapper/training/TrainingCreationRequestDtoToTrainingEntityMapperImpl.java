package org.example.mapper.training;

import org.example.dao.core.TraineeDao;
import org.example.dao.core.TrainerDao;
import org.example.dao.core.TrainingTypeDao;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainingCreationRequestDtoToTrainingEntityMapperImpl
    implements TrainingCreationRequestDtoToTrainingEntityMapper {

    private final TraineeDao traineeDao;
    private final TrainerDao trainerDao;
    private final TrainingTypeDao trainingTypeDao;

    /**
     * Constructor.
     */
    public TrainingCreationRequestDtoToTrainingEntityMapperImpl(TraineeDao traineeDao, TrainerDao trainerDao,
                                                                TrainingTypeDao trainingTypeDao) {
        this.traineeDao = traineeDao;
        this.trainerDao = trainerDao;
        this.trainingTypeDao = trainingTypeDao;
    }

    @Override
    public TrainingEntity map(TrainingCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainingCreationRequestDto must not be null");
        TrainerEntity trainerEntity = trainerDao.getByUsername(requestDto.getTrainerUsername());
        return new TrainingEntity(
            traineeDao.getByUsername(requestDto.getTraineeUsername()),
            trainerEntity,
            requestDto.getTrainingName(),
            trainerEntity.getSpecialization(),
            requestDto.getTrainingDate(),
            requestDto.getTrainingDuration()
        );
    }
}
