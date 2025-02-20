package org.example.validator;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.user.UserEntity;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.training.TrainingTypeService;
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

    @Mock
    private TrainingTypeService trainingTypeService;

    @BeforeEach
    public void init() {
        testSubject = new TrainerValidator(trainerService, traineeService, trainingTypeService);
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
    public void testValidateRetrieveAllTrainersNotAssignedToTraineeWhenRequestIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveAllTrainersNotAssignedToTrainee(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveAllTrainersNotAssignedToTraineeWhenUsernameIsNull() {

        // given
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto =
            new RetrieveAllTrainersNotAssignedToTraineeRequestDto(null);

        // then
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveAllTrainersNotAssignedToTrainee(requestDto))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveAllTrainersNotAssignedToTraineeWhenUsernameIsEmpty() {
        // given
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto =
            new RetrieveAllTrainersNotAssignedToTraineeRequestDto("");

        // then
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveAllTrainersNotAssignedToTrainee(requestDto))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveAllTrainersNotAssignedToTraineeWhenUserDoesNotExist() {

        // given
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto =
            new RetrieveAllTrainersNotAssignedToTraineeRequestDto("username");

        // when
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveAllTrainersNotAssignedToTrainee(requestDto))
            .isExactlyInstanceOf(TraineeNotFoundException.class);

    }

    @Test
    public void testValidateRetrieveAllTrainersNotAssignedToTrainee() {
        // given
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto =
            new RetrieveAllTrainersNotAssignedToTraineeRequestDto("username");

        // when
        Mockito.when(traineeService.findByUsername("username")).thenReturn(Optional.of(new TraineeEntity(
            new UserEntity(),
            null, null
        )));

        // then
        Assertions.assertThatNoException()
            .isThrownBy(() -> testSubject.validateRetrieveAllTrainersNotAssignedToTrainee(requestDto));


    }

}