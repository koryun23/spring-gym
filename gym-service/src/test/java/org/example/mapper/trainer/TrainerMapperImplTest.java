package org.example.mapper.trainer;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingEntity;
import org.example.entity.training.TrainingType;
import org.example.entity.training.TrainingTypeEntity;
import org.example.entity.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerMapperImplTest {

    private TrainerMapper testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainerMapperImpl();
    }

    @Test
    public void testMapTrainerEntityToTrainerRetrievalResponseDtoWhenNoTrainings() {
        Assertions.assertThat(testSubject.mapTrainerEntityToTrainerRetrievalResponseDto(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        ))).isEqualTo(new TrainerRetrievalResponseDto(
            "username", "first", "last", TrainingType.AEROBIC, true, Collections.emptyList()
        ));
    }

    //TODO: implement
    @Test
    public void testMapTrainerEntityToTrainerRetrievalResponseDtoWhenTrainingsExist() {

    }

    @Test
    public void testMapTrainerDtoToTrainerCreationResponseDtoWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.mapTrainerDtoToTrainerCreationResponseDto(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMapTrainerDtoToTrainerCreationResponseDto() {
        TrainerCreationResponseDto actual = testSubject.mapTrainerDtoToTrainerCreationResponseDto(
            new TrainerDto(
                new UserDto("first", "last", "username", "password", true),
                1L
            ));
        Assertions
            .assertThat(actual).isEqualTo(new TrainerCreationResponseDto("username", "password"));
    }

    @Test
    public void testMapTrainerUpdateRequestDtoToTrainerDto() {

        // given
        TrainerUpdateRequestDto trainerUpdateRequestDto =
            new TrainerUpdateRequestDto("username", "first", "last", 1L, true);
        TrainerDto trainerDto = new TrainerDto(new UserDto("first", "last", "username", null, true), 1L);

        // then
        Assertions.assertThat(testSubject.mapTrainerUpdateRequestDtoToTrainerDto(trainerUpdateRequestDto))
            .isEqualTo(trainerDto);
    }

    @Test
    public void testMapTrainerUpdateRequestDtoToTrainerDtoWhenRequestIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.mapTrainerUpdateRequestDtoToTrainerEntity(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMapTrainerCreationRequestDtoToTrainerDto() {

        // given
        TrainerCreationRequestDto trainerCreationRequestDto = new TrainerCreationRequestDto("first", "last", 1L);
        TrainerDto trainerDto = new TrainerDto(new UserDto("first", "last", null, null, true), 1L);

        // then
        Assertions.assertThat(testSubject.mapTrainerCreationRequestDtoToTrainerDto(trainerCreationRequestDto))
            .isEqualTo(trainerDto);
    }

    @Test
    public void testMapTrainerCreationRequestDtoToTrainerDtoWhenRequestIsNull() {

        Assertions.assertThatThrownBy(() -> testSubject.mapTrainerCreationRequestDtoToTrainerDto(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMapTrainerEntityToUserDto() {

        // given
        TrainerEntity trainerEntity = new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC));
        UserDto userDto = new UserDto("first", "last", "username", "password", true);

        // then
        Assertions.assertThat(testSubject.mapTrainerEntityToUserDto(trainerEntity)).isEqualTo(userDto);
    }

    @Test
    public void testMapTrainerEntityToUserDtoWhenTrainerEntityIsNull() {

        Assertions.assertThatThrownBy(() -> testSubject.mapTrainerEntityToTrainerDto(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMapTrainerEntityToTrainerDto() {

        // given
        TrainingTypeEntity specialization = new TrainingTypeEntity(TrainingType.STRENGTH_TRAINING);
        specialization.setId(1L);
        TrainerEntity trainerEntity = new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            specialization);
        TrainerDto trainerDto = new TrainerDto(new UserDto("first", "last", "username", "password", true), 1L);

        // then
        Assertions.assertThat(testSubject.mapTrainerEntityToTrainerDto(trainerEntity)).isEqualTo(trainerDto);
    }

    @Test
    public void testMapTrainerUpdateRequestDtoToTrainerEntity() {

        // given
        TrainerUpdateRequestDto trainerUpdateRequestDto =
            new TrainerUpdateRequestDto("username", "first", "last", 1L, true);
        TrainerEntity trainerEntity = new TrainerEntity(new UserEntity("first", "last", "username", null, true),
            new TrainingTypeEntity(TrainingType.STRENGTH_TRAINING));

        // then
        Assertions.assertThat(testSubject.mapTrainerUpdateRequestDtoToTrainerEntity(trainerUpdateRequestDto))
            .isEqualTo(trainerEntity);
    }

    @Test
    public void testMapTrainerEntityToTrainerUpdateResponseDto() {

        // given
        TrainerEntity trainerEntity = new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        );
        TraineeEntity traineeEntity = new TraineeEntity(
            new UserEntity("f", "l", "u", "p", true),
            null, null
        );
        TrainingEntity trainingEntity1 = new TrainingEntity(traineeEntity, trainerEntity, "training",
            new TrainingTypeEntity(TrainingType.AEROBIC), Date.valueOf("2024-10-10"), 1000L);
        TrainingEntity trainingEntity2 = new TrainingEntity(traineeEntity, trainerEntity, "training",
            new TrainingTypeEntity(TrainingType.AEROBIC), Date.valueOf("2024-11-11"), 1000L);

        trainerEntity.setTrainingEntityList(List.of(trainingEntity1, trainingEntity2));

        TraineeDto traineeDto = new TraineeDto(new UserDto("f", "l", "u", "p", true), null, null);
        List<TraineeDto> traineeDtoList = List.of(traineeDto, traineeDto);
        TrainerUpdateResponseDto responseDto = new TrainerUpdateResponseDto(
            "username", "first", "last", TrainingType.AEROBIC, true, traineeDtoList
        );

        // then
        Assertions.assertThat(testSubject.mapTrainerEntityToTrainerUpdateResponseDto(trainerEntity))
            .isEqualTo(responseDto);
    }
}