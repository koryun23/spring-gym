package org.example.config;

import org.example.dao.core.TraineeDao;
import org.example.dao.core.TrainerDao;
import org.example.dao.core.TrainingDao;
import org.example.dao.impl.TraineeDaoImpl;
import org.example.dao.impl.TrainerDaoImpl;
import org.example.dao.impl.TrainingDaoImpl;
import org.example.entity.Trainee;
import org.example.entity.Trainer;
import org.example.entity.Training;
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
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;

@Configuration
public class Config {

    private static final String TRAINEE_ID_PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\service\\core\\trainee-id.txt";
    private static final String TRAINER_ID_PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\service\\core\\trainer-id.txt";
    private static final String TRAINING_ID_PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\service\\core\\training-id.txt";

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
    public FileStorage<Trainee> traineeFileStorage() {
        return new TraineeFileStorageImpl();
    }

    @Bean
    public FileStorage<Trainer> trainerFileStorage() {
        return new TrainerFileStorageImpl();
    }

    @Bean
    public FileStorage<Training> trainingFileStorage() {
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
        return new TraineeFacadeImpl(traineeService(), trainerService(), idService(TRAINEE_ID_PATH), usernamePasswordService());
    }

    @Bean
    public TrainerFacade trainerFacade() {
        return new TrainerFacadeImpl(trainerService(), traineeService(), idService(TRAINER_ID_PATH), usernamePasswordService());
    }

    @Bean
    public TrainingFacade trainingFacade() {
        return new TrainingFacadeImpl(trainingService(), idService(TRAINING_ID_PATH), traineeService(), trainerService());
    }

    @Bean
    @Scope("prototype")
    public IdService idService(String filePath) {
        return new IdServiceImpl(filePath);
    }

    @Bean
    public UsernamePasswordService usernamePasswordService() {
        return new UsernamePasswordServiceImpl(traineeService(), trainerService());
    }
}
