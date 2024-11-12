package org.example.service.core.trainer;

import java.util.List;
import java.util.Optional;
import org.example.dto.plain.TrainerDto;
import org.example.entity.TrainerEntity;

public interface TrainerService {

    TrainerEntity create(TrainerDto params);

    TrainerEntity update(TrainerEntity params);

    TrainerEntity select(Long trainerId);

    TrainerEntity selectByUsername(String username);

    Optional<TrainerEntity> findById(Long id);

    Optional<TrainerEntity> findByUsername(String username);

    List<TrainerEntity> findAllNotAssignedTo(String traineeUsername);
}
