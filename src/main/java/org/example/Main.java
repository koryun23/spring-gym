package org.example;

import org.example.config.Config;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.SpecializationType;
import org.example.entity.TrainingType;
import org.example.facade.core.TraineeFacade;
import org.example.facade.core.TrainerFacade;
import org.example.facade.core.TrainingFacade;
import org.example.helper.DateConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        DateConverter dateConverter = context.getBean(DateConverter.class);
        TraineeFacade traineeFacade = context.getBean(TraineeFacade.class);
        TrainerFacade trainerFacade = context.getBean(TrainerFacade.class);
        TrainingFacade trainingFacade = context.getBean(TrainingFacade.class);

        traineeFacade.createTrainee(new TraineeCreationRequestDto(
            "John",
            "Murray",
            true,
            dateConverter.stringToDate("2024-10-10"),
            "manchester"
        ));

        trainerFacade.createTrainer(new TrainerCreationRequestDto(
            "Jack",
            "Henderson",
            true,
            SpecializationType.FITNESS
        ));

        trainingFacade.createTraining(new TrainingCreationRequestDto(
            1L,
            1L,
            "Aeoribics TrainingEntity",
            TrainingType.AEROBIC,
            dateConverter.stringToDate("2024-12-12"),
            1_000_000L
        ));
    }
}