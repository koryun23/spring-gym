package org.example.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.example.dto.RestResponse;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeSwitchActivationStateResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.user.UserEntity;
import org.example.exception.CustomIllegalArgumentException;
import org.example.mapper.trainee.TraineeMapper;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.user.UserService;
import org.example.validator.TraineeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TraineeControllerTest {

    private TraineeController testSubject;

    @Mock
    private TraineeService traineeService;

    @Mock
    private UserService userService;

    @Mock
    private TraineeMapper traineeMapper;

    @Mock
    private TraineeValidator traineeValidator;

    @BeforeEach
    public void init() {
        testSubject = new TraineeController(traineeService, userService, traineeMapper, traineeValidator);
    }

    @Test
    public void testRegisterWhenValidationsFail() {
        Mockito.when(traineeValidator.validateCreateTrainee(new TraineeCreationRequestDto("first", "last", null, null)))
            .thenThrow(CustomIllegalArgumentException.class);

        Assertions.assertThatThrownBy(
                () -> testSubject.register(new TraineeCreationRequestDto("first", "last", null, null)))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testRegisterWhenValidationsPass() {

        Mockito.when(traineeValidator.validateCreateTrainee(new TraineeCreationRequestDto("first", "last", null, null)))
            .thenReturn(null);
        Mockito.when(traineeMapper.mapTraineeCreationRequestDtoToTraineeEntity(
                new TraineeCreationRequestDto("first", "last", null, null)))
            .thenReturn(new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                null, null
            ));
        Mockito.when(traineeService.create(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            null, null
        ))).thenReturn(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            null, null
        ));
        Mockito.when(traineeMapper.mapTraineeEntityToTraineeCreationResponseDto(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            null, null
        ))).thenReturn(new TraineeCreationResponseDto("username", "password"));

        ResponseEntity<RestResponse> register =
            testSubject.register(new TraineeCreationRequestDto("first", "last", null, null));
        LocalDateTime timestamp = register.getBody().getTimestamp();
        Assertions.assertThat(register)
            .isEqualTo(new ResponseEntity<>(
                new RestResponse(new TraineeCreationResponseDto("username", "password"), HttpStatus.OK, timestamp,
                    Collections.emptyList()), HttpStatus.OK));
    }

    @Test
    public void testRetrieveWhenValidationsFail() {
        Mockito.when(traineeValidator.validateRetrieveTrainee("username"))
            .thenThrow(CustomIllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> testSubject.retrieve("username")).isExactlyInstanceOf(
            CustomIllegalArgumentException.class);
    }

    @Test
    public void testRetrieveWhenValidationsPass() {
        Mockito.when(traineeValidator.validateRetrieveTrainee("username")).thenReturn(null);

        TraineeEntity trainee = new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            null, null
        );
        Mockito.when(traineeService.selectByUsername("username")).thenReturn(trainee);

        TraineeRetrievalResponseDto responseDto = new TraineeRetrievalResponseDto(
            "first", "last", null, null, true, Collections.emptyList()
        );

        Mockito.when(traineeMapper.mapTraineeEntityToTraineeRetrievalResponseDto(trainee)).thenReturn(responseDto);

        ResponseEntity<RestResponse> responseEntity = testSubject.retrieve("username");
        LocalDateTime timestamp = responseEntity.getBody().getTimestamp();

        Assertions.assertThat(responseEntity).isEqualTo(new ResponseEntity<>(
            new RestResponse(responseDto, HttpStatus.OK, timestamp, Collections.emptyList()),
            HttpStatus.OK
        ));
    }

    @Test
    public void testUpdateWhenValidationsFail() {
        TraineeUpdateRequestDto requestDto = new TraineeUpdateRequestDto("first", "last", "username", true, null, null);
        Mockito.when(traineeValidator.validateUpdateTrainee(requestDto))
            .thenThrow(CustomIllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> testSubject.update(requestDto, "username")).isExactlyInstanceOf(
            CustomIllegalArgumentException.class);
    }

    @Test
    public void testUpdateWhenValidationsPass() {
        TraineeUpdateRequestDto requestDto = new TraineeUpdateRequestDto("first", "last", "username", true, null, null);
        TraineeEntity trainee =
            new TraineeEntity(new UserEntity("first", "last", "username", "password", true), null, null);
        TraineeUpdateResponseDto responseDto =
            new TraineeUpdateResponseDto("username", "first", "last", null, null, true, Collections.emptyList());

        Mockito.when(traineeValidator.validateUpdateTrainee(requestDto)).thenReturn(null);
        Mockito.when(traineeMapper.mapTraineeUpdateRequestDtoToTraineeEntity(requestDto)).thenReturn(trainee);
        Mockito.when(traineeService.update(trainee)).thenReturn(trainee);
        Mockito.when(traineeMapper.mapTraineeEntityToTraineeUpdateResponseDto(trainee)).thenReturn(responseDto);

        ResponseEntity<RestResponse> responseEntity = testSubject.update(requestDto, "username");
        LocalDateTime timestamp = responseEntity.getBody().getTimestamp();
        RestResponse restResponse = new RestResponse(responseDto, HttpStatus.OK, timestamp, Collections.emptyList());
        Assertions.assertThat(responseEntity).isEqualTo(new ResponseEntity<>(restResponse, HttpStatus.OK));
    }

    @Test
    public void testDeleteWhenValidationsFail() {
        TraineeDeletionByUsernameRequestDto requestDto = new TraineeDeletionByUsernameRequestDto("username");
        Mockito.when(traineeValidator.validateDeleteTrainee(requestDto))
            .thenThrow(CustomIllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> testSubject.delete("username"))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testDeleteWhenValidationsPass() {
        TraineeDeletionByUsernameRequestDto requestDto = new TraineeDeletionByUsernameRequestDto("username");
        TraineeDeletionResponseDto responseDto = new TraineeDeletionResponseDto(HttpStatus.OK);

        Mockito.when(traineeValidator.validateDeleteTrainee(requestDto)).thenReturn(null);
        Mockito.when(traineeService.delete("username")).thenReturn(true);

        ResponseEntity<RestResponse> responseEntity = testSubject.delete("username");
        LocalDateTime timestamp = responseEntity.getBody().getTimestamp();
        RestResponse restResponse = new RestResponse(responseDto, HttpStatus.OK, timestamp, Collections.emptyList());

        Assertions.assertThat(responseEntity).isEqualTo(new ResponseEntity<>(restResponse, HttpStatus.OK));
    }

    @Test
    public void testSwitchActivationStateWhenValidationsFail() {
        TraineeSwitchActivationStateRequestDto requestDto =
            new TraineeSwitchActivationStateRequestDto("username", true);
        Mockito.when(traineeValidator.validateSwitchActivationState(requestDto)).thenThrow(
            CustomIllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> testSubject.switchActivationState(requestDto, "username"))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testSwitchActivationStateWhenValidationsPass() {
        TraineeSwitchActivationStateRequestDto requestDto =
            new TraineeSwitchActivationStateRequestDto("username", true);
        UserEntity user = new UserEntity("first", "last", "username", "password", true);
        TraineeSwitchActivationStateResponseDto responseDto =
            new TraineeSwitchActivationStateResponseDto(HttpStatus.OK);
        Mockito.when(traineeValidator.validateSwitchActivationState(requestDto)).thenReturn(null);
        Mockito.when(userService.switchActivationState("username", true)).thenReturn(user);

        ResponseEntity<RestResponse> actual = testSubject.switchActivationState(requestDto, "username");
        LocalDateTime timestamp = actual.getBody().getTimestamp();

        RestResponse restResponse = new RestResponse(responseDto, HttpStatus.OK, timestamp, Collections.emptyList());
        ResponseEntity<RestResponse> expected = new ResponseEntity<>(restResponse, HttpStatus.OK);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

}