package org.example.service.impl;

import java.sql.Date;
import java.util.List;
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
    public void testFindAllByTraineeUsernameAndCriteriaWhenTraineeUsernameIsNull() {
        Assertions.assertThatThrownBy(
                () -> testSubject.findAllByTraineeUsernameAndCriteria(null, null, null, null, null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(trainingEntityRepository, traineeService, trainerService);
    }

    @Test
    public void testFindAllByTraineeUsernameAndCriteria() {
        // given
        TrainingEntity training = new TrainingEntity(
            new TraineeEntity(), new TrainerEntity(), "training", new TrainingTypeEntity(), Date.valueOf("2024-10-10"),
            1000L
        );
        List<TrainingEntity> trainings = List.of(training);

        // when
        Mockito.when(trainingEntityRepository.findAllByTraineeUsernameAndCriteria("trainee",
            null, null, null, null)).thenReturn(trainings);

        // then
        Assertions.assertThat(testSubject.findAllByTraineeUsernameAndCriteria("trainee", null, null, null, null))
            .isEqualTo(trainings);
        Mockito.verifyNoMoreInteractions(trainingEntityRepository);
        Mockito.verifyNoInteractions(traineeService, trainerService);
    }

    @Test
    public void testFindAllByTrainerUsernameAndCriteriaWhenTrainerUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findAllByTrainerUsernameAndCriteria(null, null, null, null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(traineeService, trainerService, trainingEntityRepository);
    }

    @Test
    public void testFindAllByTrainerUsernameAndCriteria() {
        // given
        TrainingEntity training = new TrainingEntity(
            new TraineeEntity(), new TrainerEntity(), "training", new TrainingTypeEntity(), Date.valueOf("2024-10-10"),
            1000L
        );
        List<TrainingEntity> trainings = List.of(training);

        // when
        Mockito.when(trainingEntityRepository.findAllByTrainerUsernameAndCriteria("trainee",
            null, null, null)).thenReturn(trainings);

        // then
        Assertions.assertThat(testSubject.findAllByTrainerUsernameAndCriteria("trainee", null, null, null))
            .isEqualTo(trainings);
        Mockito.verifyNoMoreInteractions(trainingEntityRepository);
        Mockito.verifyNoInteractions(traineeService, trainerService);
    }
}