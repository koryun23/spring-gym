package org.example.mapper.trainer;

import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.entity.UserEntity;

public interface TrainerMapper {

    TrainerCreationResponseDto mapTrainerEntityToTrainerCreationResponseDto(TrainerEntity trainerEntity);

    TrainerRetrievalResponseDto mapTrainerEntityToTrainerRetrievalResponseDto(TrainerEntity trainerEntity);

    TrainerUpdateResponseDto mapTrainerEntityToTrainerUpdateResponseDto(TrainerEntity trainerEntity);

    TrainerEntity mapTrainerUpdateRequestDtoToTrainerEntity(TrainerUpdateRequestDto requestDto);

    TrainerDto mapTrainerEntityToTrainerDto(TrainerEntity trainerEntity);

    UserDto mapTrainerEntityToUserDto(TrainerEntity trainerEntity);

    TrainerDto mapTrainerCreationRequestDtoToTrainerDto(TrainerCreationRequestDto requestDto);

    TrainerDto mapTrainerUpdateRequestDtoToTrainerDto(TrainerUpdateRequestDto requestDto);
}
