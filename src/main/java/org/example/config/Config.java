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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;

@PropertySource("classpath:application.properties")
@Configuration
public class Config {

    @Bean
    public DatabasePathService databasePathService() {
        return new DatabasePathServiceImpl();
    }

    @Bean
    public DateConverter dateConverter() {
        return new DateConverter(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @Bean
    public TrainingStorage trainingStorage() {
        return new TrainingStorageImpl();
    }
//
    @Bean
    public TraineeStorage traineeStorage() {
        return new TraineeStorageImpl();
    }

    @Bean
    public TrainerStorage trainerStorage() {
        return new TrainerStorageImpl();
    }

    @Bean
    public FileStorage<TraineeEntity> traineeFileStorage() {
        return new TraineeFileStorageImpl();
    }

    @Bean
    public FileStorage<TrainerEntity> trainerFileStorage() {
        return new TrainerFileStorageImpl();
    }

    @Bean
    public FileStorage<TrainingEntity> trainingFileStorage() {
        return new TrainingFileStorageImpl();
    }

    @Bean
    public TrainingDao trainingDao() {
        return new TrainingDaoImpl();
    }

    @Bean
    public TraineeDao traineeDao() {
        return new TraineeDaoImpl();
    }

    @Bean
    public TrainerDao trainerDao() {
        return new TrainerDaoImpl();
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
        return new TraineeFacadeImpl(traineeService(), trainerService(), idService(databasePathService().getTraineeIdPath()), traineeUsernamePasswordService());
    }

    @Bean
    public TrainerFacade trainerFacade() {
        return new TrainerFacadeImpl(trainerService(), traineeService(), idService(databasePathService().getTrainerIdPath()), trainerUsernamePasswordService());
    }

    @Bean
    public TrainingFacade trainingFacade() {
        return new TrainingFacadeImpl(trainingService(), idService(databasePathService().getTrainingIdPath()), traineeService(), trainerService());
    }

    @Bean
    @Scope("prototype")
    public IdService idService(String filePath) {
        return new IdServiceImpl(filePath);
    }

    @Bean
    public UsernamePasswordService traineeUsernamePasswordService() {
        return new TraineeUsernamePasswordServiceImpl(traineeService(), trainerService());
    }

    @Bean
    public UsernamePasswordService trainerUsernamePasswordService() {
        return new TrainerUsernamePasswordServiceImpl(traineeService(), trainerService());
    }
}
