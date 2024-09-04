package org.example.facade.impl;

import org.assertj.core.api.Assertions;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.entity.TraineeEntity;
import org.example.facade.core.TraineeFacade;
import org.example.service.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TraineeFacadeImplTest {

    private TraineeFacade testSubject;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private IdService idService;

    @Mock
    private UsernamePasswordService usernamePasswordService;

    @BeforeEach
    public void init() {
        testSubject = new TraineeFacadeImpl(traineeService, trainerService, idService, usernamePasswordService);
    }

    @Test
    public void testCreateTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.createTrainee(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateTraineeWhenTraineeWithIdExists() {
        Mockito.when(idService.getId()).thenReturn(1L);
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.of(new TraineeEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        )));
        Assertions.assertThat(testSubject.createTrainee(new TraineeCreationRequestDto(
                "first",
                "last",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
        )).getErrors().getFirst()).isEqualTo("A trainee with the specified id - 1, already exists");
    }

    @Test
    public void testUpdateTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.updateTrainee(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateTraineeWhenUserDoesNotExist() {
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.updateTrainee(new TraineeUpdateRequestDto(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                Date.valueOf("2024-10-10"),
                "manchester"
                )).getErrors().getFirst()).isEqualTo("A user with specified username - username, does not exist");
    }

    @Test
    public void testRetrieveTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.retrieveTrainee(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
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
    public void testDeleteTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.deleteTrainee(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
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