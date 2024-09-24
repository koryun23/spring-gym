package org.example.mapper.trainee;

import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.dao.core.UserDao;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TraineeUpdateRequestDtoToTraineeEntityMapperImplTest {

    private TraineeUpdateRequestDtoToTraineeEntityMapperImpl testSubject;

    @Mock
    private UserDao userDao;

    @BeforeEach
    public void init() {
        testSubject = new TraineeUpdateRequestDtoToTraineeEntityMapperImpl(userDao);
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
        Mockito.when(userDao.getByUsername("username")).thenReturn(user);
        Assertions.assertThat(testSubject.map(new TraineeUpdateRequestDto(
            "u",
            "p",
            1L,
            "first",
            "last",
            "username",
            "password",
            true,
            Date.valueOf("2024-10-10"),
            "address"
        ))).isEqualTo(new TraineeEntity(
            user,
            Date.valueOf("2024-10-10"),
            "address"
        ));
    }

}