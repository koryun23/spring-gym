package org.example.config;

import org.example.dao.impl.TraineeDao;
import org.example.dao.impl.TrainerDao;
import org.example.dao.impl.TrainingDao;
import org.example.facade.core.TraineeFacade;
import org.example.facade.core.TrainerFacade;
import org.example.facade.core.TrainingFacade;
import org.example.facade.impl.TraineeFacadeImpl;
import org.example.facade.impl.TrainerFacadeImpl;
import org.example.facade.impl.TrainingFacadeImpl;
import org.example.helper.DateConverter;
import org.example.repository.impl.TraineeStorageImpl;
import org.example.repository.impl.TrainerStorageImpl;
import org.example.repository.impl.TrainingStorageImpl;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.example.service.impl.TraineeServiceImpl;
import org.example.service.impl.TrainerServiceImpl;
import org.example.service.impl.TrainingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class Config {

    @Bean
    public DateConverter dateConverter() {
        return new DateConverter(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @Bean
    public TrainingStorageImpl trainingStorage() {
        return new TrainingStorageImpl();
    }

    @Bean
    public TraineeStorageImpl traineeStorage() {
        return new TraineeStorageImpl();
    }

    @Bean
    public TrainerStorageImpl trainerStorage() {
        return new TrainerStorageImpl();
    }

    @Bean
    public TrainingDao trainingDao() {
        return new TrainingDao();
    }

    @Bean
    public TraineeDao traineeDao() {
        return new TraineeDao();
    }

    @Bean
    public TrainerDao trainerDao() {
        return new TrainerDao();
    }

    @Bean
    public TrainingService trainingService() {
        return new TrainingServiceImpl();
    }

    @Bean
    public TrainerService trainerService() {
        return new TrainerServiceImpl();
    }

    @Bean
    public TraineeService traineeService() {
        return new TraineeServiceImpl();
    }

    @Bean
    public TraineeFacade traineeFacade() {
        return new TraineeFacadeImpl(traineeService());
    }

    @Bean
    public TrainerFacade trainerFacade() {
        return new TrainerFacadeImpl(new TrainerServiceImpl());
    }

    @Bean
    public TrainingFacade trainingFacade() {
        return new TrainingFacadeImpl(new TrainingServiceImpl());
    }

}
