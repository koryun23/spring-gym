package org.example.validator;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.TrainerNotFoundException;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainerValidatorTest {

    private TrainerValidator testSubject;

    @Mock
    private TrainerService trainerService;

    @Mock
    private UserService userService;

    @Mock
    private TraineeService traineeService;

    // TODO: IMPLEMENT NORMALLY
    @BeforeEach
    public void init() {
        testSubject = new TrainerValidator(trainerService, traineeService, null);
    }

    @Test
    public void testValidateCreateTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainer(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainerWhenFirstNameIsNullOrEmpty() {
        TrainerCreationRequestDto requestDto1 = new TrainerCreationRequestDto("", "last", 1L);
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainer(requestDto1))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainerWhenLastNameIsNullOrEmpty() {
        TrainerCreationRequestDto requestDto1 = new TrainerCreationRequestDto("first", "", 1L);
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainer(requestDto1))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainerWhenTrainingTypeIdIsInvalid() {
        TrainerCreationRequestDto requestDto1 = new TrainerCreationRequestDto("first", "last", -1L);
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainer(requestDto1))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainerWhenValid() {
        TrainerCreationRequestDto requestDto1 = new TrainerCreationRequestDto("first", "last", 1L);
        Assertions.assertThat(testSubject.validateCreateTrainer(requestDto1)).isNull();
    }

    @Test
    public void testValidateUpdateTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainer(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTrainerWhenUsernameIsInvalid() {
        TrainerUpdateRequestDto requestDto1 = new TrainerUpdateRequestDto("", "first", "last", 1L, true);
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainer(requestDto1)).isExactlyInstanceOf(
            CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTrainerWhenFirstNameIsInvalid() {
        TrainerUpdateRequestDto requestDto1 = new TrainerUpdateRequestDto("username", "", "last", 1L, true);
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainer(requestDto1)).isExactlyInstanceOf(
            CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTrainerWhenLastNameIsInvalid() {
        TrainerUpdateRequestDto requestDto1 = new TrainerUpdateRequestDto("username", "first", "", 1L, true);
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainer(requestDto1))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTrainerWhenSpecializationIsInvalid() {
        TrainerUpdateRequestDto requestDto1 =
            new TrainerUpdateRequestDto("username", "first", "last", null, true);
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainer(requestDto1))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTrainerWhenIsActiveIsInvalid() {
        TrainerUpdateRequestDto requestDto1 = new TrainerUpdateRequestDto("username", "first", "last", null, null);
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainer(requestDto1))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateUpdateTrainerWhenUserDoesNotExist() {
        Assertions.assertThatThrownBy(() -> testSubject.validateUpdateTrainer(new TrainerUpdateRequestDto(
            "username", "first", "last", 1L, true
        ))).isExactlyInstanceOf(TrainerNotFoundException.class);
    }

    @Test
    public void testValidateRetrieveTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveTrainer(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTrainerWhenUsernameIsInvalid() {
        TrainerRetrievalByUsernameRequestDto requestDto = new TrainerRetrievalByUsernameRequestDto("");
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveTrainer(requestDto))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTrainerWhenUserDoesNotExist() {
        Mockito.when(trainerService.findByUsername("username")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(
                () -> testSubject.validateRetrieveTrainer(new TrainerRetrievalByUsernameRequestDto("username")))
            .isExactlyInstanceOf(TrainerNotFoundException.class);
    }

    @Test
    public void testValidateRetrieveTrainerWhenValid() {
        Mockito.when(trainerService.findByUsername("username")).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        Assertions.assertThat(testSubject.validateRetrieveTrainer(new TrainerRetrievalByUsernameRequestDto("username")))
            .isNull();
    }

    @Test
    public void testValidateTrainerSwitchActivationStateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateSwitchActivationState(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateTrainerSwitchActivationStateWhenUsernameIsInvalid() {
        TrainerSwitchActivationStateRequestDto requestDto = new TrainerSwitchActivationStateRequestDto("");
        Assertions.assertThatThrownBy(() -> testSubject.validateSwitchActivationState(requestDto))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateTrainerSwitchActivationStateWhenUserDoesNotExist() {
        Mockito.when(trainerService.findByUsername("username")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(
                () -> testSubject.validateSwitchActivationState(new TrainerSwitchActivationStateRequestDto("username")))
            .isExactlyInstanceOf(TrainerNotFoundException.class);
        Mockito.verifyNoMoreInteractions(trainerService, userService, traineeService);
    }

    @Test
    public void testValidateTrainerSwitchActivationStateWhenValid() {
        Mockito.when(trainerService.findByUsername("username")).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        Assertions.assertThat(
                testSubject.validateSwitchActivationState(new TrainerSwitchActivationStateRequestDto("username")))
            .isNull();
    }
}