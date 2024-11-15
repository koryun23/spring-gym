package org.example.mapper.trainee;

import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraineeMapperImplTest {

    private TraineeMapper testSubject;

    @BeforeEach
    public void init() {
        testSubject = new TraineeMapperImpl();
    }

    @Test
    public void testMapTraineeCreationRequestDtoToTraineeEntity() {
        Assertions.assertThat(testSubject.mapTraineeCreationRequestDtoToTraineeEntity(
            new TraineeCreationRequestDto(
                "first", "last", null, "address"
            )
        )).isEqualTo(new TraineeEntity(
            new UserEntity(
                "first", "last", null, null, true
            ),
            null,
            "address"
        ));
    }

    @Test
    public void testMapTraineeEntityToTraineeCreationResponseDto() {
        Assertions.assertThat(testSubject.mapTraineeEntityToTraineeCreationResponseDto(
            new TraineeEntity(
                new UserEntity("first", "last", "username", "password", true),
                null, "address"
            )
        )).isEqualTo(new TraineeCreationResponseDto("username", "password"));
    }

    @Test
    public void testMapTraineeEntityToTraineeRetrievalResponseDto() {
        Assertions.assertThat(testSubject.mapTraineeEntityToTraineeRetrievalResponseDto(
            new TraineeEntity(new UserEntity("first", "last", "username", "password", true), null, null)
        )).isEqualTo(new TraineeRetrievalResponseDto("first", "last", null, null, true, null));
    }

    @Test
    public void testMapTraineeEntityToTraineeUpdateResponseDto() {
        Assertions.assertThat(testSubject.mapTraineeEntityToTraineeUpdateResponseDto(new TraineeEntity(
            new UserEntity("first", "last", "username", "password", true),
            null, null
        ))).isEqualTo(new TraineeUpdateResponseDto(
            "username", "first", "last", null, null, true, null
        ));
    }

    @Test
    public void testMapTraineeUpdateRequestDtoToTraineeEntity() {
        Assertions.assertThat(testSubject.mapTraineeUpdateRequestDtoToTraineeEntity(new TraineeUpdateRequestDto(
            "first", "last", "username", true, null, null
        ))).isEqualTo(new TraineeEntity(
            new UserEntity("first", "last", "username", null, true),
            null, null
        ));
    }
}