package org.example.validator;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainingValidatorTest {

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    private TrainingValidator testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainingValidator(trainerService, traineeService);
    }

    @Test
    public void testValidateCreateTrainingWhenTraineeUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            null, "trainee", "training", Date.valueOf("2024-10-10"), 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainingWhenTraineeUsernameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "", "trainee", "training", Date.valueOf("2024-10-10"), 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainingWhenTrainerUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", null, "training", Date.valueOf("2024-10-10"), 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainingWhenTrainerUsernameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", "", "training", Date.valueOf("2024-10-10"), 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainingWhenTrainingNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", "trainee", null, Date.valueOf("2024-10-10"), 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainingWhenTrainingNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", "trainee", "", Date.valueOf("2024-10-10"), 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainingWhenDurationIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", "trainer", null, Date.valueOf("2024-10-10"), 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainingWhenDurationIsNegative() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", "trainer", "training", Date.valueOf("2024-10-10"), -1L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainingWhenDateIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", "trainer", "training", null, 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainingWhenTraineeDoesNotExist() {
        Mockito.when(traineeService.findByUsername("trainee")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", "trainer", "training", Date.valueOf("2024-10-10"), 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class).hasMessage("Trainee does not exist");

        Mockito.verifyNoMoreInteractions(traineeService);
        Mockito.verifyNoInteractions(trainerService);
    }

    @Test
    public void testValidateCreateTrainingWhenTrainerDoesNotExist() {
        Mockito.when(traineeService.findByUsername("trainee")).thenReturn(Optional.of(
            new TraineeEntity(
                new UserEntity("first", "last", "trainee", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            )
        ));

        Mockito.when(trainerService.findByUsername("trainer")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", "trainer", "training", Date.valueOf("2024-10-10"), 1000L
        ))).isExactlyInstanceOf(CustomIllegalArgumentException.class).hasMessage("Trainer does not exist");

        Mockito.verifyNoMoreInteractions(traineeService, trainerService);
    }

    @Test
    public void testValidateCreateTrainingWhenValid() {
        Mockito.when(traineeService.findByUsername("trainee")).thenReturn(Optional.of(
            new TraineeEntity(
                new UserEntity("first", "last", "trainee", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            )
        ));

        Mockito.when(trainerService.findByUsername("trainer")).thenReturn(Optional.of(
            new TrainerEntity(
                new UserEntity("first", "last", "trainer", "password", true),
                new TrainingTypeEntity(TrainingType.AEROBIC)
            )
        ));

        Assertions.assertThat(testSubject.validateCreateTraining(new TrainingCreationRequestDto(
            "trainee", "trainer", "training", Date.valueOf("2024-10-10"), 1000L
        ))).isNull();

        Mockito.verifyNoMoreInteractions(traineeService, trainerService);
    }

    @Test
    public void testValidateRetrieveTrainingListByTrainerWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(
            () -> testSubject.validateRetrieveTrainingListByTrainer(new TrainingListRetrievalByTrainerRequestDto(
                null, null, null, null
            ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTrainingListByTrainerWhenUsernameIsEmpty() {
        Assertions.assertThatThrownBy(
            () -> testSubject.validateRetrieveTrainingListByTrainer(new TrainingListRetrievalByTrainerRequestDto(
                "", null, null, null
            ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTrainingListByTrainerWhenTrainerDoesNotExist() {
        Mockito.when(trainerService.findByUsername("trainer")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(
            () -> testSubject.validateRetrieveTrainingListByTrainer(new TrainingListRetrievalByTrainerRequestDto(
                "trainer", null, null, null
            ))).isExactlyInstanceOf(TrainerNotFoundException.class);

        Mockito.verifyNoMoreInteractions(trainerService);
        Mockito.verifyNoInteractions(traineeService);
    }

    @Test
    public void testValidateRetrieveTrainingListByTrainerWhenValid() {
        Mockito.when(trainerService.findByUsername("trainer")).thenReturn(Optional.of(new TrainerEntity()));

        Assertions.assertThat(
            testSubject.validateRetrieveTrainingListByTrainer(new TrainingListRetrievalByTrainerRequestDto(
                "trainer", null, null, null
            ))).isNull();

        Mockito.verifyNoMoreInteractions(trainerService);
        Mockito.verifyNoInteractions(traineeService);
    }

    @Test
    public void testValidateRetrieveTrainingListByTraineeWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(
            () -> testSubject.validateRetrieveTrainingListByTrainee(new TrainingListRetrievalByTraineeRequestDto(
                null, null, null, null, null
            ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTrainingListByTraineeWhenUsernameIsEmpty() {
        Assertions.assertThatThrownBy(
            () -> testSubject.validateRetrieveTrainingListByTrainee(new TrainingListRetrievalByTraineeRequestDto(
                "", null, null, null, null
            ))).isExactlyInstanceOf(CustomIllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTrainingListByTraineeWhenTraineeDoesNotExist() {
        Mockito.when(traineeService.findByUsername("trainee")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(
            () -> testSubject.validateRetrieveTrainingListByTrainee(new TrainingListRetrievalByTraineeRequestDto(
                "trainee", null, null, null, null
            ))).isExactlyInstanceOf(TraineeNotFoundException.class);

        Mockito.verifyNoMoreInteractions(traineeService);
        Mockito.verifyNoInteractions(trainerService);
    }

    @Test
    public void testValidateRetrieveTrainingListByTraineeWhenValid() {
        Mockito.when(traineeService.findByUsername("trainee")).thenReturn(Optional.of(new TraineeEntity()));

        Assertions.assertThat(
            testSubject.validateRetrieveTrainingListByTrainee(new TrainingListRetrievalByTraineeRequestDto(
                "trainee", null, null, null, null
            ))).isNull();
    }
}