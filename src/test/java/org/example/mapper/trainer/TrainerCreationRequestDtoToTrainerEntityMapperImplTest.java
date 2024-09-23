package org.example.mapper.trainer;

import org.assertj.core.api.Assertions;
import org.example.dao.core.TrainingTypeDao;
import org.example.dao.core.UserDao;
import org.example.dto.request.TrainerCreationRequestDto;
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
class TrainerCreationRequestDtoToTrainerEntityMapperImplTest {

    private TrainerCreationRequestDtoToTrainerEntityMapperImpl testSubject;

    @Mock
    private UserDao userDao;

    @Mock
    private TrainingTypeDao trainingTypeDao;

    @BeforeEach
    public void init() {
        testSubject = new TrainerCreationRequestDtoToTrainerEntityMapperImpl(userDao, trainingTypeDao);
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        TrainerCreationRequestDto requestDto = new TrainerCreationRequestDto(
            "first", "last", true, 1L
        );
        requestDto.setUserId(1L);
        requestDto.setUsername("username");
        requestDto.setPassword("password");
        UserEntity user = new UserEntity("first", "last", "username", "password", true);
        user.setId(1L);
        TrainingTypeEntity specialization = new TrainingTypeEntity(TrainingType.AEROBIC);
        specialization.setId(1L);
        Mockito.when(userDao.getByUsername("username")).thenReturn(user);
        Mockito.when(trainingTypeDao.get(1L)).thenReturn(specialization);
        Assertions.assertThat(testSubject.map(requestDto)).isEqualTo(new TrainerEntity(
            user,
            specialization
        ));
    }

}