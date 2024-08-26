package org.example;


import org.example.entity.Trainee;
import org.example.repository.core.Storage;
import org.example.repository.impl.TraineeStorageImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        ApplicationContext context = SpringApplication.run(Main.class, args);
        TraineeStorageImpl traineeStorage = context.getBean(TraineeStorageImpl.class);
        Date bday1 = null;
        try {
            bday1 = formatter.parse("2024-10-22");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        traineeStorage.parseMemoryFile();
        traineeStorage.add(new Trainee(
                2L,
                "kevin",
                "de bruyne",
                "kevin17",
                "assist",
                true,
                bday1,
                "manchester"
        ));
        System.out.println(traineeStorage.get(1L));
    }
}