package org.example.service.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.SpecializationType;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.exception.InvalidIdException;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsernamePasswordServiceImplTest {

    private UsernamePasswordServiceImpl testSubject;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @BeforeEach
    public void init() {
        testSubject = new UsernamePasswordServiceImpl(traineeService, trainerService);
    }

    @Test
    public void testWhenFirstNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.username(
            null, "last", 1L, "suffix"
            )).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenFirstNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.username(
            "", "last", 1L, "suffix"
        )).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenLastNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.username(
            "first", null, 1L, "suffix"
        )).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenLastNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> testSubject.username(
            "first", "", 1L, "suffix"
        )).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenSuffixIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.username(
            "first", "last", 1L, null
        )).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenIdIsNegative() {
        Assertions.assertThatThrownBy(() -> testSubject.username(
            "first", "last", -1L, "suffix"
        )).isExactlyInstanceOf(InvalidIdException.class);
    }

    @Test
    public void testWhenIdIsZero() {
        Assertions.assertThatThrownBy(() -> testSubject.username(
            "first", "last", 0L, "suffix"
        )).isExactlyInstanceOf(InvalidIdException.class);
    }

    @Test
    public void testGetUsernameWhenUserWithUsernameDoesNotExist() {
        //given
        Mockito.when(traineeService.findByUsername("first.last")).thenReturn(Optional.empty());
        Mockito.when(trainerService.findByUsername("first.last")).thenReturn(Optional.empty());

        //when
        String username = testSubject.username("first", "last", 1L, "suffix");

        //then
        Assertions.assertThat(username).isEqualTo("first.last");
    }

    @Test
    public void testGetUsernameWhenTrainerDoesNotExist() {
        //given
        Mockito.when(traineeService.findByUsername("first.last")).thenReturn(Optional.of(new TraineeEntity(
            1L, "first", "last", "first.last", "password", true,
            Date.valueOf("2024-10-10"), "address"
        )));
        Mockito.when(trainerService.findByUsername("first.last")).thenReturn(Optional.empty());

        //when
        String username = testSubject.username("first", "last", 1L, "suffix");

        //then
        Assertions.assertThat(username).isEqualTo("first.last.1");
    }

    @Test
    public void testGetUsernameWhenTraineeDoesNotExist() {
        //given
        Mockito.when(trainerService.findByUsername("first.last")).thenReturn(Optional.of(new TrainerEntity(
            1L, "first", "last", "first.last", "password", true,
            SpecializationType.FITNESS
        )));
        Mockito.when(traineeService.findByUsername("first.last")).thenReturn(Optional.empty());

        //when
        String username = testSubject.username("first", "last", 1L, "suffix");

        //then
        Assertions.assertThat(username).isEqualTo("first.last.1");
    }

    @Test
    public void testGetUsernameWhenUserExists() {
        //given
        Mockito.when(trainerService.findByUsername("first.last")).thenReturn(Optional.of(new TrainerEntity(
            1L, "first", "last", "first.last", "password", true,
            SpecializationType.FITNESS
        )));
        Mockito.when(traineeService.findByUsername("first.last")).thenReturn(Optional.of(new TraineeEntity(
            2L, "first", "last", "first.last.2", "password", true,
            Date.valueOf("2024-10-10"), "address"
        )));
        Mockito.when(trainerService.findByUsername("first.last.2")).thenReturn(Optional.empty());
        Mockito.when(traineeService.findByUsername("first.last.2")).thenReturn(Optional.of(new TraineeEntity(
            2L, "first", "last", "first.last.2", "password", true,
            Date.valueOf("2024-10-10"), "address"
        )));

        //when
        String username = testSubject.username("first", "last", 2L, "suffix");

        //then
        Assertions.assertThat(username).isEqualTo("first.last.2.suffix");
    }
}