package org.example;

import java.sql.Date;
import java.util.Optional;
import org.example.config.Config;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.UserEntity;
import org.example.facade.core.TraineeFacade;
import org.example.facade.core.TrainerFacade;
import org.example.facade.impl.TraineeFacadeImpl;
import org.example.facade.impl.TrainingFacadeImpl;
import org.example.repository.core.UserEntityRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    /**
     * Main method.
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        TrainerFacade trainerFacade = context.getBean(TrainerFacade.class);
        TraineeFacade traineeFacade = context.getBean(TraineeFacade.class);
        TrainingFacadeImpl trainingFacade = context.getBean(TrainingFacadeImpl.class);
//        trainingFacade.createTraining(new TrainingCreationRequestDto(
//            1L, 2L, "another training", 1L, Date.valueOf("2024-10-10"), 10000L
//        ));
        trainingFacade.deleteMultpileTraineeTraining("first.last.3");
    }
}