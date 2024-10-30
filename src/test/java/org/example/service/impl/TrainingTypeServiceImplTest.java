package org.example.service.impl;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
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
}