package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingType;
import org.example.entity.training.TrainingTypeEntity;
import org.example.entity.user.UserEntity;
import org.example.repository.TraineeEntityRepository;
import org.example.repository.TrainerEntityRepository;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.training.TrainingTypeService;
import org.example.service.core.user.UserRoleService;
import org.example.service.core.user.UserService;
import org.example.service.core.user.UsernamePasswordService;
import org.example.service.impl.trainer.TrainerServiceImpl;
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
    private TrainerEntityRepository trainerEntityRepository;

    @Mock
    private TraineeEntityRepository traineeEntityRepository;

    @Mock
    private UserService userService;

    @Mock
    private UsernamePasswordService usernamePasswordService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private UserRoleService userRoleService;

    @BeforeEach
    public void init() {
        testSubject =
            new TrainerServiceImpl(usernamePasswordService, trainerEntityRepository, userService, userRoleService,
                trainingTypeService);
    }

    @Test
    public void testCreate() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, null);
        Mockito.when(usernamePasswordService.username("first", "last")).thenReturn("username");
        Mockito.when(usernamePasswordService.password()).thenReturn("password");

        Mockito.when(trainerEntityRepository.save(trainerEntity)).thenReturn(trainerEntity);

        Assertions.assertThat(
                testSubject.create(new TrainerDto(new UserDto("first", "last", "username", "password", true), null)))
            .isEqualTo(trainerEntity);

        Mockito.verifyNoMoreInteractions(trainerEntityRepository);
    }

    @Test
    public void testUpdate() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findByUserUsername("username")).thenReturn(Optional.of(trainerEntity));
        Mockito.when(trainerEntityRepository.save(trainerEntity)).thenReturn(trainerEntity);

        Assertions.assertThat(testSubject.update(
            new TrainerDto(new UserDto("first", "last", "username", "password", true),
                trainerEntity.getSpecialization().getId()))).isEqualTo(trainerEntity);

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testSelect() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(trainerEntity));

        Assertions.assertThat(testSubject.select(1L)).isEqualTo(trainerEntity);

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testSelectByUsername() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findByUserUsername("username")).thenReturn(Optional.of(trainerEntity));

        Assertions.assertThat(testSubject.selectByUsername("username")).isEqualTo(trainerEntity);

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testFindById() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findById(1L)).thenReturn(Optional.of(trainerEntity));

        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(trainerEntity));

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testFindByUsername() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findByUserUsername("username")).thenReturn(Optional.of(trainerEntity));

        Assertions.assertThat(testSubject.findByUsername("username")).isEqualTo(Optional.of(trainerEntity));

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

    @Test
    public void testFindAllNotAssignedTo() {
        UserEntity userEntity = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity trainingTypeEntity = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainerEntity = new TrainerEntity(userEntity, trainingTypeEntity);

        Mockito.when(trainerEntityRepository.findAllTrainersNotAssignedTo("username"))
            .thenReturn(List.of(trainerEntity));

        Assertions.assertThat(testSubject.findAllNotAssignedTo("username")).isEqualTo(List.of(trainerEntity));

        Mockito.verifyNoMoreInteractions(trainerEntityRepository, traineeEntityRepository);
    }

}