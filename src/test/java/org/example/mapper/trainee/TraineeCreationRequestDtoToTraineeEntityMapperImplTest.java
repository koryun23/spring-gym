package org.example.mapper.trainee;

import java.sql.Date;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class TraineeCreationRequestDtoToTraineeEntityMapperImplTest {

    private TraineeCreationRequestDtoToTraineeEntityMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TraineeCreationRequestDtoToTraineeEntityMapperImpl();
    }

    @Test
    public void testWhenNull() {
        assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        TraineeCreationRequestDto request = new TraineeCreationRequestDto(
            "first",
            "last",
            true,
            Date.valueOf("2024-10-10"),
            "address"
        );
        request.setUserId(1L);
        request.setUsername("first.last");
        request.setPassword("pwd");
        assertThat(testSubject.map(request)).isEqualTo(new TraineeEntity(
            1L,
            "first",
            "last",
            "first.last",
            "pwd",
            true,
            Date.valueOf("2024-10-10"),
            "address"
        )).isEqualTo(new TraineeEntity(
            1L,
            "first",
            "last",
            "first.last",
            "pwd",
            true,
            Date.valueOf("2024-10-10"),
            "address"
        ));
    }

}