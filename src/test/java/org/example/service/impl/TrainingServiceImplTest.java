package org.example.service.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.repository.core.TrainingEntityRepository;
import org.example.service.core.training.TrainingService;
import org.example.service.impl.training.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    private TrainingService testSubject;

    @Mock
    private TrainingEntityRepository trainingEntityRepository;

    @BeforeEach
    public void init() {
        testSubject = new TrainingServiceImpl(trainingEntityRepository);
    }

    @Test
    public void testCreate() {
        UserEntity traineeUserEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(traineeUserEntity, Date.valueOf("2024-10-10"), "address");

        UserEntity trainerUserEntity = new UserEntity("first2", "last2", "username2", "password2", true);
        TrainingTypeEntity specialization = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(trainerUserEntity, specialization);

        TrainingEntity trainingEntity = new TrainingEntity(
            traineeEntity, trainerEntity, "name", specialization, Date.valueOf("2024-10-10"), 100L
        );
        Mockito.when(trainingEntityRepository.save(trainingEntity)).thenReturn(trainingEntity);

        Assertions.assertThat(testSubject.create(trainingEntity)).isEqualTo(trainingEntity);

        Mockito.verifyNoMoreInteractions(trainingEntityRepository);
    }

    @Test
    public void testSelect() {
        UserEntity traineeUserEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(traineeUserEntity, Date.valueOf("2024-10-10"), "address");

        UserEntity trainerUserEntity = new UserEntity("first2", "last2", "username2", "password2", true);
        TrainingTypeEntity specialization = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(trainerUserEntity, specialization);

        TrainingEntity trainingEntity = new TrainingEntity(
            traineeEntity, trainerEntity, "name", specialization, Date.valueOf("2024-10-10"), 100L
        );
        Mockito.when(trainingEntityRepository.findById(1L)).thenReturn(Optional.of(trainingEntity));

        Assertions.assertThat(testSubject.select(1L)).isEqualTo(trainingEntity);

        Mockito.verifyNoMoreInteractions(trainingEntityRepository);
    }

    @Test
    public void testUpdate() {
        UserEntity traineeUserEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(traineeUserEntity, Date.valueOf("2024-10-10"), "address");

        UserEntity trainerUserEntity = new UserEntity("first2", "last2", "username2", "password2", true);
        TrainingTypeEntity specialization = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(trainerUserEntity, specialization);

        TrainingEntity trainingEntity = new TrainingEntity(
            traineeEntity, trainerEntity, "name", specialization, Date.valueOf("2024-10-10"), 100L
        );
        Mockito.when(trainingEntityRepository.update(trainingEntity)).thenReturn(trainingEntity);

        Assertions.assertThat(testSubject.update(trainingEntity)).isEqualTo(trainingEntity);

        Mockito.verifyNoMoreInteractions(trainingEntityRepository);
    }

    @Test
    public void testFindById() {
        UserEntity traineeUserEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(traineeUserEntity, Date.valueOf("2024-10-10"), "address");

        UserEntity trainerUserEntity = new UserEntity("first2", "last2", "username2", "password2", true);
        TrainingTypeEntity specialization = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(trainerUserEntity, specialization);

        TrainingEntity trainingEntity = new TrainingEntity(
            traineeEntity, trainerEntity, "name", specialization, Date.valueOf("2024-10-10"), 100L
        );
        Mockito.when(trainingEntityRepository.findById(1L)).thenReturn(Optional.of(trainingEntity));

        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(trainingEntity));

        Mockito.verifyNoMoreInteractions(trainingEntityRepository);
    }
}