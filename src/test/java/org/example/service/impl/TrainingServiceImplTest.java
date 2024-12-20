package org.example.service.impl;

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
}