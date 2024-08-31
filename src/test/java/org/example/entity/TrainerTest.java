package org.example.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TrainerTest {

    @Test
    public void TestTrainerEquals() {
        Trainer trainer1 = new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        );

        Trainer trainer2 = new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        );

        Assertions.assertThat(trainer1).isEqualTo(trainer2);
    }

    @Test
    public void TestTrainerHashCode() {
        Trainer trainer1 = new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        );

        Trainer trainer2 = new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        );

        Assertions.assertThat(trainer1.hashCode()).isEqualTo(trainer2.hashCode());
    }
}