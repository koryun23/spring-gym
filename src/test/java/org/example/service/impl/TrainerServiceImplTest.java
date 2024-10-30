package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.repository.TraineeEntityRepository;
import org.example.repository.TrainerEntityRepository;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.user.UserService;
import org.example.service.impl.trainer.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    private TrainerService testSubject;

    @Mock
    private TrainerEntityRepository trainerEntityRepository;

    @Mock
    private TraineeEntityRepository traineeEntityRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        testSubject = new TrainerServiceImpl(traineeEntityRepository, trainerEntityRepository, userService);
    }

    @Test
    public void testCreate() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.save(trainerEntity)).thenReturn(trainerEntity);

        Assertions.assertThat(testSubject.create(trainerEntity)).isEqualTo(trainerEntity);

        Mockito.verifyNoMoreInteractions(trainerEntityRepository);
    }

    @Test
    public void testUpdate() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.save(trainerEntity)).thenReturn(trainerEntity);

        Assertions.assertThat(testSubject.update(trainerEntity)).isEqualTo(trainerEntity);

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testSelect() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(trainerEntity));

        Assertions.assertThat(testSubject.select(1L)).isEqualTo(trainerEntity);

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testSelectByUsername() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findByUserUsername("username")).thenReturn(Optional.of(trainerEntity));

        Assertions.assertThat(testSubject.selectByUsername("username")).isEqualTo(trainerEntity);

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testFindById() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(trainerEntity));

        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(trainerEntity));

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testFindByUsername() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findByUserUsername("username")).thenReturn(Optional.of(trainerEntity));

        Assertions.assertThat(testSubject.findByUsername("username")).isEqualTo(Optional.of(trainerEntity));

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testFindAllNotAssignedTo() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findAllTrainersNotAssignedTo("username")).thenReturn(
            List.of(trainerEntity));

        Assertions.assertThat(testSubject.findAllNotAssignedTo("username")).isEqualTo(List.of(trainerEntity));

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

}