package org.example.facade.core;

import java.util.List;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;

public interface TrainingTypeFacade {

    RestResponse<TrainingTypeListRetrievalResponseDto> getTrainingTypes();
}
