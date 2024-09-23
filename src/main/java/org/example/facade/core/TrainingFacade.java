package org.example.facade.core;

import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.response.MultipleTrainingDeletionByTraineeResponseDto;
import org.example.dto.response.MultipleTrainingDeletionByTrainerResponseDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;

public interface TrainingFacade {

    TrainingCreationResponseDto createTraining(TrainingCreationRequestDto requestDto);

    TrainingRetrievalResponseDto retrieveTraining(Long trainingId);

    TrainingListRetrievalResponseDto retrieveTrainingListByTrainer(TrainingListRetrievalByTrainerRequestDto requestDto);

    TrainingListRetrievalResponseDto retrieveTrainingListByTrainee(TrainingListRetrievalByTraineeRequestDto requestDto);

    MultipleTrainingDeletionByTraineeResponseDto deleteMultpileTraineeTraining(String traineeUsername);

    MultipleTrainingDeletionByTrainerResponseDto deleteMultipleTrainerTraining(String trainerUsername);

}
