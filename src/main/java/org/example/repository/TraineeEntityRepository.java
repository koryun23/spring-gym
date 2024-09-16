package org.example.repository;

import org.example.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeEntityRepository extends JpaRepository<TraineeEntity, Long> {
}
