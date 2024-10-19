package org.example.validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerSwitchActivationStateResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class TrainerValidatorTest {

    private TrainerValidator testSubject;

    @Mock
    private TrainerService trainerService;

    @Mock
    private UserService userService;

    @Mock
    private TraineeService traineeService;

    @BeforeEach
    public void init() {
        testSubject = new TrainerValidator(trainerService, userService, traineeService);
    }

    @Test
    public void testValidateCreateTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateCreateTrainer(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateCreateTrainerWhenFirstNameIsNullOrEmpty() {
        TrainerCreationRequestDto requestDto1 = new TrainerCreationRequestDto("", "last", 1L);
        RestResponse<TrainerCreationResponseDto> restResponse1 = testSubject.validateCreateTrainer(requestDto1);
        LocalDateTime timestamp1 = restResponse1.getTimestamp();
        Assertions.assertThat(restResponse1).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp1, List.of("First name is required")));

        TrainerCreationRequestDto requestDto2 = new TrainerCreationRequestDto(null, "last", 1L);
        RestResponse<TrainerCreationResponseDto> restResponse2 = testSubject.validateCreateTrainer(requestDto2);
        LocalDateTime timestamp2 = restResponse2.getTimestamp();
        Assertions.assertThat(restResponse2).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp2, List.of("First name is required")));
    }

    @Test
    public void testValidateCreateTrainerWhenLastNameIsNullOrEmpty() {
        TrainerCreationRequestDto requestDto1 = new TrainerCreationRequestDto("first", "", 1L);
        RestResponse<TrainerCreationResponseDto> restResponse1 = testSubject.validateCreateTrainer(requestDto1);
        LocalDateTime timestamp1 = restResponse1.getTimestamp();
        Assertions.assertThat(restResponse1).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp1, List.of("Last name is required")));

        TrainerCreationRequestDto requestDto2 = new TrainerCreationRequestDto("first", null, 1L);
        RestResponse<TrainerCreationResponseDto> restResponse2 = testSubject.validateCreateTrainer(requestDto2);
        LocalDateTime timestamp2 = restResponse2.getTimestamp();
        Assertions.assertThat(restResponse2).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp2, List.of("Last name is required")));
    }

    @Test
    public void testValidateCreateTrainerWhenTrainingTypeIdIsInvalid() {
        TrainerCreationRequestDto requestDto1 = new TrainerCreationRequestDto("first", "last", -1L);
        RestResponse<TrainerCreationResponseDto> restResponse1 = testSubject.validateCreateTrainer(requestDto1);
        LocalDateTime timestamp1 = restResponse1.getTimestamp();
        Assertions.assertThat(restResponse1).isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp1,
            List.of("Specialization id must be a positive integer")));

        TrainerCreationRequestDto requestDto2 = new TrainerCreationRequestDto("first", "last", null);
        RestResponse<TrainerCreationResponseDto> restResponse2 = testSubject.validateCreateTrainer(requestDto2);
        LocalDateTime timestamp2 = restResponse2.getTimestamp();
        Assertions.assertThat(restResponse2).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp2, List.of("Specialization is required")));
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
        TrainerUpdateRequestDto requestDto1 = new TrainerUpdateRequestDto("", "first", "last", new TrainingTypeDto(
            TrainingType.AEROBIC), true);
        RestResponse<TrainerUpdateResponseDto> restResponse1 = testSubject.validateUpdateTrainer(requestDto1);
        LocalDateTime timestamp1 = restResponse1.getTimestamp();
        Assertions.assertThat(restResponse1).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp1, List.of("Username is required")));

        TrainerUpdateRequestDto requestDto2 = new TrainerUpdateRequestDto(null, "first", "last", new TrainingTypeDto(
            TrainingType.AEROBIC), true);
        RestResponse<TrainerUpdateResponseDto> restResponse2 = testSubject.validateUpdateTrainer(requestDto2);
        LocalDateTime timestamp2 = restResponse2.getTimestamp();
        Assertions.assertThat(restResponse2).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp2, List.of("Username is required")));
    }

    @Test
    public void testValidateUpdateTrainerWhenFirstNameIsInvalid() {
        TrainerUpdateRequestDto requestDto1 = new TrainerUpdateRequestDto("username", "", "last", new TrainingTypeDto(
            TrainingType.AEROBIC), true);
        RestResponse<TrainerUpdateResponseDto> restResponse1 = testSubject.validateUpdateTrainer(requestDto1);
        LocalDateTime timestamp1 = restResponse1.getTimestamp();
        Assertions.assertThat(restResponse1).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp1, List.of("First name is required")));

        TrainerUpdateRequestDto requestDto2 = new TrainerUpdateRequestDto("username", null, "last", new TrainingTypeDto(
            TrainingType.AEROBIC), true);
        RestResponse<TrainerUpdateResponseDto> restResponse2 = testSubject.validateUpdateTrainer(requestDto2);
        LocalDateTime timestamp2 = restResponse2.getTimestamp();
        Assertions.assertThat(restResponse2).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp2, List.of("First name is required")));
    }

    @Test
    public void testValidateUpdateTrainerWhenLastNameIsInvalid() {
        TrainerUpdateRequestDto requestDto1 = new TrainerUpdateRequestDto("username", "first", "", new TrainingTypeDto(
            TrainingType.AEROBIC), true);
        RestResponse<TrainerUpdateResponseDto> restResponse1 = testSubject.validateUpdateTrainer(requestDto1);
        LocalDateTime timestamp1 = restResponse1.getTimestamp();
        Assertions.assertThat(restResponse1).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp1, List.of("Last name is required")));

        TrainerUpdateRequestDto requestDto2 =
            new TrainerUpdateRequestDto("username", "first", null, new TrainingTypeDto(
                TrainingType.AEROBIC), true);
        RestResponse<TrainerUpdateResponseDto> restResponse2 = testSubject.validateUpdateTrainer(requestDto2);
        LocalDateTime timestamp2 = restResponse2.getTimestamp();
        Assertions.assertThat(restResponse2).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp2, List.of("Last name is required")));
    }

    @Test
    public void testValidateUpdateTrainerWhenSpecializationIsInvalid() {
        TrainerUpdateRequestDto requestDto1 =
            new TrainerUpdateRequestDto("username", "first", "last", null, true);
        RestResponse<TrainerUpdateResponseDto> restResponse1 = testSubject.validateUpdateTrainer(requestDto1);
        LocalDateTime timestamp1 = restResponse1.getTimestamp();
        Assertions.assertThat(restResponse1).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp1, List.of("Specialization is required")));
    }

    @Test
    public void testValidateUpdateTrainerWhenIsActiveIsInvalid() {
        TrainerUpdateRequestDto requestDto1 = new TrainerUpdateRequestDto("username", "first", "last", null, null);
        RestResponse<TrainerUpdateResponseDto> restResponse1 = testSubject.validateUpdateTrainer(requestDto1);
        LocalDateTime timestamp1 = restResponse1.getTimestamp();
        Assertions.assertThat(restResponse1).isEqualTo(
            new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp1, List.of("Specialization is required")));
    }

    @Test
    public void testValidateUpdateTrainerWhenUserDoesNotExist() {
        Mockito.when(userService.findByUsername("username")).thenReturn(Optional.empty());
        RestResponse<TrainerUpdateResponseDto> actual = testSubject.validateUpdateTrainer(new TrainerUpdateRequestDto(
            "username", "first", "last", new TrainingTypeDto(TrainingType.AEROBIC), true
        ));
        Assertions.assertThat(actual)
            .isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, actual.getTimestamp(),
                List.of("User does not exist"))
            );
    }

    @Test
    public void testValidateRetrieveTrainerWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateRetrieveTrainer(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testValidateRetrieveTrainerWhenUsernameIsInvalid() {
        TrainerRetrievalByUsernameRequestDto requestDto = new TrainerRetrievalByUsernameRequestDto("");
        RestResponse<TrainerRetrievalResponseDto> restResponse = testSubject.validateRetrieveTrainer(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();
        Assertions.assertThat(restResponse)
            .isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp, List.of("Username is required")));
    }

    @Test
    public void testValidateRetrieveTrainerWhenUserDoesNotExist() {
        Mockito.when(trainerService.findByUsername("username")).thenReturn(Optional.empty());
        RestResponse<TrainerRetrievalResponseDto> restResponse =
            testSubject.validateRetrieveTrainer(new TrainerRetrievalByUsernameRequestDto("username"));
        Assertions.assertThat(restResponse)
            .isEqualTo(new RestResponse<>(null, HttpStatus.NOT_FOUND, restResponse.getTimestamp(),
                List.of("Trainee with a username of username not found")));
        Mockito.verifyNoMoreInteractions(trainerService, userService, traineeService);
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
        RestResponse<TrainerSwitchActivationStateResponseDto> restResponse = testSubject.validateSwitchActivationState(requestDto);
        LocalDateTime timestamp = restResponse.getTimestamp();
        Assertions.assertThat(restResponse)
            .isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp, List.of("Username is required")));

        TrainerSwitchActivationStateRequestDto requestDto2 = new TrainerSwitchActivationStateRequestDto(null);
        RestResponse<TrainerSwitchActivationStateResponseDto> restResponse2 = testSubject.validateSwitchActivationState(requestDto2);
        LocalDateTime timestamp2 = restResponse2.getTimestamp();
        Assertions.assertThat(restResponse2)
            .isEqualTo(new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, timestamp2, List.of("Username is required")));
    }

    @Test
    public void testValidateTrainerSwitchActivationStateWhenUserDoesNotExist() {
        Mockito.when(trainerService.findByUsername("username")).thenReturn(Optional.empty());
        RestResponse<TrainerSwitchActivationStateResponseDto> restResponse =
            testSubject.validateSwitchActivationState(new TrainerSwitchActivationStateRequestDto("username"));
        Assertions.assertThat(restResponse)
            .isEqualTo(new RestResponse<>(null, HttpStatus.NOT_FOUND, restResponse.getTimestamp(),
                List.of("Trainee with a username of username not found")));
        Mockito.verifyNoMoreInteractions(trainerService, userService, traineeService);
    }

    @Test
    public void testValidateTrainerSwitchActivationStateWhenValid() {
        Mockito.when(trainerService.findByUsername("username")).thenReturn(Optional.of(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        )));
        Assertions.assertThat(testSubject.validateSwitchActivationState(new TrainerSwitchActivationStateRequestDto("username")))
            .isNull();
    }
}