package org.example.repository.core;

import java.util.Optional;
import org.example.entity.TrainingEntity;
import org.springframework.stereotype.Repository;

public interface TrainingEntityRepository {

    Optional<TrainingEntity> findById(Long id);

    TrainingEntity save(TrainingEntity training);

    void deleteById(Long id);
}
