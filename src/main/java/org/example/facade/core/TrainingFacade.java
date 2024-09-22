package org.example.facade.core;

import java.util.Date;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeDateRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeTrainerDateRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeTrainerRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerDateRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;

public interface TrainingFacade {

    TrainingCreationResponseDto createTraining(TrainingCreationRequestDto requestDto);

    TrainingRetrievalResponseDto retrieveTraining(Long trainingId);

    TrainingListRetrievalResponseDto retrieveTrainingListByTrainer(TrainingListRetrievalByTrainerRequestDto requestDto);

    TrainingListRetrievalResponseDto retrieveTrainingListByTrainee(TrainingListRetrievalByTraineeRequestDto requestDto);
}
