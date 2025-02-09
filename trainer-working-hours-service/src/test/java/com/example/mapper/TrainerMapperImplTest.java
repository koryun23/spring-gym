package com.example.mapper;

import com.example.dto.ActionType;
import com.example.dto.TrainerDto;
import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursResponseDto;
import com.example.entity.TrainerEntity;
import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerMapperImplTest {

    private TrainerMapper testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainerMapperImpl();
    }

    @Test
    public void testMapTrainerWorkingHoursRequestDtoToTrainerEntityWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.mapTrainerWorkingHoursRequestDtoToTrainerEntity(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMapTrainerWorkingHoursRequestDtoToTrainerEntity() {

        // given
        TrainerWorkingHoursRequestDto requestDto = new TrainerWorkingHoursRequestDto(
            1L, "username", "first", "last", true, Date.valueOf("2024-10-10"), 1000L, ActionType.ADD
        );

        TrainerEntity trainerEntity = new TrainerEntity("username", "first", "last", true, 2024, 10, 1000L);

        // then
        Assertions.assertThat(testSubject.mapTrainerWorkingHoursRequestDtoToTrainerEntity(requestDto))
            .isEqualTo(trainerEntity);
    }

    @Test
    public void testMapTrainerEntityToTrainerDtoWhenNull() {

        Assertions.assertThatThrownBy(() -> testSubject.mapTrainerEntityToTrainerDto(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMapTrainerEntityToTrainerDto() {

        // given
        TrainerEntity trainerEntity = new TrainerEntity(
            "username", "first", "last", true, 2025, 8, 1000L
        );
        trainerEntity.setTrainerId(1L);
        TrainerDto trainerDto = new TrainerDto(
            1L, "username", "first", "last", true, 2025, 8, 1000L
        );

        // then
        Assertions.assertThat(testSubject.mapTrainerEntityToTrainerDto(trainerEntity)).isEqualTo(trainerDto);
    }

    @Test
    public void testMapTrainerEntityToTrainerWorkingHoursResponseDtoWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.mapTrainerEntityToTrainerWorkingHoursResponseDto(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMapTrainerEntityToTrainerWorkingHoursResponseDto() {
        // given
        TrainerEntity trainerEntity = new TrainerEntity(
            "username", "first", "last", true, 2025, 8, 1000L
        );
        TrainerWorkingHoursResponseDto trainerWorkingHoursResponseDto =
            new TrainerWorkingHoursResponseDto("username", 1000L);

        //then
        Assertions.assertThat(testSubject.mapTrainerEntityToTrainerWorkingHoursResponseDto(trainerEntity))
            .isEqualTo(trainerWorkingHoursResponseDto);
    }
}