package org.example.repository;

import java.sql.Date;
import java.util.List;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingEntityRepository extends JpaRepository<TrainingEntity, Long> {

    @Query("select training from TrainingEntity training where training.trainee.user.username = ?1 "
        + "AND ((training.date > ?2 AND training.date < ?3) OR "
        + "training.trainer.user.username = ?4 OR training.trainingType = ?5)")
    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, TrainingType trainingType);

    @Query("select training from TrainingEntity training where training.trainer.user.username = ?1 "
        + "AND ((training.date > ?2 AND training.date < ?3) OR "
        + "training.trainee.user.username = ?4)")
    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);

    void deleteAllByTraineeUserUsername(String traineeUsername);

    void deleteAllByTrainerUserUsername(String trainerUsername);
}
