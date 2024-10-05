package org.example.facade.core;

import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerPasswordChangeRequestDto;
import org.example.dto.request.TrainerRetrievalByIdRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerListRetrievalResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;

public interface TrainerFacade {

    TrainerCreationResponseDto createTrainer(TrainerCreationRequestDto requestDto);

    TrainerUpdateResponseDto updateTrainer(TrainerUpdateRequestDto requestDto);

    TrainerUpdateResponseDto switchActivationState(TrainerSwitchActivationStateRequestDto requestDto);

    TrainerUpdateResponseDto changePassword(TrainerPasswordChangeRequestDto requestDto);

    TrainerRetrievalResponseDto retrieveTrainerByUsername(TrainerRetrievalByUsernameRequestDto requestDto);

    TrainerListRetrievalResponseDto retrieveAllTrainersNotAssignedToTrainee(
        RetrieveAllTrainersNotAssignedToTraineeRequestDto requestDto);
}
