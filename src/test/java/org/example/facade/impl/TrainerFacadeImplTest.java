package org.example.facade.impl;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TrainerPasswordChangeRequestDto;
import org.example.dto.request.TrainerRetrievalByIdRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerListRetrievalResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
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
            "u", "p",
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

    @Test
    public void testSwitchActivationStateWhenNotAuthenticated() {
        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(false);

        TrainerUpdateResponseDto responseDto =
            testSubject.switchActivationState(new TrainerSwitchActivationStateRequestDto("u", "p", "username"));

        Assertions.assertThat(responseDto).isEqualTo(new TrainerUpdateResponseDto(List.of("Authentication failed")));
    }

    @Test
    public void testRetrieveAllTrainersNotAssignedToTrainee() {

        UserEntity user1 = new UserEntity("first", "last", "trainer", "pwd", true);
        TrainingTypeEntity specialization1 = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainer1 = new TrainerEntity(
            user1,
            specialization1
        );
        trainer1.setId(1L);
        user1.setId(1L);
        specialization1.setId(1L);

        UserEntity user2 = new UserEntity("f", "l", "t", "pwd-2", true);
        TrainingTypeEntity specialization2 = new TrainingTypeEntity(TrainingType.WEIGHTLIFTING);
        TrainerEntity trainer2 = new TrainerEntity(
            user2,
            specialization2
        );
        trainer2.setId(2L);
        user2.setId(2L);
        specialization2.setId(2L);

        TrainerRetrievalResponseDto responseDto1 = new TrainerRetrievalResponseDto(
            trainer1.getId(), user1.getId(), user1.getIsActive(),
            specialization1.getId()
        );

        TrainerRetrievalResponseDto responseDto2 =
            new TrainerRetrievalResponseDto(trainer2.getId(), user2.getId(), user2.getIsActive(),
                specialization2.getId());

        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Mockito.when(trainerService.findAllNotAssignedTo("username")).thenReturn(List.of(
            trainer1, trainer2
        ));
        Mockito.when(trainerEntityToTrainerRetrievalResponseDtoMapper.map(trainer1)).thenReturn(responseDto1);
        Mockito.when(trainerEntityToTrainerRetrievalResponseDtoMapper.map(trainer2)).thenReturn(responseDto2);

        TrainerListRetrievalResponseDto responseDto =
            testSubject.retrieveAllTrainersNotAssignedToTrainee(new RetrieveAllTrainersNotAssignedToTraineeRequestDto(
                "u", "p", "username"
            ));

        Assertions.assertThat(responseDto).isEqualTo(new TrainerListRetrievalResponseDto(
            List.of(responseDto1, responseDto2), "username"
        ));
    }

    @Test
    public void testChangePassword() {

        UserEntity user1 = new UserEntity("first", "last", "username", "psasword", true);
        TrainingTypeEntity specialization1 = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainer1 = new TrainerEntity(user1, specialization1);
        user1.setId(1L);
        specialization1.setId(1L);
        trainer1.setId(1L);

        Mockito.when(trainerService.findById(1L)).thenReturn(Optional.of(trainer1));

        Assertions.assertThat(
                testSubject.changePassword(new TrainerPasswordChangeRequestDto("u", "p", 1L, "new-password")))
            .isEqualTo(new TrainerUpdateResponseDto(1L, 1L, true, 1L));
    }
}