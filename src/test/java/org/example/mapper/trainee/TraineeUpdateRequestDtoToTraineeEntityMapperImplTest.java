package org.example.mapper.trainee;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.entity.TraineeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraineeUpdateRequestDtoToTraineeEntityMapperImplTest {

    private TraineeUpdateRequestDtoToTraineeEntityMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TraineeUpdateRequestDtoToTraineeEntityMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        Assertions.assertThat(testSubject.map(new TraineeUpdateRequestDto(
            1L,
            "first",
            "last",
            "username",
            "password",
            true,
            Date.valueOf("2024-10-10"),
            "address"
        ))).isEqualTo(new TraineeEntity(
            1L,
            "first",
            "last",
            "username",
            "password",
            true,
            Date.valueOf("2024-10-10"),
            "address"
        ));
    }

}