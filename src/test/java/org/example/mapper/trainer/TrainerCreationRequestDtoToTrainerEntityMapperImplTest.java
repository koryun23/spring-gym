package org.example.mapper.trainer;

import org.assertj.core.api.Assertions;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.entity.SpecializationType;
import org.example.entity.TrainerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerCreationRequestDtoToTrainerEntityMapperImplTest {

    private TrainerCreationRequestDtoToTrainerEntityMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainerCreationRequestDtoToTrainerEntityMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        TrainerCreationRequestDto requestDto = new TrainerCreationRequestDto(
            "first",
            "last",
            true,
            SpecializationType.FITNESS
        );
        requestDto.setUserId(1L);
        requestDto.setUsername("username");
        requestDto.setPassword("password");
        Assertions.assertThat(testSubject.map(requestDto)).isEqualTo(new TrainerEntity(
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