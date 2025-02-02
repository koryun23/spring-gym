package com.example.repository;

import com.example.entity.TrainerEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {

    @Query("select t from TrainerEntity t where t.trainerUsername = ?1")
    List<TrainerEntity> findAllByTrainerUsername(String username);

    @Query("select t from TrainerEntity t where t.trainerUsername = ?1 and "
        + "t.trainingMonth = ?2 and "
        + "t.trainingYear = ?3")
    Optional<TrainerEntity> findByUsernameAndMonthAndYear(String username, Integer month, Integer year);
}
