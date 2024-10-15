package org.example.facade.core;

import org.example.dto.RestResponse;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByIdRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineePasswordChangeRequestDto;
import org.example.dto.request.TraineeRetrievalByIdRequestDto;
import org.example.dto.request.TraineeRetrievalByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeSwitchActivationStateResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;

public interface TraineeFacade {

    RestResponse<TraineeCreationResponseDto> createTrainee(TraineeCreationRequestDto requestDto);

    RestResponse<TraineeUpdateResponseDto> updateTrainee(TraineeUpdateRequestDto requestDto);

    RestResponse<TraineeRetrievalResponseDto> retrieveTrainee(TraineeRetrievalByUsernameRequestDto requestDto);

    RestResponse<TraineeDeletionResponseDto> deleteTraineeByUsername(TraineeDeletionByUsernameRequestDto requestDto);

    RestResponse<TraineeSwitchActivationStateResponseDto> switchActivationState(TraineeSwitchActivationStateRequestDto requestDto);
}
