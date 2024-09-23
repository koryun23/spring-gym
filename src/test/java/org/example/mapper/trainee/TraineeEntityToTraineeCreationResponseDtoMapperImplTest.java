package org.example.mapper.trainee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraineeEntityToTraineeCreationResponseDtoMapperImplTest {

    private TraineeEntityToTraineeCreationResponseDtoMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TraineeEntityToTraineeCreationResponseDtoMapperImpl();
    }

    @Test
    public void testWhenNull() {
        assertThatThrownBy(() -> testSubject.map(null))
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
        assertThat(testSubject.map(trainee)).isEqualTo(new TraineeCreationResponseDto(
            1L,
            1L,
            true,
            Date.valueOf("2024-10-10"),
            "address"
        ));
    }

}