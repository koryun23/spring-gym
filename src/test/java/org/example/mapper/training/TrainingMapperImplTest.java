package org.example.mapper.training;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.example.dto.plain.TrainingDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TraineeTrainingRetrievalResponseDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class TrainingMapperImplTest {

    private TrainingMapper testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainingMapperImpl();
    }

    @Test
    public void testMapTrainingEntityToTrainingCreationResponseDto() {
        Assertions.assertThat(testSubject.mapTrainingEntityToTrainingCreationResponseDto(
            new TrainingEntity(null, null, null, null, null, null))
        ).isEqualTo(new TrainingCreationResponseDto(HttpStatus.OK));
    }

    @Test
    public void testMapTrainingEntityToTrainingDto() {
        Assertions.assertThat(testSubject.mapTrainingEntityToTrainingDto(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("trainee", "trainee", "trainee", "trainee", true),
                null, null
            ),
            new TrainerEntity(
                new UserEntity("trainer", "trainer", "trainer", "trainer", true),
                new TrainingTypeEntity(TrainingType.AEROBIC)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.AEROBIC),
                Date.valueOf("2024-10-10"),
            1000L
            ))).isEqualTo(new TrainingDto(
                "trainee", "trainer", "training", Date.valueOf("2024-10-10"), 1000L
        ));
    }

    @Test
    public void testMapTrainingCreationRequestDtoToTrainingDto() {
        Assertions.assertThat(testSubject.mapTrainingCreationRequestDtoToTrainingDto(new TrainingCreationRequestDto(
            "trainee", "trainer", "training", Date.valueOf("2024-10-10"), 1000L
        ))).isEqualTo(new TrainingDto(
            "trainee", "trainer", "training", Date.valueOf("2024-10-10"), 1000L
        ));
    }

    @Test
    public void testMapTrainingEntityToTraineeTrainingRetrievalResponseDto() {
        TrainingTypeEntity trainingType = new TrainingTypeEntity(TrainingType.AEROBIC);
        trainingType.setId(1L);
        Assertions.assertThat(testSubject.mapTrainingEntityToTraineeTrainingRetrievalResponseDto(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("trainee", "trainee", "trainee", "trainee", true),
                null, null
            ),
            new TrainerEntity(
                new UserEntity("trainer", "trainer", "trainer", "trainer", true),
                trainingType
            ),
            "training",
            trainingType,
            Date.valueOf("2024-10-10"),
            1000L
        ))).isEqualTo(new TraineeTrainingRetrievalResponseDto(
            "training", Date.valueOf("2024-10-10"), 1L, 1000L, "trainer"
        ));
    }
}