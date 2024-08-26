package org.example;

import org.example.entity.Trainee;
import org.example.entity.Trainer;
import org.example.helper.DateConverter;
import org.example.repository.impl.TraineeStorageImpl;
import org.example.repository.impl.TrainerStorageImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class, args);
        TraineeStorageImpl traineeStorage = context.getBean(TraineeStorageImpl.class);
        TrainerStorageImpl trainerStorage = context.getBean(TrainerStorageImpl.class);
    }
}