package com.example.service;

import com.example.entity.TrainerEntity;
import com.example.repository.TrainerRepository;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    private TrainerService testSubject;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainerWorkingHoursUpdateStrategy strategy;

    @BeforeEach
    public void init() {
        testSubject = new TrainerServiceImpl(trainerRepository);
    }

    @Test
    public void testUpdateWorkingHours() {

        // given
        TrainerEntity trainerEntity = new TrainerEntity(
            "username", "first", "last", true, 2025, 8, 1000L
        );

        // when
        Mockito.when(strategy.updateTrainerWorkingHours(trainerEntity)).thenReturn(trainerEntity);

        // then
        Assertions.assertThat(testSubject.updateWorkingHours(trainerEntity, strategy))
            .isEqualTo(trainerEntity);
    }

    @Test
    public void testUpdateWorkingHoursWhenTrainerEntityIsNull() {

        Assertions.assertThatThrownBy(() -> testSubject.updateWorkingHours(null, strategy))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateWorkingHoursWhenStrategyIsNull() {

        // given
        TrainerEntity trainerEntity = new TrainerEntity(
            "username", "first", "last", true, 2025, 8, 1000L
        );

        // then
        Assertions.assertThatThrownBy(() -> testSubject.updateWorkingHours(trainerEntity, null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindAllByUsernameWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findAllByUsername(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindAllByUsernameWhenUsernameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.findAllByUsername(""))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindAllByUsername() {

        // given
        TrainerEntity trainerEntity1 = new TrainerEntity(
            "username", "first", "last", true, 2025, 8, 1000L
        );
        TrainerEntity trainerEntity2 = new TrainerEntity(
            "username", "first", "last", true, 2025, 9, 2000L
        );

        List<TrainerEntity> all = List.of(trainerEntity1, trainerEntity2);

        // when
        Mockito.when(trainerRepository.findAllByTrainerUsername("username")).thenReturn(all);

        // then
        Assertions.assertThat(testSubject.findAllByUsername("username")).isEqualTo(all);
        Mockito.verifyNoMoreInteractions(trainerRepository);

    }

    @Test
    public void findAll() {

        // given
        TrainerEntity trainerEntity1 = new TrainerEntity(
            "username1", "first1", "last1", true, 2025, 8, 1000L
        );
        TrainerEntity trainerEntity2 = new TrainerEntity(
            "username2", "first2", "last2", true, 2024, 9, 2000L
        );

        List<TrainerEntity> all = List.of(trainerEntity1, trainerEntity2);

        // when
        Mockito.when(trainerRepository.findAll()).thenReturn(all);

        // then
        Assertions.assertThat(testSubject.findAll()).isEqualTo(all);
        Mockito.verifyNoMoreInteractions(trainerRepository);
    }
}