package org.example.mapper.trainer;

import org.assertj.core.api.Assertions;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerMapperImplTest {

    private TrainerMapper testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainerMapperImpl();
    }

    @Test
    public void testMapTrainerEntityToTrainerCreationResponseDto() {
        Assertions.assertThat(testSubject.mapTrainerEntityToTrainerCreationResponseDto(new TrainerEntity(
            new UserEntity(null, null, "username", "password", true),
            new TrainingTypeEntity(TrainingType.FLEXIBILITY_TRAINING)
        ))).isEqualTo(new TrainerCreationResponseDto("username", "password"));
    }

    @Test
    public void testMapTrainerEntityToTrainerRetrievalResponseDto() {
        Assertions.assertThat(testSubject.mapTrainerEntityToTrainerRetrievalResponseDto(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        ))).isEqualTo(new TrainerRetrievalResponseDto(
            "username", "first", "last", TrainingType.AEROBIC, true, null
        ));
    }
}