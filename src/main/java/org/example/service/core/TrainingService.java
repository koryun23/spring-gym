package org.example.service.core;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;

public interface TrainingService {

    TrainingEntity create(TrainingEntity params);

    TrainingEntity select(Long id);

    TrainingEntity update(TrainingEntity trainingEntity);

    Optional<TrainingEntity> findById(Long id);

    List<TrainingEntity> findAll();

    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, TrainingType trainingType);

    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);

    void deleteAllByTraineeUsername(String traineeUsername);

    void deleteAllByTrainerUsername(String trainerUsername);
}
