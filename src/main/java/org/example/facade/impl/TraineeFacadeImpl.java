package org.example.facade.impl;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.facade.core.TraineeFacade;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UsernamePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.util.List;

@Component
public class TraineeFacadeImpl implements TraineeFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeFacadeImpl.class);

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Autowired
    @Qualifier("traineeUsernamePasswordService")
    private UsernamePasswordService usernamePasswordService;

    @Autowired
    @Qualifier("traineeIdService")
    private IdService idService;

    @Autowired
    public TraineeFacadeImpl(TraineeService traineeService,
                             TrainerService trainerService) {
        Assert.notNull(traineeService, "TraineeEntity Service must not be null");
        Assert.notNull(trainerService, "TrainerEntity Service must not be null");
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @Override
    public TraineeCreationResponseDto createTrainee(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        LOGGER.info("Creating a TraineeEntity based on the TraineeCreationRequestDto - {}", requestDto);

        Long traineeId = idService.getId();

        if(!traineeService.findById(traineeId).isEmpty()) {
            return new TraineeCreationResponseDto(List.of(String.format("A trainee with the specified id - %d, already exists", traineeId)));
        }

        String username = usernamePasswordService.username(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                traineeId
        );
        String password = usernamePasswordService.password();
        TraineeEntity trainee = traineeService.create(new TraineeEntity(
                traineeId,
                requestDto.getFirstName(),
                requestDto.getLastName(),
                username,
                password,
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
        idService.autoIncrement();
        LOGGER.info("Successfully created a TraineeEntity based on the TraineeCreationRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TraineeUpdateResponseDto updateTrainee(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        LOGGER.info("Updating a TraineeEntity based on the TraineeUpdateRequestDto - {}", requestDto);

        if(traineeService.findByUsername(requestDto.getUsername()).isEmpty()) {
            return new TraineeUpdateResponseDto(List.of(String.format("A user with specified username - %s, does not exist", requestDto.getUsername())));
        }

        TraineeEntity trainee = traineeService.update(new TraineeEntity(
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
        LOGGER.info("Successfully updated a TraineeEntity based on the TraineeUpdateRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TraineeRetrievalResponseDto retrieveTrainee(Long id) {
        Assert.notNull(id, "TraineeEntity id must not be null");
        LOGGER.info("Retrieving a TraineeEntity with an id of {}", id);

        if(id <= 0) {
            return new TraineeRetrievalResponseDto(List.of(String.format("TraineeEntity id must be positive: %d specified", id)));
        }

        if(traineeService.findById(id).isEmpty()) {
            return new TraineeRetrievalResponseDto(List.of(String.format("A TraineeEntity with an id - %d, does not exist", id)));
        }

        TraineeEntity trainee = traineeService.select(id);
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
        LOGGER.info("Successfully retrieved a TraineeEntity with an id of {}, response - {}", id, responseDto);
        return responseDto;
    }

    @Override
    public TraineeDeletionResponseDto deleteTrainee(Long id) {
        Assert.notNull(id, "TraineeEntity id must not be null");
        if(id <= 0) {
            return new TraineeDeletionResponseDto(List.of(String.format("TraineeEntity id must be positive: %d specified", id)));
        }

        if(traineeService.findById(id).isEmpty()) {
            return new TraineeDeletionResponseDto(List.of(String.format("A TraineeEntity with an id - %d, does not exist", id)));
        }

        LOGGER.info("Deleting a TraineeEntity with an id of {}", id);
        boolean success = traineeService.delete(id);
        TraineeDeletionResponseDto responseDto = new TraineeDeletionResponseDto(success);
        LOGGER.info("Successfully deleted a TraineeEntity with an id of {}, response - {}", id, responseDto);
        return responseDto;
    }
}
