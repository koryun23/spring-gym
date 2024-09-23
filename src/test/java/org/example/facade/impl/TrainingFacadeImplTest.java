package org.example.facade.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainingType;
import org.example.entity.UserEntity;
import org.example.facade.core.TrainingFacade;
import org.example.mapper.training.TrainingCreationRequestDtoToTrainingEntityMapper;
import org.example.mapper.training.TrainingEntityToTrainingCreationResponseDtoMapper;
import org.example.mapper.training.TrainingEntityToTrainingRetrievalResponseDtoMapper;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.example.service.core.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainingFacadeImplTest {

    private TrainingFacade testSubject;

    @Mock
    private TrainingService trainingService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private UserService userService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingCreationRequestDtoToTrainingEntityMapper trainingCreationRequestDtoToTrainingEntityMapper;

    @Mock
    private TrainingEntityToTrainingCreationResponseDtoMapper trainingEntityToTrainingCreationResponseDtoMapper;

    @Mock
    private TrainingEntityToTrainingRetrievalResponseDtoMapper trainingEntityToTrainingRetrievalResponseDtoMapper;

    @BeforeEach
    public void init() {
        testSubject = new TrainingFacadeImpl(
            trainingService,
            traineeService,
            trainerService,
            userService,
            trainingCreationRequestDtoToTrainingEntityMapper,
            trainingEntityToTrainingCreationResponseDtoMapper,
            trainingEntityToTrainingRetrievalResponseDtoMapper
        );
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
                1L,
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
                1L,
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
                1L,
                Date.valueOf("2024-10-10"),
                1000L
        )).getErrors().getFirst())
            .isEqualTo("Cannot create a trainingEntity: a trainee with an id of 1 does not exist");
    }

    @Test
    public void testCreateTrainingWhenTrainerDoesNotExist() {
        Mockito.when(trainerService.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.of(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"), "address"
        )));
        Assertions.assertThat(testSubject.createTraining(new TrainingCreationRequestDto(
                1L,
                1L,
                "training",
                1L,
                Date.valueOf("2024-10-10"),
                1000L
        )).getErrors().getFirst())
            .isEqualTo("Cannot create a trainingEntity: a trainer with an id of 1 does not exist");
    }

    @Test
    public void testRetrieveTrainingWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.retrieveTraining(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testRetrieveTrainingWhenNegative() {
        Assertions.assertThat(testSubject.retrieveTraining(-1L).getErrors().getFirst())
                .isEqualTo("TrainingEntity id must be positive: -1 specified");
    }

    @Test
    public void testRetrieveTrainingWhenDoesNotExist() {
        Mockito.when(trainingService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.retrieveTraining(1L).getErrors().getFirst())
                .isEqualTo("TrainingEntity with a specified id of 1 does not exist");
    }
}