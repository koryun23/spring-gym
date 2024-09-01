package org.example.dao.impl;

import org.assertj.core.api.Assertions;
import org.example.entity.Trainee;
import org.example.exception.TraineeNotFoundException;
import org.example.repository.impl.TraineeStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TraineeDaoImplTest {

    private TraineeDaoImpl testSubject;

    @Mock
    private TraineeStorageImpl traineeStorage;

    @BeforeEach
    public void init() {
        testSubject = new TraineeDaoImpl();
        testSubject.setStorage(traineeStorage);
    }

    @Test
    public void testGetWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGet() {
        Mockito.when(traineeStorage.get(1L)).thenReturn(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
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
        Mockito.verifyNoMoreInteractions(traineeStorage);
    }

    @Test
    public void testSaveWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.save(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSave() {
        testSubject.save(new Trainee(
                2L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
        Mockito.when(traineeStorage.get(2L)).thenReturn(new Trainee(
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
    public void testUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdate() {

        Mockito.when(traineeStorage.update(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ))).thenReturn(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
        Mockito.when(traineeStorage.get(1L)).thenReturn(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
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
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
    }

    @Test
    public void testDeleteWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.delete(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDelete() {
        Mockito.when(traineeStorage.get(1L)).thenReturn(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
        testSubject.delete(1L);
        Mockito.when(traineeStorage.get(1L)).thenThrow(TraineeNotFoundException.class);
        Assertions.assertThatThrownBy(() -> testSubject.get(1L))
                .isExactlyInstanceOf(TraineeNotFoundException.class);
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
        Mockito.when(traineeStorage.getByUsername("username")).thenReturn(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        ));
        Assertions.assertThat(testSubject.getByUsername("username")).isEqualTo(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
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
        Mockito.when(traineeStorage.findByUsername("username")).thenReturn(Optional.of(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        )));
        Assertions.assertThat(testSubject.findByUsername("username")).isEqualTo(Optional.of(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        )));
    }

    @Test
    public void testFindByIdWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindById() {
        Mockito.when(traineeStorage.findById(1L)).thenReturn(Optional.of(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        )));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                Date.valueOf("2024-10-10"),
                "manchester"
        )));
    }
}