package org.example.validator;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.user.UserEntity;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.TraineeNotFoundException;
import org.example.service.core.trainee.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TraineeValidatorTest {

    private TraineeValidator testSubject;

    @Mock
    private TraineeService traineeService;

    @BeforeEach
    public void init() {
        testSubject = new TraineeValidator(traineeService);
    }

    @Test
    public void testValidateCreateTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainee(null))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTraineeWhenFirstNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainee(new TraineeCreationRequestDto(
            null, "last", null, null
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTraineeWhenFirstNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainee(new TraineeCreationRequestDto(
            "", "last", null, null
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTraineeWhenLastNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainee(new TraineeCreationRequestDto(
            "first", null, null, null
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTraineeWhenLastNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainee(new TraineeCreationRequestDto(
            "first", "", null, null
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTraineeWhenValid() {
        Assertions.assertThat(testSubject.validateCreateTrainee(new TraineeCreationRequestDto(
            "first", "last", null, null
        ))).isNull();
    }

    @Test
    public void testValidateUpdateTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(null))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", null, true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenUsernameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenFirstNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            null, "last", "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenFirstNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "", "last", "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenLastNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", null, "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenLastNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "", "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenIsActiveIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "username", null, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenUserDoesNotExist() {

        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(TraineeNotFoundException.class);
    }

    @Test
    public void testValidateUpdateTraineeWhenValid() {
        UserEntity userEntity = new UserEntity("first", "last", "username",
            "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(userEntity, Date.valueOf("2024-10-10"), "address");
        Mockito.when(traineeService.findByUsername("username"))
            .thenReturn(Optional.of(traineeEntity));

        Assertions.assertThat(testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isNull();
    }

    @Test
    public void testValidateRetrieveTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveTrainee(null))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTraineeWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", null, true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);

        Mockito.verifyNoMoreInteractions(traineeService);
    }

    @Test
    public void testValidateRetrieveTraineeWhenUsernameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);

        Mockito.verifyNoMoreInteractions(traineeService);
    }

    @Test
    public void testValidateRetrieveTraineeWhenUserDoesNotExist() {
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(
            () -> testSubject.validateRetrieveTrainee("username")).isExactlyInstanceOf(TraineeNotFoundException.class);
    }

    @Test
    public void testValidateRetrieveTraineeWhenValid() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(userEntity, null, null);
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.of(traineeEntity));
        Assertions.assertThat(testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isNull();
        Mockito.verifyNoMoreInteractions(traineeService);
    }

    @Test
    public void testValidateDeleteTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateDeleteTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateDeleteTraineeWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", null, true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);

        Mockito.verifyNoMoreInteractions(traineeService);
    }

    @Test
    public void testValidateDeleteTraineeWhenUsernameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);

        Mockito.verifyNoMoreInteractions(traineeService);
    }

    @Test
    public void testValidateDeleteTraineeWhenUserDoesNotExist() {
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(TraineeNotFoundException.class);
    }

    @Test
    public void testValidateDeleteTraineeWhenValid() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(userEntity, Date.valueOf("2024-10-10"), "address");
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.of(traineeEntity));

        Assertions.assertThat(testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isNull();
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateDeleteTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", null, true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);

        Mockito.verifyNoMoreInteractions(traineeService);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenUsernameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "", true, Date.valueOf("2024-10-10"), "address"
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);

        Mockito.verifyNoMoreInteractions(traineeService);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenUserDoesNotExist() {
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(
            () -> testSubject.validateSwitchActivationState(new TraineeSwitchActivationStateRequestDto(
                "username", true
            ))).isExactlyInstanceOf(TraineeNotFoundException.class);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenValid() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TraineeEntity traineeEntity = new TraineeEntity(userEntity, Date.valueOf("2024-10-10"), "address");
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.of(traineeEntity));

        Assertions.assertThat(testSubject.validateUpdateTrainee(new TraineeUpdateRequestDto(
            "first", "last", "username", true, Date.valueOf("2024-10-10"), "address"
        ))).isNull();

        Mockito.verifyNoMoreInteractions(traineeService);
    }

}