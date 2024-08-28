package org.example.facade.core;

import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;

public interface TrainingFacade {

    TrainingCreationResponseDto createTraining(TrainingCreationRequestDto requestDto);

    TrainingRetrievalResponseDto retrieveTraining(Long trainingId);
}
