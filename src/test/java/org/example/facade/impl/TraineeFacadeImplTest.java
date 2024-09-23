package org.example.facade.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.facade.core.TraineeFacade;
import org.example.mapper.trainee.TraineeCreationRequestDtoToTraineeEntityMapper;
import org.example.mapper.trainee.TraineeEntityToTraineeCreationResponseDtoMapper;
import org.example.mapper.trainee.TraineeEntityToTraineeRetrievalResponseDtoMapper;
import org.example.mapper.trainee.TraineeEntityToTraineeUpdateResponseDtoMapperImpl;
import org.example.mapper.trainee.TraineeUpdateRequestDtoToTraineeEntityMapper;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingService;
import org.example.service.core.UserService;
import org.example.service.core.UsernamePasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TraineeFacadeImplTest {

    private TraineeFacade testSubject;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private UserService userService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private IdService idService;

    @Mock
    private UsernamePasswordService usernamePasswordService;

    @Mock
    private TraineeCreationRequestDtoToTraineeEntityMapper traineeCreationRequestDtoToTraineeEntityMapper;

    @Mock
    private TraineeEntityToTraineeCreationResponseDtoMapper traineeToTraineeCreationResponseDtoMapper;

    @Mock
    private TraineeUpdateRequestDtoToTraineeEntityMapper traineeUpdateRequestDtoToTraineeEntityMapper;

    @Mock
    private TraineeEntityToTraineeUpdateResponseDtoMapperImpl traineeEntityToTraineeUpdateResponseDtoMapper;

    @Mock
    private TraineeEntityToTraineeRetrievalResponseDtoMapper traineeEntityToTraineeRetrievalResponseDtoMapper;

    @BeforeEach
    public void init() {
        testSubject = new TraineeFacadeImpl(
            traineeService,
            trainerService,
            trainingService,
            userService,
            traineeCreationRequestDtoToTraineeEntityMapper,
            traineeToTraineeCreationResponseDtoMapper,
            traineeUpdateRequestDtoToTraineeEntityMapper,
            traineeEntityToTraineeUpdateResponseDtoMapper,
            traineeEntityToTraineeRetrievalResponseDtoMapper,
            usernamePasswordService,
            idService
        );
    }

    @Test
    public void testCreateTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.createTrainee(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.updateTrainee(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateTraineeWhenUserDoesNotExist() {
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.updateTrainee(new TraineeUpdateRequestDto(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        ))).isExactlyInstanceOf(TraineeNotFoundException.class);
    }

    @Test
    public void testRetrieveTraineeWhenNegative() {
        Assertions.assertThat(testSubject.retrieveTrainee(-1L).getErrors().getFirst())
                .isEqualTo("TraineeEntity id must be positive: -1 specified");
    }

    @Test
    public void testRetrieveTraineeWhenTraineeDoesNotExist() {
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.retrieveTrainee(1L).getErrors().getFirst())
                .isEqualTo("A TraineeEntity with an id - 1, does not exist");
    }

    @Test
    public void testDeleteTraineeWhenNegative() {
        Assertions.assertThat(testSubject.deleteTrainee(-1L).getErrors().getFirst())
                .isEqualTo("TraineeEntity id must be positive: -1 specified");
    }

    @Test
    public void testDeleteTraineeWhenUserDoesNotExist() {
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.deleteTrainee(1L).getErrors().getFirst())
                .isEqualTo("A TraineeEntity with an id - 1, does not exist");
    }
}