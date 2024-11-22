package org.example.security;

import org.example.entity.trainee.TraineeEntity;
import org.example.entity.user.UserEntity;
import org.example.service.core.trainee.TraineeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserLoader implements CommandLineRunner {

    private final TraineeService traineeService;
    private final PasswordEncoder passwordEncoder;

    public DefaultUserLoader(TraineeService traineeService, PasswordEncoder passwordEncoder) {
        this.traineeService = traineeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        traineeService.create(new TraineeEntity(
            new UserEntity("Trainee", "Trainee", "Trainee.Trainee", passwordEncoder.encode("password"), true),
            null, null
        ));


    }
}
