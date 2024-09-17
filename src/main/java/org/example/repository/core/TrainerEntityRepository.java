package org.example.repository.core;

import java.util.Optional;
import org.example.entity.TrainerEntity;

public interface TrainerEntityRepository {

    Optional<TrainerEntity> findByUsername(String username);

    Optional<TrainerEntity> findById(Long id);

    TrainerEntity save(TrainerEntity trainer);

    void deleteById(Long id);
}
