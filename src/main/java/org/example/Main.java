package org.example;

import org.example.entity.*;
import org.example.helper.DateConverter;
import org.example.repository.impl.TraineeStorageImpl;
import org.example.repository.impl.TrainerStorageImpl;
import org.example.repository.impl.TrainingStorageImpl;
import org.example.service.core.TraineeService;
import org.example.service.impl.TraineeServiceImpl;
import org.example.service.params.TraineeCreateParams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class, args);
        DateConverter dateConverter = (DateConverter) context.getBean("dateConverter");

        TraineeService traineeService = context.getBean(TraineeServiceImpl.class);

        traineeService.create(new TraineeCreateParams(
                1L,
                "Jack",
                "Murray",
                "jack7",
                "pwd123",
                true,
                dateConverter.stringToDate("2024-10-10"),
                "london"
        ));
    }
}