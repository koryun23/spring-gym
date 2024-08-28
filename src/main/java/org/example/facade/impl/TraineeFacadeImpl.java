package org.example.facade.impl;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.Trainee;
import org.example.facade.core.TraineeFacade;
import org.example.service.core.TraineeService;
import org.example.service.params.TraineeCreateParams;
import org.example.service.params.TraineeUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TraineeFacadeImpl implements TraineeFacade {

    @Autowired
    private TraineeService traineeService;

    @Override
    public TraineeCreationResponseDto createTrainee(TraineeCreationRequestDto requestDto) {
        Trainee trainee = traineeService.create(new TraineeCreateParams(
                requestDto.getUserId(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.isActive(),
                requestDto.getDateOfBirth(),
                requestDto.getAddress()
        ));
        return new TraineeCreationResponseDto(
                trainee.getUserId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getUsername(),
                trainee.getPassword(),
                trainee.isActive(),
                trainee.getDateOfBirth(),
                requestDto.getAddress()
        );
    }

    @Override
    public TraineeUpdateResponseDto updateTrainee(TraineeUpdateRequestDto requestDto) {
        Trainee trainee = traineeService.update(new TraineeUpdateParams(
                requestDto.getUserId(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.isActive(),
                requestDto.getDateOfBirth(),
                requestDto.getAddress()
        ));
        return new TraineeUpdateResponseDto(
                trainee.getUserId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getUsername(),
                trainee.getPassword(),
                trainee.isActive(),
                trainee.getDateOfBirth(),
                trainee.getAddress()
        );
    }

    @Override
    public TraineeRetrievalResponseDto retrieveTrainee(Long id) {
        Trainee trainee = traineeService.select(id);
        return new TraineeRetrievalResponseDto(
                trainee.getUserId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getUsername(),
                trainee.getPassword(),
                trainee.isActive(),
                trainee.getDateOfBirth(),
                trainee.getAddress()
        );
    }

    @Override
    public TraineeDeletionResponseDto deleteTrainee(Long id) {
        boolean success = traineeService.delete(id);
        return new TraineeDeletionResponseDto(success);
    }
}
