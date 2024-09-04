package org.example.repository.impl;

import org.assertj.core.api.Assertions;
import org.example.entity.SpecializationType;
import org.example.entity.TrainerEntity;
import org.example.exception.TrainerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TrainerEntityStorageImplTest {

    private TrainerStorageImpl testSubject;

    @Mock
    private TrainerFileStorageImpl trainerFileStorage;

    @BeforeEach
    public void setUp() {
        Map<Long, TrainerEntity> map = new HashMap<>();
        map.put(1L, new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));

        testSubject = new TrainerStorageImpl(map);
        testSubject.setTrainerFileStorage(trainerFileStorage);
    }

    @Test
    public void testGetWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    public void testGetWhenExists() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
    }

    @Test
    public void testAddWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.add(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    public void testAdd() {
        Assertions.assertThatThrownBy(() -> testSubject.get(2L))
                .isExactlyInstanceOf(TrainerNotFoundException.class);
        testSubject.add(new TrainerEntity(
                2L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
        Assertions.assertThat(testSubject.get(2L)).isEqualTo(new TrainerEntity(
                2L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
    }

    @Test
    public void testRemoveWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.remove(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testRemoveWhenExists() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
        testSubject.remove(1L);
        Assertions.assertThatThrownBy(() -> testSubject.get(1L))
                .isExactlyInstanceOf(TrainerNotFoundException.class);
    }

    @Test
    public void testUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateWhenExists() {
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
        testSubject.update(new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                SpecializationType.FITNESS
        ));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                SpecializationType.FITNESS
        ));
    }

    @Test
    public void testGetByUsernameWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByUsernameWhenDoesNotExist() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername("asdf"))
                .isExactlyInstanceOf(TrainerNotFoundException.class);
    }

    @Test
    public void testGetByUsernameWhenExists() {
        Assertions.assertThat(testSubject.getByUsername("username")).isEqualTo(new TrainerEntity(
                        1L,
                        "first",
                        "last",
                        "username",
                        "password",
                        true,
                        SpecializationType.FITNESS
        ));
    }

    @Test
    public void testFindByUsername() {
        Assertions.assertThat(testSubject.findByUsername("username")).isEqualTo(Optional.of(new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        )));
    }

    @Test
    void testFindById() {
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        )));
    }
}