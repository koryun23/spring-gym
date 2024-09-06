package org.example.facade.core;

import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;

public interface TrainerFacade {

    TrainerCreationResponseDto createTrainer(TrainerCreationRequestDto requestDto);

    TrainerUpdateResponseDto updateTrainer(TrainerUpdateRequestDto requestDto);

    TrainerRetrievalResponseDto retrieveTrainer(Long trainerId);
}
