package org.example.service.impl;

import java.sql.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.example.exception.InvalidIdException;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UserService;
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

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        testSubject = new UsernamePasswordServiceImpl(traineeService, trainerService, userService);
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
        Mockito.when(userService.findByUsername("first.last")).thenReturn(Optional.empty());

        //when
        String username = testSubject.username("first", "last", 1L, "suffix");

        //then
        Assertions.assertThat(username).isEqualTo("first.last");
    }

    @Test
    public void testGetUsernameWhenTrainerDoesNotExist() {
        //given
        UserEntity user = new UserEntity("first", "last", "first.last", "password", true);
        user.setId(1L);

        Mockito.when(userService.findByUsername("first.last")).thenReturn(Optional.of(user));

        //when
        String username = testSubject.username("first", "last", 1L, "suffix");

        //then
        Assertions.assertThat(username).isEqualTo("first.last.1");
    }

    @Test
    public void testGetUsernameWhenTraineeDoesNotExist() {
        //given
        UserEntity user = new UserEntity("first", "last", "first.last", "password", true);
        user.setId(1L);

        Mockito.when(userService.findByUsername("first.last")).thenReturn(Optional.of(user));

        //when
        String username = testSubject.username("first", "last", 1L, "suffix");

        //then
        Assertions.assertThat(username).isEqualTo("first.last.1");
    }

    @Test
    public void testGetUsernameWhenUserExists() {
        //given
        Mockito.when(userService.findByUsername("first.last")).thenReturn(
            Optional.of(
                new UserEntity(
                    "first", "last", "first.last", "password", true
                )
            )
        );

        Mockito.when(userService.findByUsername("first.last.2")).thenReturn(Optional.of(new UserEntity(
            "first", "last", "first.last.2", "password", true
        )));

        //when
        String username = testSubject.username("first", "last", 2L, "suffix");

        //then
        Assertions.assertThat(username).isEqualTo("first.last.2.suffix");
    }
}