package org.example.repository;

import java.util.Optional;
import org.example.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeEntityRepository extends JpaRepository<TraineeEntity, Long> {

    Optional<TraineeEntity> findByUsername(String usernaem);
}
