package org.example.config;

import org.example.dao.impl.TraineeDaoImpl;
import org.example.dao.impl.TrainerDaoImpl;
import org.example.dao.impl.TrainingDaoImpl;
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
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.example.service.impl.IdServiceImpl;
import org.example.service.impl.TraineeServiceImpl;
import org.example.service.impl.TrainerServiceImpl;
import org.example.service.impl.TrainingServiceImpl;
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
    public TrainingDaoImpl trainingDao() {
        return new TrainingDaoImpl();
    }

    @Bean
    public TraineeDaoImpl traineeDao() {
        return new TraineeDaoImpl();
    }

    @Bean
    public TrainerDaoImpl trainerDao() {
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
        return new TraineeFacadeImpl(traineeService(), trainerService(), idService(TRAINEE_ID_PATH));
    }

    @Bean
    public TrainerFacade trainerFacade() {
        return new TrainerFacadeImpl(trainerService(), traineeService(), idService(TRAINER_ID_PATH));
    }

    @Bean
    public TrainingFacade trainingFacade() {
        return new TrainingFacadeImpl(new TrainingServiceImpl(), new IdServiceImpl(TRAINING_ID_PATH));
    }

    @Bean
    @Scope("prototype")
    public IdService idService(String filePath) {
        return new IdServiceImpl(filePath);
    }
}
