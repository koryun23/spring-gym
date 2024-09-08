package org.example.mapper.trainee;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraineeEntityToTraineeUpdateResponseDtoMapperImplTest {

    private TraineeEntityToTraineeUpdateResponseDtoMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TraineeEntityToTraineeUpdateResponseDtoMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        Assertions.assertThat(testSubject.map(new TraineeEntity(
            1L,
            "first",
            "last",
            "username",
            "pwd",
            true,
            Date.valueOf("2024-10-10"),
            "address"
        ))).isEqualTo(new TraineeUpdateResponseDto(
            1L,
            "first",
            "last",
            "username",
            "pwd",
            true,
            Date.valueOf("2024-10-10"),
            "address"
        ));
    }

}