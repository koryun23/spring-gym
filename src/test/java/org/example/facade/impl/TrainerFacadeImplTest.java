package org.example.facade.impl;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TrainerRetrievalByIdRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.facade.core.TrainerFacade;
import org.example.mapper.trainer.TrainerCreationRequestDtoToTrainerEntityMapper;
import org.example.mapper.trainer.TrainerEntityToTrainerCreationResponseDtoMapper;
import org.example.mapper.trainer.TrainerEntityToTrainerRetrievalResponseDtoMapper;
import org.example.mapper.trainer.TrainerEntityToTrainerUpdateResponseDtoMapper;
import org.example.mapper.trainer.TrainerUpdateRequestDtoToTrainerEntityMapper;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.TrainingTypeService;
import org.example.service.core.UserService;
import org.example.service.core.UsernamePasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainerFacadeImplTest {

    private TrainerFacade testSubject;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private IdService idService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private UserService userService;

    @Mock
    private UsernamePasswordService usernamePasswordService;

    @Mock
    private TrainerCreationRequestDtoToTrainerEntityMapper trainerCreationRequestDtoToTrainerEntityMapper;

    @Mock
    private TrainerEntityToTrainerCreationResponseDtoMapper trainerEntityToTrainerCreationResponseDtoMapper;

    @Mock
    private TrainerUpdateRequestDtoToTrainerEntityMapper trainerUpdateRequestDtoToTrainerEntityMapper;

    @Mock
    private TrainerEntityToTrainerUpdateResponseDtoMapper trainerEntityToTrainerUpdateResponseDtoMapper;

    @Mock
    private TrainerEntityToTrainerRetrievalResponseDtoMapper trainerEntityToTrainerRetrievalResponseDtoMapper;

    @BeforeEach
    public void init() {
        testSubject = new TrainerFacadeImpl(
            trainerService,
            traineeService,
            trainingTypeService,
            userService,
            trainerCreationRequestDtoToTrainerEntityMapper,
            trainerEntityToTrainerCreationResponseDtoMapper,
            trainerUpdateRequestDtoToTrainerEntityMapper,
            trainerEntityToTrainerUpdateResponseDtoMapper,
            trainerEntityToTrainerRetrievalResponseDtoMapper,
            usernamePasswordService,
            idService
        );
    }

    @Test
    public void testCreateTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.createTrainer(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
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
            1L
        )).getErrors().getFirst()).isEqualTo("TrainerEntity with the specified id of 1 does not exist");
    }

    @Test
    public void testRetrieveTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.retrieveTrainer(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testRetrieveTrainerWhenNegative() {
        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Assertions.assertThat(testSubject.retrieveTrainer(new TrainerRetrievalByIdRequestDto("u", "p", -1L))
                .getErrors().getFirst()).isEqualTo("TrainerEntity id must be positive: -1 specified");
    }

    @Test
    public void testRetrieveTrainerWhenDoesNotExist() {
        Mockito.when(trainerService.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Assertions.assertThat(testSubject.retrieveTrainer(new TrainerRetrievalByIdRequestDto("u", "p", 1L))
                .getErrors().getFirst()).isEqualTo("A TrainerEntity with a specified id of 1 not found");
    }
}