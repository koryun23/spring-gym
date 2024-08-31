package org.example.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;

class TraineeTest {

    @Test
    public void TestTraineeEquals() {

        Trainee trainee1 = new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2002-10-10"),
                "manchester"
        );

        Trainee trainee2 = new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2002-10-10"),
                "manchester"
        );

        Assertions.assertThat(trainee1).isEqualTo(trainee2);
    }

    @Test
    public void TestTraineeHashCode() {
        Trainee trainee1 = new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2002-10-10"),
                "manchester"
        );

        Trainee trainee2 = new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2002-10-10"),
                "manchester"
        );

        Assertions.assertThat(trainee1.hashCode()).isEqualTo(trainee2.hashCode());
    }
}