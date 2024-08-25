package org.example;


import org.example.entity.Trainee;
import org.example.repository.core.Storage;
import org.example.repository.impl.TraineeStorageImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.Date;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        TraineeStorageImpl traineeStorage = context.getBean(TraineeStorageImpl.class);
        traineeStorage.parseMemoryFile();
        traineeStorage.add(new Trainee(
                2L,
                "kevin",
                "de bruyne",
                "kevin17",
                "assist",
                true,
                Date.valueOf("1987-10-20"),
                "manchester"
        ));
        System.out.println(traineeStorage.get(2L));
    }
}