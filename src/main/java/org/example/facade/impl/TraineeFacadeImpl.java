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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class TraineeFacadeImpl implements TraineeFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeFacadeImpl.class);

    private final TraineeService traineeService;

    @Autowired
    public TraineeFacadeImpl(TraineeService traineeService) {
        Assert.notNull(traineeService, "Trainee Service must not be null");
        this.traineeService = traineeService;
    }

    @Override
    public TraineeCreationResponseDto createTrainee(TraineeCreationRequestDto requestDto) {

        LOGGER.info("Creating a Trainee based on the TraineeCreationRequestDto - {}", requestDto);
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
        TraineeCreationResponseDto responseDto = new TraineeCreationResponseDto(
                trainee.getUserId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getUsername(),
                trainee.getPassword(),
                trainee.isActive(),
                trainee.getDateOfBirth(),
                requestDto.getAddress()
        );
        LOGGER.info("Successfully created a Trainee based on the TraineeCreationRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TraineeUpdateResponseDto updateTrainee(TraineeUpdateRequestDto requestDto) {
        LOGGER.info("Updating a Trainee based on the TraineeUpdateRequestDto - {}", requestDto);

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
        TraineeUpdateResponseDto responseDto = new TraineeUpdateResponseDto(
                trainee.getUserId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getUsername(),
                trainee.getPassword(),
                trainee.isActive(),
                trainee.getDateOfBirth(),
                trainee.getAddress()
        );
        LOGGER.info("Successfully updated a Trainee based on the TraineeUpdateRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TraineeRetrievalResponseDto retrieveTrainee(Long id) {
        LOGGER.info("Retrieving a Trainee with an id of {}", id);
        Trainee trainee = traineeService.select(id);
        TraineeRetrievalResponseDto responseDto = new TraineeRetrievalResponseDto(
                trainee.getUserId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getUsername(),
                trainee.getPassword(),
                trainee.isActive(),
                trainee.getDateOfBirth(),
                trainee.getAddress()
        );
        LOGGER.info("Successfully retrieved a Trainee with an id of {}, response - {}", id, responseDto);
        return responseDto;
    }

    @Override
    public TraineeDeletionResponseDto deleteTrainee(Long id) {
        LOGGER.info("Deleting a Trainee with an id of {}", id);
        boolean success = traineeService.delete(id);
        TraineeDeletionResponseDto responseDto = new TraineeDeletionResponseDto(success);
        LOGGER.info("Successfully deleted a Trainee with an id of {}, response - {}", id, responseDto);
        return responseDto;
    }
}
