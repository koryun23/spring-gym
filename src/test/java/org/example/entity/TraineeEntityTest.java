package org.example.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;

class TraineeEntityTest {

    @Test
    public void TestTraineeEquals() {

        TraineeEntity trainee1 = new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2002-10-10"),
                "manchester"
        );

        TraineeEntity trainee2 = new TraineeEntity(
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
        TraineeEntity trainee1 = new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2002-10-10"),
                "manchester"
        );

        TraineeEntity trainee2 = new TraineeEntity(
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