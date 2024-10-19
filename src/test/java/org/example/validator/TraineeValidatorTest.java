package org.example.validator;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.RestResponse;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeRetrievalByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeSwitchActivationStateResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.example.service.core.TraineeService;
import org.example.service.core.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class TraineeValidatorTest {

    private TraineeValidator testSubject;

    @Mock
    private UserService userService;

    @Mock
    private TraineeService traineeService;

    @BeforeEach
    public void init() {
        testSubject = new TraineeValidator(userService, traineeService);
    }

    @Test
    public void testValidateCreateTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTraineeWhenFirstNameIsNull() {
        TraineeCreationRequestDto requestDto = new TraineeCreationRequestDto(null, "last", null, null);
        RestResponse<TraineeCreationResponseDto> restResponse = testSubject.validateCreateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();
        Assertions.assertThat(restResponse)
            .isEqualTo(
                new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                    List.of("first name is required"))
            );
    }

    @Test
    public void testValidateCreateTraineeWhenFirstNameIsEmpty() {
        TraineeCreationRequestDto requestDto = new TraineeCreationRequestDto("", "last", null, null);
        RestResponse<TraineeCreationResponseDto> restResponse = testSubject.validateCreateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();
        Assertions.assertThat(restResponse)
            .isEqualTo(
                new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                    List.of("first name is required"))
            );
    }

    @Test
    public void testValidateCreateTraineeWhenLastNameIsNull() {
        TraineeCreationRequestDto requestDto = new TraineeCreationRequestDto("first", null, null, null);
        RestResponse<TraineeCreationResponseDto> restResponse = testSubject.validateCreateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();
        Assertions.assertThat(restResponse)
            .isEqualTo(
                new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                    List.of("last name is required"))
            );
    }

    @Test
    public void testValidateCreateTraineeWhenLastNameIsEmpty() {
        TraineeCreationRequestDto requestDto = new TraineeCreationRequestDto("first", "", null, null);
        RestResponse<TraineeCreationResponseDto> restResponse = testSubject.validateCreateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();
        Assertions.assertThat(restResponse)
            .isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                List.of("last name is required"))
            );
    }

    @Test
    public void testValidateCreateTraineeWhenValid() {
        TraineeCreationRequestDto requestDto = new TraineeCreationRequestDto("first", "last", null, null);
        Assertions.assertThat(testSubject.validateCreateTrainee(requestDto)).isNull();
    }

    @Test
    public void testValidateUpdateTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenUsernameIsNull() {
        TraineeUpdateRequestDto requestDto =
            new TraineeUpdateRequestDto("first", "last", null, true, Date.valueOf("2024-10-10"), "address");
        RestResponse<TraineeUpdateResponseDto> restResponse = testSubject.validateUpdateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                List.of("Username is required"))
        );
    }

    @Test
    public void testValidateUpdateTraineeWhenUsernameIsEmpty() {
        TraineeUpdateRequestDto requestDto =
            new TraineeUpdateRequestDto("first", "last", "", true, Date.valueOf("2024-10-10"), "address");
        RestResponse<TraineeUpdateResponseDto> restResponse = testSubject.validateUpdateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                List.of("Username is required"))
        );
    }

    @Test
    public void testValidateUpdateTraineeWhenFirstNameIsNull() {
        TraineeUpdateRequestDto requestDto =
            new TraineeUpdateRequestDto(null, "last", "username", true, Date.valueOf("2024-10-10"), "address");
        RestResponse<TraineeUpdateResponseDto> restResponse = testSubject.validateUpdateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                List.of("First name is required"))
        );
    }

    @Test
    public void testValidateUpdateTraineeWhenFirstNameIsEmpty() {
        TraineeUpdateRequestDto requestDto =
            new TraineeUpdateRequestDto("", "last", "username", true, Date.valueOf("2024-10-10"), "address");
        RestResponse<TraineeUpdateResponseDto> restResponse = testSubject.validateUpdateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                List.of("First name is required"))
        );
    }

    @Test
    public void testValidateUpdateTraineeWhenLastNameIsNull() {
        TraineeUpdateRequestDto requestDto =
            new TraineeUpdateRequestDto("first", null, "username", true, Date.valueOf("2024-10-10"), "address");
        RestResponse<TraineeUpdateResponseDto> restResponse = testSubject.validateUpdateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                List.of("Last name is required"))
        );
    }

    @Test
    public void testValidateUpdateTraineeWhenLastNameIsEmpty() {
        TraineeUpdateRequestDto requestDto =
            new TraineeUpdateRequestDto("first", "", "usrename", true, Date.valueOf("2024-10-10"), "address");
        RestResponse<TraineeUpdateResponseDto> restResponse = testSubject.validateUpdateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                List.of("Last name is required"))
        );
    }

    @Test
    public void testValidateUpdateTraineeWhenIsActiveIsNull() {
        TraineeUpdateRequestDto requestDto =
            new TraineeUpdateRequestDto("first", "last", "username", null, Date.valueOf("2024-10-10"), "address");
        RestResponse<TraineeUpdateResponseDto> restResponse = testSubject.validateUpdateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                List.of("is-active field is required"))
        );
    }

    @Test
    public void testValidateUpdateTraineeWhenUserDoesNotExist() {

        Mockito.when(userService.findByUsername("username")).thenReturn(Optional.empty());

        TraineeUpdateRequestDto requestDto =
            new TraineeUpdateRequestDto("first", "last", "username", true, Date.valueOf("2024-10-10"), "address");
        RestResponse<TraineeUpdateResponseDto> restResponse = testSubject.validateUpdateTrainee(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
                List.of("User does not exist"))
        );

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateUpdateTraineeWhenValid() {
        Mockito.when(userService.findByUsername("username"))
            .thenReturn(Optional.of(new UserEntity("first", "last", "username",
                "password", true)));

        TraineeUpdateRequestDto requestDto =
            new TraineeUpdateRequestDto("first", "last", "username", true,
                Date.valueOf("2024-10-10"), "address");

        Assertions.assertThat(testSubject.validateUpdateTrainee(requestDto)).isNull();

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateRetrieveTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTraineeWhenUsernameIsNull() {
        RestResponse<TraineeRetrievalResponseDto> restResponse =
            testSubject.validateRetrieveTrainee(new TraineeRetrievalByUsernameRequestDto(null));
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(new RestResponse<>(
            null, HttpStatus.NOT_ACCEPTABLE, timestamp, List.of("Username is required"))
        );

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateRetrieveTraineeWhenUsernameIsEmpty() {
        RestResponse<TraineeRetrievalResponseDto> restResponse =
            testSubject.validateRetrieveTrainee(new TraineeRetrievalByUsernameRequestDto(""));
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(new RestResponse<>(
            null, HttpStatus.NOT_ACCEPTABLE, timestamp, List.of("Username is required"))
        );

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateRetrieveTraineeWhenUserDoesNotExist() {

        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());

        RestResponse<TraineeRetrievalResponseDto> restResponse =
            testSubject.validateRetrieveTrainee(new TraineeRetrievalByUsernameRequestDto("username"));
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(new RestResponse<>(
            null, HttpStatus.NOT_FOUND, timestamp, List.of("Trainee with a username of username not found"))
        );

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateRetrieveTraineeWhenValid() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(userEntity, null, null);
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.of(traineeEntity));

        RestResponse<TraineeRetrievalResponseDto> restResponse =
            testSubject.validateRetrieveTrainee(new TraineeRetrievalByUsernameRequestDto("username"));

        Assertions.assertThat(restResponse).isEqualTo(null);

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateDeleteTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateDeleteTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateDeleteTraineeWhenUsernameIsNull() {
        RestResponse<TraineeDeletionResponseDto> restResponse =
            testSubject.validateDeleteTrainee(new TraineeDeletionByUsernameRequestDto(null));
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
            List.of("Username is required")));

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateDeleteTraineeWhenUsernameIsEmpty() {
        RestResponse<TraineeDeletionResponseDto> restResponse =
            testSubject.validateDeleteTrainee(new TraineeDeletionByUsernameRequestDto(""));
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
            List.of("Username is required")));

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateDeleteTraineeWhenUserDoesNotExist() {
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());

        RestResponse<TraineeDeletionResponseDto> restResponse =
            testSubject.validateDeleteTrainee(new TraineeDeletionByUsernameRequestDto("username"));
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(new RestResponse<>(null, HttpStatus.NOT_FOUND, timestamp,
            List.of("Trainee with a username of username not found")));

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateDeleteTraineeWhenValid() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(userEntity, Date.valueOf("2024-10-10"), "address");
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.of(traineeEntity));

        Assertions.assertThat(testSubject.validateDeleteTrainee(new TraineeDeletionByUsernameRequestDto("username")))
            .isNull();

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateDeleteTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenUsernameIsNull() {
        RestResponse<TraineeSwitchActivationStateResponseDto> restResponse =
            testSubject.validateSwitchActivationState(new TraineeSwitchActivationStateRequestDto(null));
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
            List.of("Username is required")));

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenUsernameIsEmpty() {
        RestResponse<TraineeSwitchActivationStateResponseDto> restResponse =
            testSubject.validateSwitchActivationState(new TraineeSwitchActivationStateRequestDto(""));
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp,
            List.of("Username is required")));

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenUserDoesNotExist() {
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());

        RestResponse<TraineeSwitchActivationStateResponseDto> restResponse =
            testSubject.validateSwitchActivationState(new TraineeSwitchActivationStateRequestDto("username"));
        LocalDateTime timestamp = restResponse.getTimestamp();

        Assertions.assertThat(restResponse).isEqualTo(new RestResponse<>(null, HttpStatus.NOT_FOUND, timestamp,
            List.of("Trainee with a username of username not found")));

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenValid() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(userEntity, Date.valueOf("2024-10-10"), "address");
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.of(traineeEntity));

        Assertions.assertThat(
                testSubject.validateSwitchActivationState(new TraineeSwitchActivationStateRequestDto("username")))
            .isNull();

        Mockito.verifyNoMoreInteractions(userService, traineeService);
    }

}