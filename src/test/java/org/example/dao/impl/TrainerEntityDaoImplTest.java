package org.example.dao.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.exception.TrainerNotFoundException;
import org.example.repository.core.TrainerEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainerEntityDaoImplTest {

    private TrainerDaoImpl testSubject;

    @Mock
    private TrainerEntityRepository trainerEntityRepository;

    @BeforeEach
    public void init() {
        testSubject = new TrainerDaoImpl();
        testSubject.setTrainerEntityRepository(trainerEntityRepository);
    }

    @Test
    public void testGetWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGet() {
        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        ));
    }

    @Test
    public void testSaveWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.save(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSave() {
        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));

        testSubject.save(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        ));
        Assertions.assertThat(trainerEntityRepository.findById(1L)).isEqualTo(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
    }

    @Test
    public void testUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdate() {
        testSubject.save(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        ));
        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        TrainerEntity initialTrainerEntity = testSubject.get(1L);
        testSubject.update(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", false),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        ));
        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", false),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        Assertions.assertThat(testSubject.get(1L)).isNotEqualTo(initialTrainerEntity);
    }

    @Test
    public void testDeleteWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.delete(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDelete() {
        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        testSubject.delete(1L);
        Mockito.when(trainerEntityRepository.findById(1L)).thenThrow(TrainerNotFoundException.class);
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
        Mockito.when(trainerEntityRepository.findByUsername("username")).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        Assertions.assertThat(testSubject.getByUsername("username")).isEqualTo(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
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
        Mockito.when(trainerEntityRepository.findByUsername("username")).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        Assertions.assertThat(testSubject.findByUsername("username")).isEqualTo(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
    }

    @Test
    public void testFindByIdWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindById() {
        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
    }
}