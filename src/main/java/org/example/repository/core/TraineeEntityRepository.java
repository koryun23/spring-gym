package org.example.repository.core;

import java.util.Optional;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeEntityRepository {

    Optional<TraineeEntity> findByUsername(String usernaem);

    Optional<TraineeEntity> findById(Long id);

    TraineeEntity save(TraineeEntity trainee);

    void deleteById(Long id);
}
