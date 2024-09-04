package org.example.config;

import org.example.dao.core.TraineeDao;
import org.example.dao.core.TrainerDao;
import org.example.dao.core.TrainingDao;
import org.example.dao.impl.TraineeDaoImpl;
import org.example.dao.impl.TrainerDaoImpl;
import org.example.dao.impl.TrainingDaoImpl;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.facade.core.TraineeFacade;
import org.example.facade.core.TrainerFacade;
import org.example.facade.core.TrainingFacade;
import org.example.facade.impl.TraineeFacadeImpl;
import org.example.facade.impl.TrainerFacadeImpl;
import org.example.facade.impl.TrainingFacadeImpl;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.example.repository.core.TraineeStorage;
import org.example.repository.core.TrainerStorage;
import org.example.repository.core.TrainingStorage;
import org.example.repository.impl.*;
import org.example.service.core.*;
import org.example.service.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

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

    @Bean
    public DateConverter dateConverter() {
        return new DateConverter(new SimpleDateFormat("yyyy-MM-dd"));
    }
}
