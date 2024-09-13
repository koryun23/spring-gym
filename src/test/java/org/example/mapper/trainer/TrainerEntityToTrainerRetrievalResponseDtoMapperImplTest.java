package org.example.mapper.trainer;

import org.assertj.core.api.Assertions;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.entity.SpecializationType;
import org.example.entity.TrainerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerEntityToTrainerRetrievalResponseDtoMapperImplTest {

    private TrainerEntityToTrainerRetrievalResponseDtoMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainerEntityToTrainerRetrievalResponseDtoMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        Assertions.assertThat(testSubject.map(new TrainerEntity(
            1L,
            "first",
            "last",
            "username",
            "password",
            true,
            SpecializationType.FITNESS
        ))).isEqualTo(new TrainerRetrievalResponseDto(
            1L,
            "first",
            "last",
            "username",
            "password",
            true,
            SpecializationType.FITNESS
        ));
    }
}