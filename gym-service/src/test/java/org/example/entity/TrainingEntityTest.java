package org.example.entity;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingEntity;
import org.example.entity.training.TrainingType;
import org.example.entity.training.TrainingTypeEntity;
import org.example.entity.user.UserEntity;
import org.junit.jupiter.api.Test;

class TrainingEntityTest {

    @Test
    public void testTrainingEquals() {
        TrainingEntity trainingEntity1 = new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        );

        TrainingEntity trainingEntity2 = new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        );

        Assertions.assertThat(trainingEntity1).isEqualTo(trainingEntity2);
    }

    @Test
    public void testTrainingHashCode() {
        TrainingEntity trainingEntity1 = new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        );

        TrainingEntity trainingEntity2 = new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        );

        Assertions.assertThat(trainingEntity1.hashCode()).isEqualTo(trainingEntity2.hashCode());

    }
}