package org.example.facade.core;

import org.example.dto.RestResponse;
import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TraineeTrainerListUpdateRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerPasswordChangeRequestDto;
import org.example.dto.request.TrainerRetrievalByIdRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TraineeTrainerListUpdateResponseDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerListRetrievalResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerSwitchActivationStateResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;

public interface TrainerFacade {

    RestResponse<TrainerCreationResponseDto> createTrainer(TrainerCreationRequestDto requestDto);

    RestResponse<TrainerUpdateResponseDto> updateTrainer(TrainerUpdateRequestDto requestDto);

    RestResponse<TrainerSwitchActivationStateResponseDto> switchActivationState(TrainerSwitchActivationStateRequestDto requestDto);

    RestResponse<TrainerRetrievalResponseDto> retrieveTrainerByUsername(TrainerRetrievalByUsernameRequestDto requestDto);

    RestResponse<TrainerListRetrievalResponseDto> retrieveAllTrainersNotAssignedToTrainee(
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto);

    RestResponse<TraineeTrainerListUpdateResponseDto> updateTraineeTrainerList(TraineeTrainerListUpdateRequestDto requestDto);
}
