package org.example.service.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingEntity;
import org.example.entity.training.TrainingType;
import org.example.entity.training.TrainingTypeEntity;
import org.example.entity.user.UserEntity;
import org.example.repository.TrainingEntityRepository;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
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

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @BeforeEach
    public void init() {
        testSubject = new TrainingServiceImpl(
            trainingEntityRepository,
            traineeService,
            trainerService
        );
    }

    //TODO
    @Test
    public void testCreate() {

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

    //TODO
    @Test
    public void testUpdate() {

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