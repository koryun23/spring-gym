package org.example.mapper.trainee;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;

public interface TraineeMapper {
    TraineeEntity mapTraineeCreationRequestDtoToTraineeEntity(TraineeCreationRequestDto requestDto);

    TraineeCreationResponseDto mapTraineeEntityToTraineeCreationResponseDto(TraineeEntity trainee);

    TraineeRetrievalResponseDto mapTraineeEntityToTraineeRetrievalResponseDto(TraineeEntity trainee);

    TraineeUpdateResponseDto mapTraineeEntityToTraineeUpdateResponseDto(TraineeEntity trainee);

    TraineeEntity mapTraineeUpdateRequestDtoToTraineeEntity(TraineeUpdateRequestDto requestDto);

    UserEntity mapTraineeUpdateRequestDtoToUserEntity(TraineeUpdateRequestDto requestDto);
}
