package org.example.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerEntityRepository extends JpaRepository<TrainerEntity, Long> {

    Optional<TrainerEntity> findByUserUsername(String username);

    @Query("select trainer from TrainerEntity trainer where trainer.user.isActive = true "
        + "and not exists ( "
        + "select trainee "
        + "from TrainingEntity training "
        + "left join TraineeEntity trainee on training.trainee.id = trainee.id "
        + "left join UserEntity user on trainee.user.id = user.id "
        + "where training.trainer.id = trainer.id and user.username = ?1)")
    List<TrainerEntity> findAllTrainersNotAssignedTo(String traineeUsername);

    @Modifying
    @Transactional
    @Query("update TrainerEntity t set t.specialization = ?2 where t.user.username = ?1")
    void update(String username, TrainingTypeEntity trainingTypeEntity);
}
