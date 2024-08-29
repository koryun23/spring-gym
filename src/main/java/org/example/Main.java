package org.example;

import org.example.config.Config;
import org.example.helper.DateConverter;
import org.example.service.core.TraineeService;
import org.example.service.impl.TraineeServiceImpl;
import org.example.service.params.TraineeCreateParams;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Config.class);
        context.refresh();

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