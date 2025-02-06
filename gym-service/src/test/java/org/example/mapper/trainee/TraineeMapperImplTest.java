package org.example.mapper.trainee;

import java.sql.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingEntity;
import org.example.entity.training.TrainingType;
import org.example.entity.training.TrainingTypeEntity;
import org.example.entity.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraineeMapperImplTest {

    private TraineeMapper testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TraineeMapperImpl();
    }

    @Test
    public void testMapTraineeCreationRequestDtoToTraineeEntity() {
        Assertions.assertThat(testSubject.mapTraineeCreationRequestDtoToTraineeEntity(
            new TraineeCreationRequestDto(
                "first", "last", null, "address"
            )
        )).isEqualTo(new TraineeEntity(
            new UserEntity(
                "first", "last", null, null, true
            ),
            null,
            "address"
        ));
    }

    @Test
    public void testMapTraineeEntityToTraineeRetrievalResponseDtoWhenNoTrainings() {
        Assertions.assertThat(testSubject.mapTraineeEntityToTraineeRetrievalResponseDto(
            new TraineeEntity(new UserEntity("first", "last", "username", "password", true), null, null)
        )).isEqualTo(new TraineeRetrievalResponseDto("first", "last", null, null, true, null));
    }

    @Test
    public void testMapTraineeEntityToTraineeRetrievalResponseDtoWhenTrainingsExist() {

        // given
        TraineeEntity traineeEntity =
            new TraineeEntity(new UserEntity("first", "last", "username", "password", true), null, null);

        TrainingTypeEntity specialization = new TrainingTypeEntity(
            TrainingType.AEROBIC);
        specialization.setId(1L);

        TrainerEntity trainerEntity =
            new TrainerEntity(new UserEntity("first", "last", "trainer", "password", true), specialization);

        TrainingEntity trainingEntity1 =
            new TrainingEntity(traineeEntity, trainerEntity, "training-1", specialization, Date.valueOf("2024-10-10"),
                1000L);
        TrainingEntity trainingEntity2 =
            new TrainingEntity(traineeEntity, trainerEntity, "training-2", specialization, Date.valueOf("2024-10-10"),
                1000L);
        TrainingEntity trainingEntity3 =
            new TrainingEntity(traineeEntity, trainerEntity, "training-3", specialization, Date.valueOf("2024-10-10"),
                1000L);

        List<TrainingEntity> trainingEntityList = List.of(trainingEntity1, trainingEntity2, trainingEntity3);
        traineeEntity.setTrainingEntityList(trainingEntityList);

        TrainerDto trainerDto = new TrainerDto(new UserDto("first", "last", "trainer", "password", true), 1L);
        List<TrainerDto> assignedTrainerList = List.of(trainerDto);

        // then
        Assertions.assertThat(testSubject.mapTraineeEntityToTraineeRetrievalResponseDto(traineeEntity))
            .isEqualTo(new TraineeRetrievalResponseDto(
                "first", "last", null, null, true, assignedTrainerList
            ));

    }

    @Test
    public void testMapTraineeEntityToTraineeUpdateResponseDtoWhenNoTrainings() {
        Assertions.assertThat(testSubject.mapTraineeEntityToTraineeUpdateResponseDto(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            null, null
        ))).isEqualTo(new TraineeUpdateResponseDto(
            "username", "first", "last", null, null, true, null
        ));
    }

    @Test
    public void testMapTraineeEntityToTraineeUpdateResponseDtoWhenTrainingsExist() {
        // given
        TraineeEntity traineeEntity =
            new TraineeEntity(new UserEntity("first", "last", "username", "password", true), null, null);

        TrainingTypeEntity specialization = new TrainingTypeEntity(
            TrainingType.AEROBIC);
        specialization.setId(1L);

        TrainerEntity trainerEntity =
            new TrainerEntity(new UserEntity("first", "last", "trainer", "password", true), specialization);

        TrainingEntity trainingEntity1 =
            new TrainingEntity(traineeEntity, trainerEntity, "training-1", specialization, Date.valueOf("2024-10-10"),
                1000L);
        TrainingEntity trainingEntity2 =
            new TrainingEntity(traineeEntity, trainerEntity, "training-2", specialization, Date.valueOf("2024-10-10"),
                1000L);
        TrainingEntity trainingEntity3 =
            new TrainingEntity(traineeEntity, trainerEntity, "training-3", specialization, Date.valueOf("2024-10-10"),
                1000L);

        List<TrainingEntity> trainingEntityList = List.of(trainingEntity1, trainingEntity2, trainingEntity3);
        traineeEntity.setTrainingEntityList(trainingEntityList);

        TrainerDto trainerDto = new TrainerDto(new UserDto("first", "last", "trainer", "password", true), 1L);
        List<TrainerDto> assignedTrainerList = List.of(trainerDto);

        // then
        Assertions.assertThat(testSubject.mapTraineeEntityToTraineeUpdateResponseDto(traineeEntity))
            .isEqualTo(new TraineeUpdateResponseDto(
                "username", "first", "last", null, null, true, assignedTrainerList
            ));

    }

    @Test
    public void testMapTraineeUpdateRequestDtoToTraineeEntity() {
        Assertions.assertThat(testSubject.mapTraineeUpdateRequestDtoToTraineeEntity(new TraineeUpdateRequestDto(
            "first", "last", "username", true, null, null
        ))).isEqualTo(new TraineeEntity(
            new UserEntity("first", "last", "username", null, true),
            null, null
        ));
    }

    @Test
    public void testMapTraineeDtoToTraineeCreationResponseDto() {
        Assertions.assertThat(testSubject.mapTraineeDtoToTraineeCreationResponseDto(new TraineeDto(
            new UserDto("first", "last", "username", "password", true),
            null, null
        ))).isEqualTo(new TraineeCreationResponseDto("username", "password"));
    }

    @Test
    public void testMapTraineeDtoToTraineeCreationResponseDtoWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.mapTraineeDtoToTraineeCreationResponseDto(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}