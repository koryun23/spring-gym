package org.example.mapper.training;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingEntityToTrainingRetrievalResponseDtoMapperImplTest {

    private TrainingEntityToTrainingRetrievalResponseDtoMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainingEntityToTrainingRetrievalResponseDtoMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        TraineeEntity trainee = new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        );
        TrainingTypeEntity trainingType = new TrainingTypeEntity(TrainingType.WEIGHTLIFTING);
        TrainerEntity trainer = new TrainerEntity(
            new UserEntity("f", "l", "u", "p", true),
            trainingType
        );
        trainee.setId(1L);
        trainer.setId(1L);
        trainingType.setId(1L);
        TrainingEntity training = new TrainingEntity(
            trainee, trainer, "training", trainingType, Date.valueOf("2024-10-10"), 1000L
        );
        training.setId(1L);
        Assertions.assertThat(testSubject.map(training)).isEqualTo(new TrainingRetrievalResponseDto(
            1L,
            1L,
            1L,
            "training",
            1L,
            Date.valueOf("2024-10-10"),
            1000L
        ));
    }

}