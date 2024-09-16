package org.example.dao.impl;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.example.exception.TrainingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TrainingEntityDaoImplTest {

    private TrainingDaoImpl testSubject;

    @Mock
    private TrainingStorageImpl trainingStorage;

    @BeforeEach
    public void init() {
        testSubject = new TrainingDaoImpl();
        testSubject.setStorage(trainingStorage);
    }

    @Test
    public void testGetWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGet() {
        Mockito.when(trainingStorage.get(1L)).thenReturn(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
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
    public void testSaveWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.save(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSave() {
        testSubject.save(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
        Mockito.when(trainingStorage.get(1L)).thenReturn(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
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
    public void testUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdate() {
        Mockito.when(trainingStorage.get(1L)).thenReturn(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
        TrainingEntity initialTrainingEntity = testSubject.get(1L);
        Mockito.when(trainingStorage.get(1L)).thenReturn(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                2000L
        ));
        testSubject.update(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                2000L
        ));
        Assertions.assertThat(testSubject.get(1L)).isNotEqualTo(initialTrainingEntity);
    }

    @Test
    public void testDeleteWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.delete(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDelete() {
        testSubject.save(new TrainingEntity(
                1L,
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        ));
        Mockito.when(trainingStorage.remove(1L)).thenReturn(true);
        testSubject.delete(1L);
        Mockito.when(trainingStorage.get(1L)).thenThrow(TrainingNotFoundException.class);
        Assertions.assertThatThrownBy(() -> testSubject.get(1L))
                .isExactlyInstanceOf(TrainingNotFoundException.class);
    }
}