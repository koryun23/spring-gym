package org.example.entity;

import org.assertj.core.api.Assertions;
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