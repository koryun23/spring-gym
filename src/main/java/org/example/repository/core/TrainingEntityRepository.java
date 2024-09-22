package org.example.repository.core;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.example.entity.TrainingEntity;
import org.springframework.stereotype.Repository;

public interface TrainingEntityRepository extends CustomRepository<Long, TrainingEntity> {
    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, Long trainingTypeId);

    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);
}
