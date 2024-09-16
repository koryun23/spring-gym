package org.example.mapper.trainer;

import org.assertj.core.api.Assertions;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.entity.TrainerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerUpdateRequestDtoToTrainerEntityMapperImplTest {

    private TrainerUpdateRequestDtoToTrainerEntityMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainerUpdateRequestDtoToTrainerEntityMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        Assertions.assertThat(testSubject.map(new TrainerUpdateRequestDto(
            1L,
            "first",
            "last",
            "username",
            "password",
            true,
            SpecializationType.FITNESS
        ))).isEqualTo(new TrainerEntity(
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