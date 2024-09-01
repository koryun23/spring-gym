package org.example.facade.impl;

import org.assertj.core.api.Assertions;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.Trainee;
import org.example.entity.Training;
import org.example.entity.TrainingType;
import org.example.facade.core.TrainingFacade;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrainingFacadeImplTest {

    private TrainingFacade testSubject;

    @Mock
    private TrainingService trainingService;

    @Mock
    private IdService idService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @BeforeEach
    public void init() {
        testSubject = new TrainingFacadeImpl(trainingService, idService, traineeService, trainerService);
    }

    @Test
    public void testCreateTrainingWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.createTraining(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateTrainingWhenTraineeIdNegative() {
        Assertions.assertThat(testSubject.createTraining(new TrainingCreationRequestDto(
                -1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        )).getErrors().getFirst()).isEqualTo("The trainee id must be positive: -1 specified");
    }

    @Test
    public void testCreateTrainingWhenTrainerIdNegative() {
        Assertions.assertThat(testSubject.createTraining(new TrainingCreationRequestDto(
                1L,
                -1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        )).getErrors().getFirst()).isEqualTo("The trainer id must be positive: -1 specified");
    }

    @Test
    public void testCreateTrainingWhenTraineeDoesNotExist() {
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.createTraining(new TrainingCreationRequestDto(
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        )).getErrors().getFirst()).isEqualTo("Cannot create a training: a trainee with an id of 1 does not exist");
    }

    @Test
    public void testCreateTrainingWhenTrainerDoesNotExist() {
        Mockito.when(trainerService.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.of(new Trainee(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        )));
        Assertions.assertThat(testSubject.createTraining(new TrainingCreationRequestDto(
                1L,
                1L,
                "training",
                TrainingType.AEROBIC,
                Date.valueOf("2024-10-10"),
                1000L
        )).getErrors().getFirst()).isEqualTo("Cannot create a training: a trainer with an id of 1 does not exist");
    }

    @Test
    public void testRetrieveTrainingWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.retrieveTraining(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testRetrieveTrainingWhenNegative() {
        Assertions.assertThat(testSubject.retrieveTraining(-1L).getErrors().getFirst())
                .isEqualTo("Training id must be positive: -1 specified");
    }

    @Test
    public void testRetrieveTrainingWhenDoesNotExist() {
        Mockito.when(trainingService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.retrieveTraining(1L).getErrors().getFirst())
                .isEqualTo("Training with a specified id of 1 does not exist");
    }
}