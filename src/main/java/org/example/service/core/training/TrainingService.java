package org.example.service.core.training;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.example.dto.plain.TrainingDto;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;

public interface TrainingService {

    TrainingEntity create(TrainingDto training);

    TrainingEntity select(Long id);

    TrainingEntity update(TrainingEntity trainingEntity);

    Optional<TrainingEntity> findById(Long id);

    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, Long trainingTypeId);

    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);
}
