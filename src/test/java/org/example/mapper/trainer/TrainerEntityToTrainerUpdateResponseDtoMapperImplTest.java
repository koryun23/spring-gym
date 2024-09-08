package org.example.mapper.trainer;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.SpecializationType;
import org.example.entity.TrainerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerEntityToTrainerUpdateResponseDtoMapperImplTest {

    private TrainerEntityToTrainerUpdateResponseDtoMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainerEntityToTrainerUpdateResponseDtoMapperImpl();
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
        ))).isEqualTo(new TrainerUpdateResponseDto(
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