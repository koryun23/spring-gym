package org.example.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TrainerEntityTest {

    @Test
    public void TestTrainerEquals() {
        TrainerEntity trainerEntity1 = new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        );

        TrainerEntity trainerEntity2 = new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        );

        Assertions.assertThat(trainerEntity1).isEqualTo(trainerEntity2);
    }

    @Test
    public void TestTrainerHashCode() {
        TrainerEntity trainerEntity1 = new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        );

        TrainerEntity trainerEntity2 = new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        );

        Assertions.assertThat(trainerEntity1.hashCode()).isEqualTo(trainerEntity2.hashCode());
    }
}