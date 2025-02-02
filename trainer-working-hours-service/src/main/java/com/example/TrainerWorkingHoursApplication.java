package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
@SpringBootApplication
public class TrainerWorkingHoursApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainerWorkingHoursApplication.class, args);
    }
}