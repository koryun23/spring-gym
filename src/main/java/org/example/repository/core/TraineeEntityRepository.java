package org.example.repository.core;

import java.util.Optional;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeEntityRepository extends CustomRepository<Long, TraineeEntity> {

    Optional<TraineeEntity> findByUsername(String username);
}
