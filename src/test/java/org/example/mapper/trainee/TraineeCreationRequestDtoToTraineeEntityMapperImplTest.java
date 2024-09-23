package org.example.mapper.trainee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
import org.example.dao.core.UserDao;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TraineeCreationRequestDtoToTraineeEntityMapperImplTest {

    private TraineeCreationRequestDtoToTraineeEntityMapperImpl testSubject;

    @Mock
    private UserDao userDao;

    @BeforeEach
    public void init() {
        testSubject = new TraineeCreationRequestDtoToTraineeEntityMapperImpl(userDao);
    }

    @Test
    public void testWhenNull() {
        assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }


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

        UserEntity user = new UserEntity("first", "last", "first.last", "password", true);
        user.setId(1L);
        Mockito.when(userDao.getByUsername("first.last")).thenReturn(user);
        assertThat(testSubject.map(request)).isEqualTo(new TraineeEntity(
            user,
            Date.valueOf("2024-10-10"),
            "address"
        ));
    }

}