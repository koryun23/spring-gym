package org.example.service.core;

import org.example.entity.Trainee;

import java.util.Optional;

public interface TraineeService {

    Trainee create(Trainee params);

    Trainee update(Trainee params);

    boolean delete(Long traineeId);

    Trainee select(Long traineeId);

    Trainee selectByUsername(String username);

    Optional<Trainee> findById(Long id);

    Optional<Trainee> findByUsername(String username);
}
