package org.example.validator;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
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
    public void testValidateRetrieveTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveTrainee(null))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTraineeWhenUserDoesNotExist() {
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(
            () -> testSubject.validateRetrieveTrainee("username")).isExactlyInstanceOf(TraineeNotFoundException.class);
    }


    @Test
    public void testValidateDeleteTraineeWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateDeleteTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateDeleteTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateTraineeSwitchActiveStateWhenUserDoesNotExist() {
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(
            () -> testSubject.validateSwitchActivationState(new TraineeSwitchActivationStateRequestDto(
                "username", true
            ))).isExactlyInstanceOf(TraineeNotFoundException.class);
    }
}