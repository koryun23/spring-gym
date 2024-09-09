package org.example.facade.impl;

import java.util.List;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.facade.core.TraineeFacade;
import org.example.mapper.trainee.TraineeCreationRequestDtoToTraineeEntityMapper;
import org.example.mapper.trainee.TraineeEntityToTraineeCreationResponseDtoMapper;
import org.example.mapper.trainee.TraineeEntityToTraineeRetrievalResponseDtoMapper;
import org.example.mapper.trainee.TraineeEntityToTraineeUpdateResponseDtoMapperImpl;
import org.example.mapper.trainee.TraineeUpdateRequestDtoToTraineeEntityMapper;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UsernamePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeFacadeImpl implements TraineeFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeFacadeImpl.class);

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TraineeCreationRequestDtoToTraineeEntityMapper traineeCreationRequestDtoToTraineeEntityMapper;
    private final TraineeEntityToTraineeCreationResponseDtoMapper traineeToTraineeCreationResponseDtoMapper;
    private final TraineeUpdateRequestDtoToTraineeEntityMapper traineeUpdateRequestDtoToTraineeEntityMapper;
    private final TraineeEntityToTraineeUpdateResponseDtoMapperImpl traineeEntityToTraineeUpdateResponseDtoMapper;
    private final TraineeEntityToTraineeRetrievalResponseDtoMapper traineeEntityToTraineeRetrievalResponseDtoMapper;
    private final UsernamePasswordService usernamePasswordService;
    private final IdService idService;

    /**
     * Constructor.
     */
    public TraineeFacadeImpl(TraineeService traineeService,
                             TrainerService trainerService,
                             TraineeCreationRequestDtoToTraineeEntityMapper
                                 traineeCreationRequestDtoToTraineeEntityMapper,
                             TraineeEntityToTraineeCreationResponseDtoMapper traineeToTraineeCreationResponseDtoMapper,
                             TraineeUpdateRequestDtoToTraineeEntityMapper traineeUpdateRequestDtoToTraineeEntityMapper,
                             TraineeEntityToTraineeUpdateResponseDtoMapperImpl
                                 traineeEntityToTraineeUpdateResponseDtoMapper,
                             TraineeEntityToTraineeRetrievalResponseDtoMapper
                                 traineeEntityToTraineeRetrievalResponseDtoMapper,
                             @Qualifier("traineeUsernamePasswordService")
                             UsernamePasswordService usernamePasswordService,
                             @Qualifier("traineeIdService")
                             IdService idService) {
        this.traineeToTraineeCreationResponseDtoMapper = traineeToTraineeCreationResponseDtoMapper;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.traineeCreationRequestDtoToTraineeEntityMapper = traineeCreationRequestDtoToTraineeEntityMapper;
        this.traineeUpdateRequestDtoToTraineeEntityMapper = traineeUpdateRequestDtoToTraineeEntityMapper;
        this.traineeEntityToTraineeUpdateResponseDtoMapper = traineeEntityToTraineeUpdateResponseDtoMapper;
        this.traineeEntityToTraineeRetrievalResponseDtoMapper = traineeEntityToTraineeRetrievalResponseDtoMapper;
        this.usernamePasswordService = usernamePasswordService;
        this.idService = idService;
    }

    @Override
    public TraineeCreationResponseDto createTrainee(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        LOGGER.info("Creating a TraineeEntity based on the TraineeCreationRequestDto - {}", requestDto);

        Long traineeId = idService.getId();

        if (!traineeService.findById(traineeId).isEmpty()) {
            return new TraineeCreationResponseDto(
                List.of(String.format("A trainee with the specified id - %d, already exists", traineeId)));
        }

        String username = usernamePasswordService.username(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            traineeId,
            "trainee"
        );

        String password = usernamePasswordService.password();
        requestDto.setUserId(traineeId);
        requestDto.setUsername(username);
        requestDto.setPassword(password);

        TraineeCreationResponseDto responseDto = traineeToTraineeCreationResponseDtoMapper.map(
            traineeService.create(traineeCreationRequestDtoToTraineeEntityMapper.map(requestDto)));

        idService.autoIncrement();

        LOGGER.info("Successfully created a TraineeEntity based on the TraineeCreationRequestDto - {}, response - {}",
            requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TraineeUpdateResponseDto updateTrainee(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        LOGGER.info("Updating a TraineeEntity based on the TraineeUpdateRequestDto - {}", requestDto);

        if(traineeService.findById(requestDto.getUserId()).isEmpty()) {
            return new TraineeUpdateResponseDto(List.of(
                String.format("A user with specified id - %d, does not exist", requestDto.getUserId())));
        }

        TraineeUpdateResponseDto responseDto = traineeEntityToTraineeUpdateResponseDtoMapper.map(
            traineeService.update(traineeUpdateRequestDtoToTraineeEntityMapper.map(requestDto)));

        LOGGER.info("Successfully updated a TraineeEntity based on the TraineeUpdateRequestDto - {}, response - {}",
            requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TraineeRetrievalResponseDto retrieveTrainee(Long id) {
        Assert.notNull(id, "TraineeEntity id must not be null");
        LOGGER.info("Retrieving a TraineeEntity with an id of {}", id);

        if (id <= 0) {
            return new TraineeRetrievalResponseDto(
                List.of(String.format("TraineeEntity id must be positive: %d specified", id)));
        }

        if (traineeService.findById(id).isEmpty()) {
            return new TraineeRetrievalResponseDto(
                List.of(String.format("A TraineeEntity with an id - %d, does not exist", id)));
        }

        TraineeRetrievalResponseDto responseDto =
            traineeEntityToTraineeRetrievalResponseDtoMapper.map(traineeService.select(id));

        LOGGER.info("Successfully retrieved a TraineeEntity with an id of {}, response - {}", id, responseDto);
        return responseDto;
    }

    @Override
    public TraineeDeletionResponseDto deleteTrainee(Long id) {
        Assert.notNull(id, "TraineeEntity id must not be null");
        if (id <= 0) {
            return new TraineeDeletionResponseDto(
                List.of(String.format("TraineeEntity id must be positive: %d specified", id)));
        }

        if (traineeService.findById(id).isEmpty()) {
            return new TraineeDeletionResponseDto(
                List.of(String.format("A TraineeEntity with an id - %d, does not exist", id)));
        }

        LOGGER.info("Deleting a TraineeEntity with an id of {}", id);
        boolean success = traineeService.delete(id);
        TraineeDeletionResponseDto responseDto = new TraineeDeletionResponseDto(success);
        LOGGER.info("Successfully deleted a TraineeEntity with an id of {}, response - {}", id, responseDto);
        return responseDto;
    }
}
