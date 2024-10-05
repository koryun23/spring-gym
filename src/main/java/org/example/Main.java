package org.example;

import java.sql.Date;
import org.example.config.Config;
import org.example.dto.request.MultipleTrainingDeletionByTraineeRequestDto;
import org.example.dto.request.MultipleTrainingDeletionByTrainerRequestDto;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByIdRequestDto;
import org.example.dto.request.TraineePasswordChangeRequestDto;
import org.example.dto.request.TraineeRetrievalByIdRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerPasswordChangeRequestDto;
import org.example.dto.request.TrainerRetrievalByIdRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.request.TrainingRetrievalByIdRequestDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.facade.core.TraineeFacade;
import org.example.facade.core.TrainerFacade;
import org.example.facade.core.TrainingFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    /**
     * Main method.
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        TraineeFacade traineeFacade = context.getBean(TraineeFacade.class);
        // TRAINEE FACADE SCENARIO
        traineeFacade.createTrainee(new TraineeCreationRequestDto(
            "first", "last", Date.valueOf("2024-10-10"), "address"
        ));
        traineeFacade.createTrainee(new TraineeCreationRequestDto(
            "first", "last", Date.valueOf("2024-10-10"), "address"
        ));

        traineeFacade.changePassword(
            new TraineePasswordChangeRequestDto("first.last.4", "872411bd-2", 1L, "password-4"));
        traineeFacade.switchActivationState(new TraineeSwitchActivationStateRequestDto(
            "first.last", "password-4", 2L
        ));
        traineeFacade.updateTrainee(new TraineeUpdateRequestDto(
            "john", "doe", "john.doe", true, Date.valueOf("2024-10-10"),
            "address"
        ));
        traineeFacade.createTrainee(new TraineeCreationRequestDto(
            "first", "last", Date.valueOf("2024-10-10"), "address"
        ));

        TrainerFacade trainerFacade = context.getBean(TrainerFacade.class);
        // TRAINER FACADE SCENARIO
        trainerFacade.createTrainer(new TrainerCreationRequestDto(
            "travis", "stones", 1L
        ));
        trainerFacade.createTrainer(new TrainerCreationRequestDto(
            "travis", "stones", 1L
        ));
        trainerFacade.switchActivationState(new TrainerSwitchActivationStateRequestDto(
            "travis.stones", "c355c787-1", "travis.stones"
        ));
        trainerFacade.changePassword(new TrainerPasswordChangeRequestDto(
            "travis.stones", "c355c787-1", 1L, "password-1"
        ));
        System.out.println(trainerFacade.retrieveTrainerByUsername(new TrainerRetrievalByUsernameRequestDto(
            "travis.stones", "password-1", "travis.stones"
        )));
        System.out.println(trainerFacade.retrieveTrainer(new TrainerRetrievalByIdRequestDto(
            "travis.stones", "password-1", 1L
        )));
        trainerFacade.updateTrainer(new TrainerUpdateRequestDto(
            "travis.stones", "password-1", 1L, "travis", "stones", "travis.stones", "password-1", true, 1L
        ));

        TrainingFacade trainingFacade = context.getBean(TrainingFacade.class);
        // TRAINING FACADE SCENARIO
        trainingFacade.createTraining(new TrainingCreationRequestDto(
            "travis.stones", "password-1", 1L, 1L, "training", 1L, Date.valueOf("2024-10-10"), 1000L
        ));
        System.out.println(trainingFacade.retrieveTraining(new TrainingRetrievalByIdRequestDto(
            "travis.stones", "password-1", 1L
        )));
        trainingFacade.createTraining(new TrainingCreationRequestDto(
            "travis.stones", "password-1", 1L, 2L, "second training", 1L, Date.valueOf("2024-10-10"), 1000L
        ));
        System.out.println(trainingFacade.retrieveTrainingListByTrainee(new TrainingListRetrievalByTraineeRequestDto(
            "travis.stones", "password-1", "first.last", null, null, null, null
        )));
        System.out.println(trainingFacade.retrieveTrainingListByTrainer(new TrainingListRetrievalByTrainerRequestDto(
            "travis.stones", "password-1", "travis.stones", null, null, null
        )));
        trainingFacade.retrieveTraining(new TrainingRetrievalByIdRequestDto(
            "travis.stones", "password-1", 1L
        ));
        System.out.println(trainingFacade.deleteMultpileTraineeTraining(new MultipleTrainingDeletionByTraineeRequestDto(
            "travis.stones", "password-1", "first.last"
        )));
        trainingFacade.createTraining(new TrainingCreationRequestDto(
            "travis.stones", "password-1", 1L, 1L, "training-1", 1L, Date.valueOf("2024-10-10"), 1000L
        ));
        trainingFacade.createTraining(new TrainingCreationRequestDto(
            "travis.stones", "password-1", 2L, 1L, "training-2", 1L, Date.valueOf("2024-10-10"), 1000L
        ));
        trainingFacade.deleteMultipleTrainerTraining(new MultipleTrainingDeletionByTrainerRequestDto(
            "travis.stones", "password-1", "travis.stones"
        ));
    }
}