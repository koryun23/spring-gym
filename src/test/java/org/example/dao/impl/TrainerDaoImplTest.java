package org.example.dao.impl;

import org.assertj.core.api.Assertions;
import org.example.dao.core.TrainerDao;
import org.example.entity.SpecializationType;
import org.example.entity.Trainer;
import org.example.exception.TrainerNotFoundException;
import org.example.repository.core.TrainerStorage;
import org.example.repository.impl.TrainerStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrainerDaoImplTest {

    private TrainerDaoImpl testSubject;

    @Mock
    private TrainerStorageImpl trainerStorage;

    @BeforeEach
    public void init() {
        testSubject = new TrainerDaoImpl();
        testSubject.setStorage(trainerStorage);
    }

    @Test
    public void testGetWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGet() {
        Mockito.when(trainerStorage.get(1L)).thenReturn(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new Trainer(
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
    public void testSaveWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.save(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSave() {
        Mockito.when(trainerStorage.get(1L)).thenReturn(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));

        testSubject.save(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
        Assertions.assertThat(trainerStorage.get(1L)).isEqualTo(new Trainer(
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
    public void testUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdate() {
        testSubject.save(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
        Mockito.when(trainerStorage.get(1L)).thenReturn(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
        Trainer initialTrainer = testSubject.get(1L);
        testSubject.update(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                SpecializationType.FITNESS
        ));
        Mockito.when(trainerStorage.get(1L)).thenReturn(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                SpecializationType.FITNESS
        ));
        Assertions.assertThat(testSubject.get(1L)).isNotEqualTo(initialTrainer);
    }

    @Test
    public void testDeleteWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.delete(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDelete() {
        Mockito.when(trainerStorage.get(1L)).thenReturn(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
        testSubject.delete(1L);
        Mockito.when(trainerStorage.get(1L)).thenThrow(TrainerNotFoundException.class);
        Assertions.assertThatThrownBy(() -> testSubject.get(1L))
                .isExactlyInstanceOf(TrainerNotFoundException.class);
    }

    @Test
    public void testGetByUsernameWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByUsernameWhenEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername(""))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByUsername() {
        Mockito.when(trainerStorage.getByUsername("username")).thenReturn(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        ));
        Assertions.assertThat(testSubject.getByUsername("username")).isEqualTo(new Trainer(
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
    public void testFindByUsernameWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findByUsername(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByUsernameWhenEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.findByUsername(""))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByUsername() {
        Mockito.when(trainerStorage.findByUsername("username")).thenReturn(Optional.of(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        )));
        Assertions.assertThat(testSubject.findByUsername("username")).isEqualTo(Optional.of(new Trainer(
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
    public void testFindByIdWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindById() {
        Mockito.when(trainerStorage.findById(1L)).thenReturn(Optional.of(new Trainer(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        )));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(new Trainer(
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