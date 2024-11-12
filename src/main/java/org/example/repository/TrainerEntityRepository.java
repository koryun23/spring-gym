package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerEntityRepository extends JpaRepository<TrainerEntity, Long> {

    Optional<TrainerEntity> findByUserUsername(String username);

    @Query("select trainer from TrainerEntity trainer " +
        "where not exists ( " +
        "select trainee " +
        "from TrainingEntity training " +
        "left join TraineeEntity trainee on training.trainee.id = trainee.id " +
        "left join UserEntity user on trainee.user.id = user.id " +
        "where training.trainer.id = trainer.id and user.username = ?1)")
    List<TrainerEntity> findAllTrainersNotAssignedTo(String traineeUsername);

    @Query("update TrainerEntity t set t.specialization = ?2 where t.user.username = ?1")
    TrainerEntity update(String username, TrainingType trainingType);
}
