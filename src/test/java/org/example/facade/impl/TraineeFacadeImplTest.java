package org.example.facade.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TraineeDeletionByIdRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineePasswordChangeRequestDto;
import org.example.dto.request.TraineeRetrievalByIdRequestDto;
import org.example.dto.request.TraineeRetrievalByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
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
        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.updateTrainee(new TraineeUpdateRequestDto(
            "u",
            "p",
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
        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Assertions.assertThat(testSubject.retrieveTrainee(
            new TraineeRetrievalByIdRequestDto("u", "p", -1L)).getErrors().getFirst()
        ).isEqualTo("TraineeEntity id must be positive: -1 specified");
    }

    @Test
    public void testRetrieveTraineeWhenTraineeDoesNotExist() {
        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.retrieveTrainee(
            new TraineeRetrievalByIdRequestDto("u", "p", 1L)).getErrors().getFirst()
        ).isEqualTo("A TraineeEntity with an id - 1, does not exist");
    }

    @Test
    public void testDeleteTraineeWhenNegative() {
        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Assertions.assertThat(testSubject.deleteTraineeById(
            new TraineeDeletionByIdRequestDto("u", "p", 1L)).getErrors().getFirst()
        ).isEqualTo("A TraineeEntity with an id - 1, does not exist");
    }

    @Test
    public void testDeleteTraineeWhenUserDoesNotExist() {
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Assertions.assertThat(testSubject.deleteTraineeById(
            new TraineeDeletionByIdRequestDto("u", "p", 1L)).getErrors().getFirst()
        ).isEqualTo("A TraineeEntity with an id - 1, does not exist");
    }

    @Test
    public void testSwitchActivationState() {
        TraineeSwitchActivationStateRequestDto requestDto =
            new TraineeSwitchActivationStateRequestDto("u", "p", 1L);
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        userEntity.setId(1L);
        TraineeEntity traineeEntity = new TraineeEntity(userEntity, Date.valueOf("2024-10-10"), "address");
        traineeEntity.setId(1L);

        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Mockito.when(traineeService.findById(1L)).thenReturn(Optional.of(traineeEntity));

        testSubject.switchActivationState(requestDto);
        UserEntity updatedUserEntity = new UserEntity("first", "last", "username", "password", false);
        updatedUserEntity.setId(1L);
        TraineeEntity updatedTraineeEntity =
            new TraineeEntity(updatedUserEntity, Date.valueOf("2024-10-10"), "address");
        updatedTraineeEntity.setId(1L);
        Assertions.assertThat(traineeService.findById(1L)).isEqualTo(Optional.of(updatedTraineeEntity));
    }

    @Test
    public void testSwitchActivationStateWhenNotAuthenticated() {
        TraineeSwitchActivationStateRequestDto requestDto =
            new TraineeSwitchActivationStateRequestDto("u", "p", 1L);

        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(false);

        TraineeUpdateResponseDto responseDto = testSubject.switchActivationState(requestDto);

        Assertions.assertThat(responseDto.getErrors()).isEqualTo(List.of("Authentication failed"));
    }

    @Test
    public void testChangePasswordWhenNotAuthenticated() {
        TraineePasswordChangeRequestDto requestDto =
            new TraineePasswordChangeRequestDto("u", "p", 1L, "new-password");

        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(false);

        TraineeUpdateResponseDto responseDto = testSubject.changePassword(requestDto);

        Assertions.assertThat(responseDto.getErrors()).isEqualTo(List.of("Authentication failed"));
    }

    @Test
    public void testDeleteByUsernameWhenNotAuthenticated() {
        TraineeDeletionByUsernameRequestDto requestDto =
            new TraineeDeletionByUsernameRequestDto("u", "p", "username");

        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(false);

        TraineeDeletionResponseDto responseDto = testSubject.deleteTraineeByUsername(requestDto);

        Assertions.assertThat(responseDto.getErrors()).isEqualTo(List.of("Authentication failed"));
    }

    @Test
    public void testRetrieveTraineeByUsername() {

        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(userEntity, Date.valueOf("2024-10-10"), "address");
        userEntity.setId(1L);
        traineeEntity.setId(1L);

        Mockito.when(userService.usernamePasswordMatching("u", "p")).thenReturn(true);
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.of(traineeEntity));
        Mockito.when(traineeEntityToTraineeRetrievalResponseDtoMapper.map(traineeEntity)).thenReturn(
            new TraineeRetrievalResponseDto(
                1L,
                1L,
                true,
                Date.valueOf("2024-10-10"),
                "address"
            )
        );
        TraineeRetrievalByUsernameRequestDto requestDto =
            new TraineeRetrievalByUsernameRequestDto("u", "p", "username");

        TraineeRetrievalResponseDto responseDto = testSubject.retrieveTrainee(requestDto);

        Assertions.assertThat(responseDto).isEqualTo(new TraineeRetrievalResponseDto(
            1L, 1L, true, Date.valueOf("2024-10-10"), "address"
        ));
    }
}