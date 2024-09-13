package org.example.entity;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TrainingEntityTest {

    @Test
    public void testTrainingEquals() {
        TrainingEntity trainingEntity1 = new TrainingEntity(
                1L,
                1L,
                1L,
                "trainingEntity1",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        );

        TrainingEntity trainingEntity2 = new TrainingEntity(
                1L,
                1L,
                1L,
                "trainingEntity1",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        );

        Assertions.assertThat(trainingEntity1).isEqualTo(trainingEntity2);
    }

    @Test
    public void testTrainingHashCode() {
        TrainingEntity trainingEntity1 = new TrainingEntity(
                1L,
                1L,
                1L,
                "trainingEntity1",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        );

        TrainingEntity trainingEntity2 = new TrainingEntity(
                1L,
                1L,
                1L,
                "trainingEntity1",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        );

        Assertions.assertThat(trainingEntity1.hashCode()).isEqualTo(trainingEntity2.hashCode());

    }
}