package org.example.service.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.example.repository.core.TraineeEntityRepository;
import org.example.service.core.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    private TraineeService testSubject;

    @Mock
    private TraineeEntityRepository traineeEntityRepository;

    @BeforeEach
    public void init() {
        testSubject = new TraineeServiceImpl(traineeEntityRepository);
    }

    @Test
    public void testCreate() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(
            userEntity,
            Date.valueOf("2024-10-10"), "address"
        );
        Mockito.when(traineeEntityRepository.save(traineeEntity)).thenReturn(traineeEntity);
        Assertions.assertThat(testSubject.create(traineeEntity)).isEqualTo(traineeEntity);
        Mockito.verifyNoMoreInteractions(traineeEntityRepository);
    }

    @Test
    public void testUpdate() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(
            userEntity,
            Date.valueOf("2024-10-10"), "address"
        );
        Mockito.when(traineeEntityRepository.update(traineeEntity)).thenReturn(traineeEntity);
        Assertions.assertThat(testSubject.update(traineeEntity)).isEqualTo(traineeEntity);
        Mockito.verifyNoMoreInteractions(traineeEntityRepository);
    }

    @Test
    public void testSelect() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(
            userEntity,
            Date.valueOf("2024-10-10"), "address"
        );
        Mockito.when(traineeEntityRepository.findById(1L)).thenReturn(Optional.of(traineeEntity));
        Assertions.assertThat(testSubject.select(1L)).isEqualTo(traineeEntity);
        Mockito.verifyNoMoreInteractions(traineeEntityRepository);
    }

    @Test
    public void testSelectByUsername() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(
            userEntity,
            Date.valueOf("2024-10-10"), "address"
        );
        Mockito.when(traineeEntityRepository.findByUsername("username")).thenReturn(Optional.of(traineeEntity));
        Assertions.assertThat(testSubject.selectByUsername("username")).isEqualTo(traineeEntity);
        Mockito.verifyNoMoreInteractions(traineeEntityRepository);
    }

    @Test
    public void testFindByUsername() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(
            userEntity,
            Date.valueOf("2024-10-10"), "address"
        );
        Mockito.when(traineeEntityRepository.findByUsername("username")).thenReturn(Optional.of(traineeEntity));
        Assertions.assertThat(testSubject.findByUsername("username")).isEqualTo(Optional.of(traineeEntity));
        Mockito.verifyNoMoreInteractions(traineeEntityRepository);
    }

    @Test
    public void testFindById() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(
            userEntity,
            Date.valueOf("2024-10-10"), "address"
        );
        Mockito.when(traineeEntityRepository.findById(1L)).thenReturn(Optional.of(traineeEntity));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(traineeEntity));
        Mockito.verifyNoMoreInteractions(traineeEntityRepository);
    }

}