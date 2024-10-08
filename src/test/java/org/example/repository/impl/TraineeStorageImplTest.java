package org.example.repository.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.example.entity.TraineeEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TraineeStorageImplTest {

    private TraineeStorageImpl testSubject;

    @Mock
    private FileStorage<TraineeEntity> traineeFileStorage;

    @Mock
    private DateConverter dateConverter;

    @BeforeEach
    public void setUp() {
        Map<Long, TraineeEntity> map = new HashMap<>();
        map.put(
                1L, new TraineeEntity(
                        1L,
                        "first",
                        "last",
                        "username",
                        "password",
                        true,
                        Date.valueOf("2024-10-10"),
                        "manchester"
                )
        );

        testSubject = new TraineeStorageImpl(map);
        testSubject.setTraineeFileStorage(traineeFileStorage);
    }

    @Test
    public void testTraineeGetByIdWhenExists() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
    }

    @Test
    public void testTraineeGetByUsernameWhenExists() {
        Assertions.assertThat(testSubject.getByUsername("username")).isEqualTo(new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
    }

    @Test
    public void testTraineeGetByIdWhenDoesNotExist() {
        Assertions.assertThatThrownBy(() -> testSubject.get(2L))
                .isExactlyInstanceOf(TraineeNotFoundException.class)
                .hasMessageContaining("2");
    }

    @Test
    public void testTraineeGetByUsernameWhenDoesNotExist() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername("asdf"))
                .isExactlyInstanceOf(TraineeNotFoundException.class)
                .hasMessageContaining("asdf");
    }

    @Test
    public void testTraineeGetByIdWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testTraineeGetByUsernameWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testTraineeAdd() {

        HashMap<Long, TraineeEntity> map = new HashMap<>();
        map.put(1L, new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
        map.put(2L, new TraineeEntity(
                2L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
        Assertions.assertThatThrownBy(() -> testSubject.get(2L))
                .isExactlyInstanceOf(TraineeNotFoundException.class);

        testSubject.add(new TraineeEntity(
                2L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));

        Assertions.assertThat(testSubject.get(2L)).isEqualTo(new TraineeEntity(
                2L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
    }

    @Test
    public void testTraineeAddWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.add(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testTraineeRemove() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TraineeEntity(
                        1L,
                        "first",
                        "last",
                        "username",
                        "password",
                        true,
                        Date.valueOf("2024-10-10"),
                        "manchester"
        ));
        testSubject.remove(1L);
        Assertions.assertThatThrownBy(() -> testSubject.get(1L))
                .isExactlyInstanceOf(TraineeNotFoundException.class);
    }

    @Test
    public void testTraineeRemoveWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.remove(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testTraineeUpdate() {
        HashMap<Long, TraineeEntity> map = new HashMap<>();
        map.put(1L, new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));

        testSubject.update(new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));

        Assertions.assertThat(testSubject.get(1L)).isNotEqualTo(new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
    }

    @Test
    public void testTraineeUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
                .isExactlyInstanceOf((IllegalArgumentException.class));
    }
}