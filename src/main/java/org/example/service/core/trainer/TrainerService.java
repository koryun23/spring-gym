package org.example.service.core.trainer;

import java.util.List;
import java.util.Optional;
import org.example.dto.plain.TrainerDto;
import org.example.entity.trainer.TrainerEntity;

public interface TrainerService {

    TrainerDto create(TrainerDto params);

    TrainerEntity update(TrainerDto params);

    TrainerEntity select(Long trainerId);

    TrainerEntity selectByUsername(String username);

    Optional<TrainerEntity> findById(Long id);

    Optional<TrainerEntity> findByUsername(String username);

    List<TrainerEntity> findAllNotAssignedTo(String traineeUsername);
}
