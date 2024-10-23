package org.example.mapper.trainer;

import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.UserEntity;

public interface TrainerMapper {

    TrainerEntity mapTrainerCreationRequestDtoToTrainerEntity(TrainerCreationRequestDto requestDto);

    TrainerCreationResponseDto mapTrainerEntityToTrainerCreationResponseDto(TrainerEntity trainerEntity);

    TrainerRetrievalResponseDto mapTrainerEntityToTrainerRetrievalResponseDto(TrainerEntity trainerEntity);

    TrainerUpdateResponseDto mapTrainerEntityToTrainerUpdateResponseDto(TrainerEntity trainerEntity);

    TrainerEntity mapTrainerUpdateRequestDtoToTrainerEntity(TrainerUpdateRequestDto requestDto);

    UserEntity mapTrainerUpdateRequestDtoToUserEntity(TrainerUpdateRequestDto requestDto);

    UserEntity mapSwitchActivationStateRequestDtoToUserEntity(TrainerSwitchActivationStateRequestDto requestDto);

    TrainerDto mapTrainerEntityToTrainerDto(TrainerEntity trainerEntity);

    TrainerEntity mapTrainerDtoToTrainerEntity(TrainerDto trainerDto);

    UserEntity mapTrainerDtoToUserEntity(TrainerDto trainerDto);

    UserDto mapTrainerEntityToUserDto(TrainerEntity trainerEntity);
}
