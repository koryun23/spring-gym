package org.example.dao.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.repository.core.TraineeEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TraineeDaoImplTest {

    private TraineeDaoImpl testSubject;

    @Mock
    private TraineeEntityRepository traineeRepository;

    @BeforeEach
    public void init() {
        testSubject = new TraineeDaoImpl();
        testSubject.setTraineeEntityRepository(traineeRepository);
    }

    @Test
    public void testGetWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGet() {
        Mockito.when(traineeRepository.findById(1L)).thenReturn(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        )));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        ));
        Mockito.verifyNoMoreInteractions(traineeRepository);
    }

    @Test
    public void testSaveWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.save(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSave() {
        testSubject.save(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        ));
        Mockito.when(traineeRepository.findById(2L)).thenReturn(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        )));
        Assertions.assertThat(testSubject.get(2L)).isEqualTo(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        ));
    }

    @Test
    public void testUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdate() {

        Mockito.when(traineeRepository.update(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        ))).thenReturn(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        ));
        Mockito.when(traineeRepository.findById(1L)).thenReturn(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        )));
        testSubject.update(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        ));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        ));
    }

    @Test
    public void testDeleteWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.delete(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDelete() {
        Mockito.when(traineeRepository.findById(1L)).thenReturn(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        )));
        testSubject.delete(1L);
        Mockito.when(traineeRepository.findById(1L)).thenThrow(TraineeNotFoundException.class);
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
        Mockito.when(traineeRepository.findByUsername("username")).thenReturn(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        )));
        Assertions.assertThat(testSubject.getByUsername("username")).isEqualTo(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
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
        Mockito.when(traineeRepository.findByUsername("username")).thenReturn(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        )));
        Assertions.assertThat(testSubject.findByUsername("username")).isEqualTo(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        )));
    }

    @Test
    public void testFindByIdWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindById() {
        Mockito.when(traineeRepository.findById(1L)).thenReturn(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        )));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        )));
    }
}