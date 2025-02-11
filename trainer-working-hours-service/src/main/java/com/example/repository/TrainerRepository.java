package com.example.repository;

import com.example.entity.TrainerEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {

    @Query("select t from TrainerEntity t where t.trainerUsername = ?1")
    List<TrainerEntity> findAllByTrainerUsername(String username);

    @Query("select t from TrainerEntity t where t.trainerUsername = ?1 and "
        + "t.trainingMonth = ?2 and "
        + "t.trainingYear = ?3")
    Optional<TrainerEntity> findByUsernameAndMonthAndYear(String username, @NonNull Integer month,
                                                          @NonNull Integer year);

    @Query("select t from TrainerEntity t where t.trainerUsername = ?1 "
        + "and (?2 is null or t.trainingMonth = ?2) "
        + "and (?3 is null or t.trainingYear = ?3)")
    List<TrainerEntity> findAllByUsernameAndMonthAndYear(String username, @Nullable Integer month,
                                                         @Nullable Integer year);
}
