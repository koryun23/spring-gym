package org.example.repository;

import java.sql.Date;
import java.util.List;
import org.example.entity.training.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingEntityRepository extends JpaRepository<TrainingEntity, Long> {

    @Query("select training from TrainingEntity training where training.trainee.user.username = ?1 "
        + "and (training.date >= coalesce(?2, training.date)) "
        + "and (training.date <= coalesce(?3, training.date)) "
        + "and (?4 is null or training.trainer.user.username = ?4) "
        + "and (?5 is null or training.trainingType.id = ?5)")
    List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                             String trainerUsername, Long trainingTypeId);

    @Query("select training from TrainingEntity training where training.trainer.user.username = ?1 "
        + "and training.date >= coalesce(?2, training.date) "
        + "and training.date <= coalesce(?3, training.date) "
        + "and (?4 is null or training.trainee.user.username = ?4) ")
    List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                             String traineeUsername);
}
