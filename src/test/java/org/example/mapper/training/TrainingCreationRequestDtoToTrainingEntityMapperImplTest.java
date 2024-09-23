package org.example.mapper.training;


import java.sql.Date;
import org.assertj.core.api.Assertions;
import org.example.dao.core.TraineeDao;
import org.example.dao.core.TrainerDao;
import org.example.dao.core.TrainingTypeDao;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
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
class TrainingCreationRequestDtoToTrainingEntityMapperImplTest {

    private TrainingCreationRequestDtoToTrainingEntityMapperImpl testSubject;

    @Mock
    private TrainerDao trainerDao;

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private TrainingTypeDao trainingTypeDao;

    @BeforeEach
    public void init() {
        testSubject = new TrainingCreationRequestDtoToTrainingEntityMapperImpl(traineeDao, trainerDao, trainingTypeDao);
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        TrainingCreationRequestDto requestDto = new TrainingCreationRequestDto(
            1L,
            1L,
            "name",
            1L,
            Date.valueOf("2024-10-10"),
            1000L
        );
        requestDto.setTrainingTypeId(1L);
        TraineeEntity trainee = new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            Date.valueOf("2024-10-10"),
            "address"
        );
        TrainingTypeEntity trainingType = new TrainingTypeEntity(TrainingType.WEIGHTLIFTING);
        TrainerEntity trainer = new TrainerEntity(
            new UserEntity("f", "l", "u", "p", true),
            trainingType
        );
        trainee.setId(1L);
        trainer.setId(1L);
        trainingType.setId(1L);
        Mockito.when(trainerDao.get(1L)).thenReturn(trainer);
        Mockito.when(traineeDao.get(1L)).thenReturn(trainee);
        Mockito.when(trainingTypeDao.get(1L)).thenReturn(trainingType);

        Assertions.assertThat(testSubject.map(requestDto)).isEqualTo(new TrainingEntity(
            trainee,
            trainer,
            "name",
            trainingType,
            Date.valueOf("2024-10-10"),
            1000L
        ));
    }
}