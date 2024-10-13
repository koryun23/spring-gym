package org.example.mapper.trainer;

import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;

public interface TrainerMapper {

    TrainerEntity mapTrainerCreationRequestDtoToTrainerEntity(TrainerCreationRequestDto requestDto);

    TrainerCreationResponseDto mapTrainerEntityToTrainerCreationResponseDto(TrainerEntity trainerEntity);

    TrainerRetrievalResponseDto mapTrainerEntityToTrainerRetrievalResponseDto(TrainerEntity trainerEntity);

    TrainerUpdateResponseDto mapTrainerEntityToTrainerUpdateResponseDto(TrainerEntity trainerEntity);

    TrainerEntity mapTrainerUpdateRequestDtoToTrainerEntity(TrainerUpdateRequestDto requestDto);
}
