package org.example.mapper.trainer;

import org.assertj.core.api.Assertions;
import org.example.dao.core.TrainingTypeDao;
import org.example.dao.core.UserDao;
import org.example.dto.request.TrainerUpdateRequestDto;
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
class TrainerUpdateRequestDtoToTrainerEntityMapperImplTest {

    private TrainerUpdateRequestDtoToTrainerEntityMapperImpl testSubject;

    @Mock
    private UserDao userDao;

    @Mock
    private TrainingTypeDao trainingTypeDao;

    @BeforeEach
    public void init() {
        testSubject = new TrainerUpdateRequestDtoToTrainerEntityMapperImpl(userDao, trainingTypeDao);
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        Mockito.when(userDao.getByUsername("username"))
            .thenReturn(new UserEntity("first", "last", "username", "password", true));
        Mockito.when(trainingTypeDao.get(1L)).thenReturn(new TrainingTypeEntity(TrainingType.AEROBIC));
        Assertions.assertThat(testSubject.map(new TrainerUpdateRequestDto(
            "u", "p",
            1L,
            "first",
            "last",
            "username",
            "password",
            true,
            1L
        ))).isEqualTo(new TrainerEntity(
            new UserEntity("first", "last", "username", "password", true),
            new TrainingTypeEntity(TrainingType.AEROBIC)
        ));
    }
}