package org.example.entity;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TraineeEntityTest {

    @Test
    public void testTraineeEquals() {

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
    public void testTraineeHashCode() {
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