package org.example.repository.core;

import java.sql.Date;
import java.util.List;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;

public interface TrainingEntityRepository extends CustomRepository<Long, TrainingEntity> {
    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, TrainingType trainingType);

    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);

    void deleteAllByTraineeUsername(String traineeUsername);

    void deleteAllByTrainerUsername(String trainerUsername);
}
