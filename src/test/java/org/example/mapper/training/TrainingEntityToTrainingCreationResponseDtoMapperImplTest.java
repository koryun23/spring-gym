package org.example.mapper.training;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingEntityToTrainingCreationResponseDtoMapperImplTest {

    private TrainingEntityToTrainingCreationResponseDtoMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainingEntityToTrainingCreationResponseDtoMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        Assertions.assertThat(testSubject.map(new TrainingEntity(
            1L,
            1L,
            1L,
            "name",
            TrainingType.AEROBIC,
            Date.valueOf("2024-10-10"),
            1000L
        ))).isEqualTo(new TrainingCreationResponseDto(
            1L,
            1L,
            1L,
            "name",
            TrainingType.AEROBIC,
            Date.valueOf("2024-10-10"),
            1000L
        ));
    }

}