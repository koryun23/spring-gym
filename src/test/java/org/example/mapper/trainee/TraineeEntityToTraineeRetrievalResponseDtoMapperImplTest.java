package org.example.mapper.trainee;

import java.sql.Date;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.entity.TraineeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class TraineeEntityToTraineeRetrievalResponseDtoMapperImplTest {

    private TraineeEntityToTraineeRetrievalResponseDtoMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TraineeEntityToTraineeRetrievalResponseDtoMapperImpl();
    }

    @Test
    public void testWhenNull() {
        assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        assertThat(testSubject.map(new TraineeEntity(
            1L,
            "first",
            "last",
            "username",
            "pwd",
            true,
            Date.valueOf("2024-10-10"),
            "address"
        ))).isEqualTo(new TraineeRetrievalResponseDto(
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