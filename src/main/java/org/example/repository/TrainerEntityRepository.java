package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerEntityRepository extends JpaRepository<TrainerEntity, Long> {

    Optional<TrainerEntity> findByUserUsername(String username);

    @Query("select training.trainer from TrainingEntity training where training.trainee.user.username != ?1")
    List<TrainerEntity> findAllTrainersNotAssignedTo(String traineeUsername);
}
