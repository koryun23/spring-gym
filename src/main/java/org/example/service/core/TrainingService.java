package org.example.service.core;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.example.entity.TrainingEntity;

public interface TrainingService {

    TrainingEntity create(TrainingEntity params);

    TrainingEntity select(Long id);

    Optional<TrainingEntity> findById(Long id);

    List<TrainingEntity> findAll();

    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, Long trainingTypeId);

    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);

    void deleteAllByTraineeUsername(String traineeUsername);

    void deleteAllByTrainerUsername(String trainerUsername);
}
