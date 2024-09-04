package org.example.facade.impl;

import org.assertj.core.api.Assertions;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.entity.SpecializationType;
import org.example.entity.TrainerEntity;
import org.example.facade.core.TrainerFacade;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UsernamePasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TrainerEntityFacadeImplTest {

    private TrainerFacade testSubject;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private IdService idService;

    @Mock
    private UsernamePasswordService usernamePasswordService;

    @BeforeEach
    public void init() {
        testSubject = new TrainerFacadeImpl(trainerService, traineeService, idService, usernamePasswordService);
    }

    @Test
    public void testCreateTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.createTrainer(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateTrainerExceptionWhenUserWithIdExists() {
        Mockito.when(idService.getId()).thenReturn(1L);
        Mockito.when(trainerService.findById(1L)).thenReturn(Optional.of(new TrainerEntity(
                1L,
                "first",
                "last",
                "username",
                "password",
                true,
                SpecializationType.FITNESS
        )));

        Assertions.assertThat(testSubject.createTrainer(new TrainerCreationRequestDto(
                "first",
                "last",
                true,
                SpecializationType.FITNESS
        )).getErrors().getFirst()).isEqualTo("A TrainerEntity with the specified id - 1, already exists");
    }

    @Test
    public void testUpdateTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.updateTrainer(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateTrainerWhenTrainerDoesNotExist() {
        Mockito.when(trainerService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.updateTrainer(new TrainerUpdateRequestDto(
                1L,
                "first",
                "last",
                "username",
                "password",
                false,
                SpecializationType.FITNESS
        )).getErrors().getFirst()).isEqualTo("TrainerEntity with the specified id of 1 does not exist");
    }

    @Test
    public void testRetrieveTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.retrieveTrainer(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testRetrieveTrainerWhenNegative() {
        Assertions.assertThat(testSubject.retrieveTrainer(-1L).getErrors().getFirst())
                .isEqualTo("TrainerEntity id must be positive: -1 specified");
    }

    @Test
    public void testRetrieveTrainerWhenDoesNotExist() {
        Mockito.when(trainerService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.retrieveTrainer(1L).getErrors().getFirst())
                .isEqualTo("A TrainerEntity with a specified id of 1 not found");
    }
}