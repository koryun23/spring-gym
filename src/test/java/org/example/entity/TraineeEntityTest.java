package org.example.entity;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.user.UserEntity;
import org.junit.jupiter.api.Test;

class TraineeEntityTest {

    @Test
    public void testTraineeEquals() {

        TraineeEntity trainee1 = new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        );

        TraineeEntity trainee2 = new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        );

        Assertions.assertThat(trainee1).isEqualTo(trainee2);
    }

    @Test
    public void testTraineeHashCode() {
        TraineeEntity trainee1 = new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        );

        TraineeEntity trainee2 = new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        );

        Assertions.assertThat(trainee1.hashCode()).isEqualTo(trainee2.hashCode());
    }
}