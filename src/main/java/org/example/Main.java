package org.example;

import org.example.entity.Trainee;
import org.example.helper.DateConverter;
import org.example.repository.impl.TraineeStorageImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class, args);
        DateConverter dateConverter = (DateConverter) context.getBean("dateConverter");
        TraineeStorageImpl traineeStorage = context.getBean(TraineeStorageImpl.class);
        traineeStorage.parseMemoryFile();
        traineeStorage.add(new Trainee(
                1L,
                "John",
                "Sanchez",
                "john3",
                "pwd12",
                true,
                dateConverter.stringToDate("1950-10-19"),
                "nyc"
        ));
    }
}