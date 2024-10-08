package org.example.repository.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.example.exception.TrainingNotFoundException;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainingEntityStorageImplTest {

    private TrainingStorageImpl testSubject;

    @Mock
    private FileStorage<TrainingEntity> trainingFileStorage;

    @Mock
    private DateConverter dateConverter;

    @BeforeEach
    public void setUp() {
        Map<Long, TrainingEntity> map = new HashMap<>();
        map.put(1L, new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
        testSubject = new TrainingStorageImpl(map);
        testSubject.setTrainingFileStorage(trainingFileStorage);
    }

    @Test
    public void testGetTrainingWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetTraining() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
    }

    @Test
    public void testAdd() {
        Assertions.assertThatThrownBy(() -> testSubject.get(2L))
                .isExactlyInstanceOf(TrainingNotFoundException.class);
        testSubject.add(new TrainingEntity(
                2L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
        Assertions.assertThat(testSubject.get(2L)).isEqualTo(new TrainingEntity(
                2L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
    }

    @Test
    public void testRemove() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
        testSubject.remove(1L);
        Assertions.assertThatThrownBy(() -> testSubject.get(1L))
                .isExactlyInstanceOf(TrainingNotFoundException.class);
    }

    @Test
    public void testUpdate() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
        testSubject.update(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.WEIGHTLIFTING,
                Date.valueOf("2024-10-10"),
                1000L
        ));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.WEIGHTLIFTING,
                Date.valueOf("2024-10-10"),
                1000L
        ));
    }
}