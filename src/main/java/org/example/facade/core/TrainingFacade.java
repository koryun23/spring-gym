package org.example.facade.core;

import java.util.Date;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;

public interface TrainingFacade {

    TrainingCreationResponseDto createTraining(TrainingCreationRequestDto requestDto);

    TrainingRetrievalResponseDto retrieveTraining(Long trainingId);

    TrainingListRetrievalResponseDto retrieveTrainingListByTrainee(String traineeUsername);

    TrainingListRetrievalResponseDto retrieveTrainingListByTraineeDate(String traineeUsername, Date from, Date to);

    TrainingListRetrievalResponseDto retrieveTrainingListByTraineeTrainerDate(String traineeUsername, Date from, Date to, String trainerUsername);

    TrainingListRetrievalResponseDto retrieveTrainingListByTraineeTrainer(String traineeUsername, String trainerUsername);
}
