package org.example.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;

class TrainingTest {

    @Test
    public void TestTrainingEquals() {
        Training training1 = new Training(
                1L,
                1L,
                1L,
                "training1",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        );

        Training training2 = new Training(
                1L,
                1L,
                1L,
                "training1",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        );

        Assertions.assertThat(training1).isEqualTo(training2);
    }

    @Test
    public void TestTrainingHashCode() {
        Training training1 = new Training(
                1L,
                1L,
                1L,
                "training1",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        );

        Training training2 = new Training(
                1L,
                1L,
                1L,
                "training1",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        );

        Assertions.assertThat(training1.hashCode()).isEqualTo(training2.hashCode());

    }
}