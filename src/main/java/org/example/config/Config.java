package org.example.config;

import org.example.service.core.DatabasePathService;
import org.example.service.core.IdService;
import org.example.service.impl.DatabasePathServiceImpl;
import org.example.service.impl.IdServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan("org.example")
public class Config {

    @Value("${trainee.path}")
    private String traineePath;

    @Value("${trainee.id.path}")
    private String traineeIdPath;

    @Value("${trainer.path}")
    private String trainerPath;

    @Value("${trainer.id.path}")
    private String trainerIdPath;

    @Value("${training.path}")
    private String trainingPath;

    @Value("${training.id.path}")
    private String trainingIdPath;

    @Bean
    public DatabasePathService traineeDatabasePathService() {
        return new DatabasePathServiceImpl(traineePath, traineeIdPath);
    }

    @Bean
    public DatabasePathService trainerDatabasePathService() {
        return new DatabasePathServiceImpl(trainerPath, trainerIdPath);
    }

    @Bean
    public DatabasePathService trainingDatabasePathService() {
        return new DatabasePathServiceImpl(trainingPath, trainingIdPath);
    }

    @Bean
    public IdService traineeIdService() {
        return new IdServiceImpl(traineeDatabasePathService());
    }

    @Bean
    public IdService trainerIdService() {
        return new IdServiceImpl(trainerDatabasePathService());
    }

    @Bean
    public IdService trainingIdService() {
        return new IdServiceImpl(trainingDatabasePathService());
    }
}
