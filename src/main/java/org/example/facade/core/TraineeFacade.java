package org.example.facade.core;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;

public interface TraineeFacade {

    TraineeCreationResponseDto createTrainee(TraineeCreationRequestDto requestDto);

    TraineeUpdateResponseDto updateTrainee(TraineeUpdateRequestDto requestDto);

    TraineeRetrievalResponseDto retrieveTrainee(Long id);

    TraineeDeletionResponseDto deleteTrainee(Long id);
}
