package org.example.mapper.training;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingCreationRequestDtoToTrainingEntityMapperImplTest {

    private TrainingCreationRequestDtoToTrainingEntityMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainingCreationRequestDtoToTrainingEntityMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        TrainingCreationRequestDto requestDto = new TrainingCreationRequestDto(
            1L,
            1L,
            "name",
            TrainingType.AEROBIC,
            Date.valueOf("2024-10-10"),
            1000L
        );
        requestDto.setTrainingId(1L);
        Assertions.assertThat(testSubject.map(requestDto)).isEqualTo(new TrainingEntity(
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