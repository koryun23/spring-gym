package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.training.TrainingType;
import org.example.entity.training.TrainingTypeEntity;
import org.example.exception.TrainingTypeNotFoundException;
import org.example.repository.TrainingTypeEntityRepository;
import org.example.service.core.training.TrainingTypeService;
import org.example.service.impl.training.TrainingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceImplTest {

    private TrainingTypeService testSubject;

    @Mock
    private TrainingTypeEntityRepository trainingTypeEntityRepository;

    @BeforeEach
    public void init() {
        testSubject = new TrainingTypeServiceImpl(trainingTypeEntityRepository);
    }

    @Test
    public void testFindAll() {
        Mockito.when(trainingTypeEntityRepository.findAll()).thenReturn(List.of(
            new TrainingTypeEntity(TrainingType.AEROBIC),
            new TrainingTypeEntity(TrainingType.FLEXIBILITY_TRAINING),
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
        ));
        Assertions.assertThat(testSubject.findAll()).isEqualTo(List.of(
            new TrainingTypeEntity(TrainingType.AEROBIC),
            new TrainingTypeEntity(TrainingType.FLEXIBILITY_TRAINING),
            new TrainingTypeEntity(TrainingType.WEIGHTLIFTING)
        ));
        Mockito.verifyNoMoreInteractions(trainingTypeEntityRepository);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(trainingTypeEntityRepository);
    }

    @Test
    public void testFindById() {
        // given
        Optional<TrainingTypeEntity> optional = Optional.of(new TrainingTypeEntity(TrainingType.AEROBIC));

        // when
        Mockito.when(trainingTypeEntityRepository.findById(1L)).thenReturn(optional);

        // then
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(optional);
        Mockito.verifyNoMoreInteractions(trainingTypeEntityRepository);
    }

    @Test
    public void testGetWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.get(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(trainingTypeEntityRepository);
    }

    @Test
    public void testGetWhenExists() {
        // given
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        Optional<TrainingTypeEntity> optional = Optional.of(trainingTypeEntity);

        // when
        Mockito.when(trainingTypeEntityRepository.findById(1L)).thenReturn(optional);

        // then
        Assertions.assertThat(testSubject.get(1L)).isEqualTo(trainingTypeEntity);
        Mockito.verifyNoMoreInteractions(trainingTypeEntityRepository);
    }

    @Test
    public void testGetWhenDoesNotExist() {

        // when
        Mockito.when(trainingTypeEntityRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> testSubject.get(1L))
            .isExactlyInstanceOf(TrainingTypeNotFoundException.class);
        Mockito.verifyNoMoreInteractions(trainingTypeEntityRepository);
    }
}