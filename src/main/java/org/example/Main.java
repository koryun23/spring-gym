package org.example;

import java.sql.Date;
import java.util.Optional;
import org.example.config.Config;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.entity.UserEntity;
import org.example.facade.core.TraineeFacade;
import org.example.facade.impl.TraineeFacadeImpl;
import org.example.repository.core.UserEntityRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    /**
     * Main method.
     *
     * @param args Runtime Arguments.
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        TraineeFacade traineeFacade = context.getBean(TraineeFacade.class);
        /*traineeFacade.createTrainee(new TraineeCreationRequestDto(
            "jack", "grealish", true, Date.valueOf("2024-10-10"), "manchester"
        ));

        traineeFacade.updateTrainee(new TraineeUpdateRequestDto(
            1L, "jack", "grealish", "jack.grealish", "updated password", false, Date.valueOf("2024-10-10"), "madrid"
        ));*/
        System.out.println(traineeFacade.retrieveTrainee("asdfadfs"));
    }
}