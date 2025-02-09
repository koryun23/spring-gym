package org.example.service.core.training;

import java.sql.Date;
import java.util.List;
import org.example.dto.plain.TrainingDto;
import org.example.entity.training.TrainingEntity;

public interface TrainingService {

    TrainingEntity create(TrainingDto training);

    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, Long trainingTypeId);

    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);

    List<TrainingEntity> findAllByTrainerUsername(String trainerUsername);

    List<TrainingEntity> findAllByTraineeUsername(String traineeUsername);
}
