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
        + "AND (?2 is null or training.date >= ?2) "
        + "AND (?3 is null or training.date <= ?3)"
        + "AND (?4 is null or training.trainer.user.username = ?4)"
        + "AND (?5 is null or training.trainingType = ?5)")
    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, TrainingType trainingType);

    @Query("select training from TrainingEntity training where training.trainer.user.username = ?1 "
        + "AND (?2 is null or training.date >= ?2) "
        + "AND (?3 is null or training.date <= ?3)"
        + "AND (?4 is null or training.trainee.user.username = ?4)")
    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);
}
