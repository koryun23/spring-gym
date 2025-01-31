package org.example.entity;

import org.assertj.core.api.Assertions;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingType;
import org.example.entity.training.TrainingTypeEntity;
import org.example.entity.user.UserEntity;
import org.junit.jupiter.api.Test;

class TrainerEntityTest {

    @Test
    public void testTrainerEquals() {
        TrainerEntity trainerEntity1 = new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        );

        TrainerEntity trainerEntity2 = new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        );

        Assertions.assertThat(trainerEntity1).isEqualTo(trainerEntity2);
    }

    @Test
    public void testTrainerHashCode() {
        TrainerEntity trainerEntity1 = new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        );

        TrainerEntity trainerEntity2 = new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        );

        Assertions.assertThat(trainerEntity1.hashCode()).isEqualTo(trainerEntity2.hashCode());
    }
}