package com.example.strategy;

import com.example.entity.TrainerEntity;
import com.example.repository.TrainerRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainerWorkingHoursRemoveStrategyTest {

    private TrainerWorkingHoursRemoveStrategy testSubject;

    @Mock
    private TrainerRepository trainerRepository;

    @BeforeEach
    public void init() {
        testSubject = new TrainerWorkingHoursRemoveStrategy(trainerRepository);
    }

    @Test
    public void testUpdateWorkingHoursWhenTrainerEntityIsNull() {

        Assertions.assertThatThrownBy(() -> testSubject.updateTrainerWorkingHours(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void testUpdateWorkingHoursWhenTrainerEntityExists() {

        // given
        TrainerEntity addedTrainerEntity = new TrainerEntity("username", "first", "last", true, 2025, 8, 1000L);
        TrainerEntity existingTrainerEntity = new TrainerEntity("username", "first", "last", true, 2025, 8, 3000L);
        TrainerEntity updatedTrainerEntity = new TrainerEntity("username", "first", "last", true, 2025, 8, 2000L);

        // when
        Mockito.when(trainerRepository.findByTrainerUsernameAndTrainingMonthAndTrainingYear("username", 8, 2025))
            .thenReturn(Optional.of(existingTrainerEntity));

        Mockito.when(trainerRepository.save(updatedTrainerEntity)).thenReturn(updatedTrainerEntity);

        // then
        Assertions.assertThat(testSubject.updateTrainerWorkingHours(addedTrainerEntity))
            .isEqualTo(updatedTrainerEntity);
        Mockito.verifyNoMoreInteractions(trainerRepository);
    }

    @Test
    public void testUpdateWorkingHoursWhenTrainerEntityDoesNotExist() {

        // given
        TrainerEntity trainerEntity = new TrainerEntity("username", "first", "last", true, 2025, 8, 1000L);

        // when
        Mockito.when(trainerRepository.findByTrainerUsernameAndTrainingMonthAndTrainingYear("username", 8, 2025)).thenReturn(Optional.empty());
        Mockito.when(trainerRepository.save(trainerEntity)).thenReturn(trainerEntity);

        // then
        Assertions.assertThat(testSubject.updateTrainerWorkingHours(trainerEntity)).isEqualTo(trainerEntity);
        Mockito.verifyNoMoreInteractions(trainerRepository);
    }
}