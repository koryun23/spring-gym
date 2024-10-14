package org.example.facade.core;

import org.example.dto.RestResponse;
import org.example.dto.request.MultipleTrainingDeletionByTraineeRequestDto;
import org.example.dto.request.MultipleTrainingDeletionByTrainerRequestDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.request.TrainingRetrievalByIdRequestDto;
import org.example.dto.request.TrainingUpdateRequestDto;
import org.example.dto.response.MultipleTrainingDeletionByTraineeResponseDto;
import org.example.dto.response.MultipleTrainingDeletionByTrainerResponseDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.dto.response.TrainingUpdateResponseDto;

public interface TrainingFacade {

    RestResponse<TrainingCreationResponseDto> createTraining(TrainingCreationRequestDto requestDto);

    RestResponse<TrainingRetrievalResponseDto> retrieveTraining(TrainingRetrievalByIdRequestDto requestDto);

    RestResponse<TrainingListRetrievalResponseDto> retrieveTrainingListByTrainer(TrainingListRetrievalByTrainerRequestDto requestDto);

    RestResponse<TrainingListRetrievalResponseDto> retrieveTrainingListByTrainee(TrainingListRetrievalByTraineeRequestDto requestDto);

    RestResponse<MultipleTrainingDeletionByTraineeResponseDto> deleteMultpileTraineeTraining(
        MultipleTrainingDeletionByTraineeRequestDto requestDto);

    RestResponse<MultipleTrainingDeletionByTrainerResponseDto> deleteMultipleTrainerTraining(
        MultipleTrainingDeletionByTrainerRequestDto requestDto);

    RestResponse<TrainingUpdateResponseDto> updateTraining(TrainingUpdateRequestDto requestDto);
}
