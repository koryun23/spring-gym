package com.example.repository;

import com.example.entity.TrainerEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends MongoRepository<TrainerEntity, Long> {

    @Query("{trainerUsername: '?0'}")
    List<TrainerEntity> findAllByTrainerUsername(String trainerUsername);

    @Query(value = "{trainerUsername: '?0', trainingMonth: ?1, trainingYear: ?2}")
    Optional<TrainerEntity> findByTrainerUsernameAndTrainingMonthAndTrainingYear(String trainerUsername,
                                                                                 @NonNull Integer trainingMonth,
                                                                                 @NonNull Integer trainingYear);

    @Query("{'trainerUsername' : ?0, 'trainingMonth' : ?1, 'trainingYear' : ?2}")
    @Update("{'$set': {'duration': ?3}}")
    void updateWorkingHours(String trainerUsername, Integer trainingMonth, Integer trainingYear, Long newWorkingHours);
}
