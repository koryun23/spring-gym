package org.example.service.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.plain.TrainingDto;
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

    @Test
    public void testCreate() {
        TrainingDto trainingDto = new TrainingDto("trainee", "trainer", "training", null, 1000L);
        TraineeEntity traineeEntity =
            new TraineeEntity(new UserEntity("first", "last", "trainee", "pwd", true), null, null);
        TrainerEntity trainerEntity = new TrainerEntity(new UserEntity("first", "last", "trainer", "pwd", true),
            new TrainingTypeEntity(TrainingType.AEROBIC));
        TrainingEntity trainingEntity = new TrainingEntity(
            traineeEntity, trainerEntity, "training", new TrainingTypeEntity(TrainingType.AEROBIC), null, 1000L
        );

        Mockito.when(traineeService.selectByUsername("trainee")).thenReturn(traineeEntity);
        Mockito.when(trainerService.selectByUsername("trainer")).thenReturn(trainerEntity);
        Mockito.when(trainingEntityRepository.save(trainingEntity)).thenReturn(trainingEntity);

        Assertions.assertThat(testSubject.create(trainingDto)).isEqualTo(trainingEntity);
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
        TraineeEntity traineeEntity =
            new TraineeEntity(new UserEntity("first", "last", "trainee", "pwd", true), null, null);
        TrainerEntity trainerEntity = new TrainerEntity(new UserEntity("first", "last", "trainer", "pwd", true),
            new TrainingTypeEntity(TrainingType.AEROBIC));
        TrainingEntity trainingEntity = new TrainingEntity(
            traineeEntity, trainerEntity, "training", new TrainingTypeEntity(TrainingType.AEROBIC), null, 1000L
        );

        Mockito.when(trainingEntityRepository.save(trainingEntity)).thenReturn(trainingEntity);

        Assertions.assertThat(testSubject.update(trainingEntity)).isEqualTo(trainingEntity);
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