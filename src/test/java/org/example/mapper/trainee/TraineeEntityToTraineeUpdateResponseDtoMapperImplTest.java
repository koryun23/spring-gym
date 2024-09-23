package org.example.mapper.trainee;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
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
        UserEntity user = new UserEntity("first", "last", "username", "password", true);
        user.setId(1L);
        TraineeEntity trainee = new TraineeEntity(
            user,
            Date.valueOf("2024-10-10"),
            "address"
        );
        trainee.setId(1L);
        Assertions.assertThat(testSubject.map(trainee)).isEqualTo(new TraineeUpdateResponseDto(
            1L,
            true,
            Date.valueOf("2024-10-10"),
            "address"
        ));
    }

}