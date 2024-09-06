package org.example.repository.core;

import java.util.Optional;
import org.example.entity.TrainerEntity;

public interface TrainerStorage extends Storage<TrainerEntity> {

    TrainerEntity getByUsername(String username);

    Optional<TrainerEntity> findByUsername(String username);
}
