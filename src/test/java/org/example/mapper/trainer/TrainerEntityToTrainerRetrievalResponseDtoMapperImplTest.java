package org.example.mapper.trainer;

import org.assertj.core.api.Assertions;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainerEntityToTrainerRetrievalResponseDtoMapperImplTest {

    private TrainerEntityToTrainerRetrievalResponseDtoMapperImpl testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TrainerEntityToTrainerRetrievalResponseDtoMapperImpl();
    }

    @Test
    public void testWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.map(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testWhenValid() {
        UserEntity user = new UserEntity("first", "last", "username", "password", true);
        TrainingTypeEntity specialization = new TrainingTypeEntity(TrainingType.AEROBIC);
        TrainerEntity trainer = new TrainerEntity(
            user,
            specialization
        );
        user.setId(1L);
        specialization.setId(1L);
        trainer.setId(1L);
        Assertions.assertThat(testSubject.map(trainer)).isEqualTo(new TrainerRetrievalResponseDto(
            1L,
            1L,
            true,
            1L
        ));
    }
}