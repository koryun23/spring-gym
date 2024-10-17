package org.example.facade.core;

import org.example.dto.RestResponse;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;

public interface TrainingFacade {

    RestResponse<TrainingCreationResponseDto> createTraining(TrainingCreationRequestDto requestDto);

    RestResponse<TrainingListRetrievalResponseDto> retrieveTrainingListByTrainer(
        TrainingListRetrievalByTrainerRequestDto requestDto);

    RestResponse<TrainingListRetrievalResponseDto> retrieveTrainingListByTrainee(
        TrainingListRetrievalByTraineeRequestDto requestDto);
}
