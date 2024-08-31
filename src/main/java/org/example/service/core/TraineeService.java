package org.example.service.core;

import org.example.entity.Trainee;
import org.example.service.params.TraineeCreateParams;
import org.example.service.params.TraineeUpdateParams;

import java.util.Optional;

public interface TraineeService {

    Trainee create(TraineeCreateParams params);

    Trainee update(TraineeUpdateParams params);

    boolean delete(Long traineeId);

    Trainee select(Long traineeId);

    Trainee selectByUsername(String username);

    Optional<Trainee> findById(Long id);

    Optional<Trainee> findByUsername(String username);
}
