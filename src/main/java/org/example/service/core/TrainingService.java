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

    List<TrainingEntity> findAllByTrainee(String traineeUsername);

    List<TrainingEntity> findAllByTrainer(String trainerUsername);

    List<TrainingEntity> findAllByTraineeTrainer(String traineeUsername, String trainerUsername);

    List<TrainingEntity> findAllByTraineeDate(String traineeUsername, Date from, Date to);

    List<TrainingEntity> findAllByTrainerDate(String trainerUsername, Date from, Date to);

    List<TrainingEntity> findAllByTraineeTrainerDate(String traineeUsername, Date from, Date to, String trainerUsername);
}
