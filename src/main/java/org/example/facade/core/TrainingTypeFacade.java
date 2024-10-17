package org.example.facade.core;

import org.example.dto.RestResponse;
import org.example.dto.request.TrainingTypeListRetrievalRequestDto;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;

public interface TrainingTypeFacade {

    RestResponse<TrainingTypeListRetrievalResponseDto> getTrainingTypes(TrainingTypeListRetrievalRequestDto requestDto);
}
