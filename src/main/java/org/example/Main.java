package org.example;

import org.example.entity.*;
import org.example.helper.DateConverter;
import org.example.repository.impl.TraineeStorageImpl;
import org.example.repository.impl.TrainerStorageImpl;
import org.example.repository.impl.TrainingStorageImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class, args);
        TraineeStorageImpl traineeStorage = context.getBean(TraineeStorageImpl.class);
        TrainerStorageImpl trainerStorage = context.getBean(TrainerStorageImpl.class);
        TrainingStorageImpl trainingStorage = context.getBean(TrainingStorageImpl.class);
        DateConverter dateConverter = (DateConverter) context.getBean("dateConverter");

        trainingStorage.add(new Training(
            1L,
                1L,
                1L,
                "first training",
                TrainingType.AEROBIC,
                dateConverter.stringToDate("2024-08-27"),
                1_000_000L
        ));

        trainingStorage.add(new Training(
                2L,
                1L,
                1L,
                "second training",
                TrainingType.FLEXIBILITY_TRAINING,
                dateConverter.stringToDate("2024-08-24"),
                1_000_000L
        ));

        trainingStorage.update(new Training(
                2L,
                1L,
                1L,
                "second training",
                TrainingType.FLEXIBILITY_TRAINING,
                dateConverter.stringToDate("2024-08-20"),
                1_000_000L
        ));

        trainingStorage.remove(2L);
    }
}