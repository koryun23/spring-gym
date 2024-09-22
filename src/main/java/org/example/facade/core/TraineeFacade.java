package org.example.facade.core;

import java.util.Date;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;

public interface TraineeFacade {

    TraineeCreationResponseDto createTrainee(TraineeCreationRequestDto requestDto);

    TraineeUpdateResponseDto updateTrainee(TraineeUpdateRequestDto requestDto);

    TraineeRetrievalResponseDto retrieveTrainee(Long id);

    TraineeRetrievalResponseDto retrieveTrainee(String username);

    TraineeDeletionResponseDto deleteTrainee(Long id);

    TraineeDeletionResponseDto deleteTrainee(String username);

    TraineeUpdateResponseDto activateTrainee(Long id);

    TraineeUpdateResponseDto switchActivationState(Long id);

    TraineeUpdateResponseDto changePassword(Long id, String newPassword);
}
