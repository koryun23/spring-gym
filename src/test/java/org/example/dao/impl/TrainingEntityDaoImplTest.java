package org.example.dao.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.exception.TrainingNotFoundException;
import org.example.repository.impl.TrainingEntityRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TrainingEntityDaoImplTest {

    private TrainingDaoImpl testSubject;

    @Mock
    private TrainingEntityRepositoryImpl trainingEntityRepository;

    @BeforeEach
    public void init() {
        testSubject = new TrainingDaoImpl();
        testSubject.setStorage(trainingEntityRepository);
    }

    @Test
    public void testGetWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGet() {
        Mockito.when(trainingEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        )));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        ));
    }

    @Test
    public void testSaveWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.save(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSave() {
        testSubject.save(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        ));
        Mockito.when(trainingEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        )));
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        ));
    }

    @Test
    public void testUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdate() {
        Mockito.when(trainingEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        )));
        TrainingEntity initialTrainingEntity = testSubject.get(1L);
        Mockito.when(trainingEntityRepository.findById(1L)).thenReturn(Optional.of(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training-name",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        )));
        testSubject.update(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training-name",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        ));
        Assertions.assertThat(testSubject.get(1L)).isNotEqualTo(initialTrainingEntity);
    }

    @Test
    public void testDeleteWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.delete(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDelete() {
        testSubject.save(new TrainingEntity(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                Date.valueOf("2024-10-10"),
                "address"
            ),
            new TrainerEntity(
                new UserEntity("f", "l", "u", "p", true),
                new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
            ),
            "training",
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING),
            Date.valueOf("2024-10-10"),
            1000L
        ));
        testSubject.delete(1L);
        Mockito.when(trainingEntityRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.get(1L))
            .isExactlyInstanceOf(TrainingNotFoundException.class);
    }
}