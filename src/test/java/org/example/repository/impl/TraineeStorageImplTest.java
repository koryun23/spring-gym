package org.example.repository.impl;

import org.assertj.core.api.Assertions;
import org.example.entity.Trainee;
import org.example.exception.TraineeNotFoundException;
import org.example.helper.DateConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


class TraineeStorageImplTest {

    private Map<Long, Trainee> map;
    private TraineeStorageImpl traineeStorage;

    @BeforeEach
    public void setUp() {
        map = new HashMap<>();
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
        traineeStorage = new TraineeStorageImpl(map);
        traineeStorage.setDateConverter(new DateConverter(new SimpleDateFormat("yyyy-MM-dd")));
    }
    @Test
    public void TestTraineeGetByIdWhenExists() {
        Assertions.assertThat(traineeStorage.get(1L)).isEqualTo(new Trainee(
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
        Assertions.assertThat(traineeStorage.getByUsername("username")).isEqualTo(new Trainee(
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
        Assertions.assertThatThrownBy(() -> traineeStorage.get(2L))
                .isExactlyInstanceOf(TraineeNotFoundException.class)
                .hasMessageContaining("2");
    }

    @Test
    public void TestTraineeGetByUsernameWhenDoesNotExist() {
        Assertions.assertThatThrownBy(() -> traineeStorage.getByUsername("asdf"))
                .isExactlyInstanceOf(TraineeNotFoundException.class)
                .hasMessageContaining("asdf");
    }

    @Test
    public void TestTraineeGetByIdWhenNull() {
        Assertions.assertThatThrownBy(() -> traineeStorage.get(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void TestTraineeGetByUsernameWhenNull() {
        Assertions.assertThatThrownBy(() -> traineeStorage.getByUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void TestTraineeAdd() {

        Assertions.assertThatThrownBy(() -> traineeStorage.get(2L))
                .isExactlyInstanceOf(TraineeNotFoundException.class);

        traineeStorage.add(new Trainee(
                2L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));

        Assertions.assertThat(traineeStorage.get(2L)).isEqualTo(new Trainee(
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
        Assertions.assertThatThrownBy(() -> traineeStorage.add(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void TestTraineeRemove() {
        Assertions.assertThat(map.size()).isEqualTo(1);
        Assertions.assertThat(traineeStorage.get(1L)).isEqualTo(new Trainee(
                        1L,
                        "first",
                        "last",
                        "username",
                        "password",
                        true,
                        Date.valueOf("2024-10-10"),
                        "manchester"
        ));
        traineeStorage.remove(1L);
        Assertions.assertThat(map.size()).isZero();
    }

    @Test
    public void TestTraineeRemoveWhenNull() {
        Assertions.assertThatThrownBy(() -> traineeStorage.remove(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void TestTraineeUpdate() {
        Assertions.assertThat(map.size()).isOne();
        Assertions.assertThat(traineeStorage.get(1L)).isEqualTo(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));

        traineeStorage.update(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));

        Assertions.assertThat(map.size()).isOne();
        Assertions.assertThat(traineeStorage.get(1L)).isNotEqualTo(new Trainee(
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
        Assertions.assertThatThrownBy(() -> traineeStorage.update(null))
                .isExactlyInstanceOf((IllegalArgumentException.class));
    }
}