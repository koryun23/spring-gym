package org.example.mapper.trainer;

import org.assertj.core.api.Assertions;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerEntityToTrainerCreationResponseDtoMapperImplTest {

    private TrainerEntityToTrainerCreationResponseDtoMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainerEntityToTrainerCreationResponseDtoMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        UserEntity user = new UserEntity("first", "last", "username", "password", true);
        user.setId(1L);
        TrainingTypeEntity specialization = new TrainingTypeEntity(TrainingType.AEROBIC);
        specialization.setId(1L);
        TrainerEntity trainer = new TrainerEntity(
            user,
            specialization
        );
        trainer.setId(1L);
        Assertions.assertThat(testSubject.map(trainer)).isEqualTo(new TrainerCreationResponseDto(
            1L,
            1L,
            true,
            1L
        ));
    }

}