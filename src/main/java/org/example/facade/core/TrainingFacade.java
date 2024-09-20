package org.example.facade.core;

import java.util.Date;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeDateRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeTrainerDateRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeTrainerRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerDateRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;

public interface TrainingFacade {

    TrainingCreationResponseDto createTraining(TrainingCreationRequestDto requestDto);

    TrainingRetrievalResponseDto retrieveTraining(Long trainingId);

    TrainingListRetrievalResponseDto retrieveTrainingListByTrainer(String trainerUsername);

    TrainingListRetrievalResponseDto retrieveTrainingListByTrainee(String traineeUsername);

    TrainingListRetrievalResponseDto retrieveTrainingListByTraineeDate(
        TrainingListRetrievalByTraineeDateRequestDto requestDto);

    TrainingListRetrievalResponseDto retrieveTrainingListByTrainerDate(
        TrainingListRetrievalByTrainerDateRequestDto requestDto);

    TrainingListRetrievalResponseDto retrieveTrainingListByTraineeTrainerDate(
        TrainingListRetrievalByTraineeTrainerDateRequestDto requestDto);

    TrainingListRetrievalResponseDto retrieveTrainingListByTraineeTrainer(
        TrainingListRetrievalByTraineeTrainerRequestDto requestDto);
}
