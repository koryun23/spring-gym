package org.example.repository.core;

import java.util.Optional;
import org.example.entity.TrainingTypeEntity;

public interface TrainingTypeEntityRepository {

    Optional<TrainingTypeEntity> findById(Long id);

    TrainingTypeEntity save(TrainingTypeEntity trainingTypeEntity);

    void deleteById(Long id);
}
