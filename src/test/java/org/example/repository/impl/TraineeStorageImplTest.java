package org.example.repository.impl;

import org.assertj.core.api.Assertions;
import org.example.entity.Trainee;
import org.example.exception.TraineeNotFoundException;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class TraineeStorageImplTest {

    private TraineeStorageImpl testSubject;

    @Mock
    private FileStorage<Trainee> traineeFileStorage;

    @Mock
    private DateConverter dateConverter;

    @BeforeEach
    public void setUp() {
        Map<Long, Trainee> map = new HashMap<>();
        map.put(
                1L, new Trainee(
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
    public void TestTraineeGetByIdWhenExists() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new Trainee(
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
    public void TestTraineeGetByUsernameWhenExists() {
        Assertions.assertThat(testSubject.getByUsername("username")).isEqualTo(new Trainee(
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
    public void TestTraineeGetByIdWhenDoesNotExist() {
        Assertions.assertThatThrownBy(() -> testSubject.get(2L))
                .isExactlyInstanceOf(TraineeNotFoundException.class)
                .hasMessageContaining("2");
    }

    @Test
    public void TestTraineeGetByUsernameWhenDoesNotExist() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername("asdf"))
                .isExactlyInstanceOf(TraineeNotFoundException.class)
                .hasMessageContaining("asdf");
    }

    @Test
    public void TestTraineeGetByIdWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void TestTraineeGetByUsernameWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void TestTraineeAdd() {

        HashMap<Long, Trainee> map = new HashMap<>();
        map.put(1L, new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
        map.put(2L, new Trainee(
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

        testSubject.add(new Trainee(
                2L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));

        Assertions.assertThat(testSubject.get(2L)).isEqualTo(new Trainee(
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
    public void TestTraineeAddWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.add(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void TestTraineeRemove() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new Trainee(
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
    public void TestTraineeRemoveWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.remove(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void TestTraineeUpdate() {
        HashMap<Long, Trainee> map = new HashMap<>();
        map.put(1L, new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));

        testSubject.update(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));

        Assertions.assertThat(testSubject.get(1L)).isNotEqualTo(new Trainee(
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
    public void TestTraineeUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
                .isExactlyInstanceOf((IllegalArgumentException.class));
    }
}