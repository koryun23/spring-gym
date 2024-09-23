package org.example.repository.core;

import java.sql.Date;
import java.util.List;
import org.example.entity.TrainingEntity;

public interface TrainingEntityRepository extends CustomRepository<Long, TrainingEntity> {
    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, Long trainingTypeId);

    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);

    void deleteAllByTraineeUsername(String traineeUsername);

    void deleteAllByTrainerUsername(String trainerUsername);
}
