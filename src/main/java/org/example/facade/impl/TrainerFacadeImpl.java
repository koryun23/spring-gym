package org.example.facade.impl;

import java.util.List;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.facade.core.TrainerFacade;
import org.example.mapper.trainer.TrainerCreationRequestDtoToTrainerEntityMapper;
import org.example.mapper.trainer.TrainerEntityToTrainerCreationResponseDtoMapper;
import org.example.mapper.trainer.TrainerEntityToTrainerRetrievalResponseDtoMapper;
import org.example.mapper.trainer.TrainerEntityToTrainerUpdateResponseDtoMapper;
import org.example.mapper.trainer.TrainerUpdateRequestDtoToTrainerEntityMapper;
import org.example.service.core.IdService;
import org.example.service.core.TrainerService;
import org.example.service.core.UsernamePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerFacadeImpl implements TrainerFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerFacadeImpl.class);

    private final TrainerService trainerService;
    private final TrainerCreationRequestDtoToTrainerEntityMapper trainerCreationRequestDtoToTrainerEntityMapper;
    private final TrainerEntityToTrainerCreationResponseDtoMapper trainerEntityToTrainerCreationResponseDtoMapper;
    private final TrainerUpdateRequestDtoToTrainerEntityMapper trainerUpdateRequestDtoToTrainerEntityMapper;
    private final TrainerEntityToTrainerUpdateResponseDtoMapper trainerEntityToTrainerUpdateResponseDtoMapper;
    private final TrainerEntityToTrainerRetrievalResponseDtoMapper trainerEntityToTrainerRetrievalResponseDtoMapper;

    @Qualifier("trainerUsernamePasswordService")
    private UsernamePasswordService usernamePasswordService;

    @Qualifier("trainerIdService")
    private IdService idService;

    /**
     * Constructor.
     */
    public TrainerFacadeImpl(TrainerService trainerService,
                             TrainerCreationRequestDtoToTrainerEntityMapper
                                 trainerCreationRequestDtoToTrainerEntityMapper,
                             TrainerEntityToTrainerCreationResponseDtoMapper
                                     trainerEntityToTrainerCreationResponseDtoMapper,
                             TrainerUpdateRequestDtoToTrainerEntityMapper trainerUpdateRequestDtoToTrainerEntityMapper,
                             TrainerEntityToTrainerUpdateResponseDtoMapper
                                     trainerEntityToTrainerUpdateResponseDtoMapper,
                             TrainerEntityToTrainerRetrievalResponseDtoMapper
                                     trainerEntityToTrainerRetrievalResponseDtoMapper,
                             UsernamePasswordService usernamePasswordService,
                             IdService idService) {
        this.trainerService = trainerService;
        this.trainerCreationRequestDtoToTrainerEntityMapper = trainerCreationRequestDtoToTrainerEntityMapper;
        this.trainerEntityToTrainerCreationResponseDtoMapper = trainerEntityToTrainerCreationResponseDtoMapper;
        this.trainerUpdateRequestDtoToTrainerEntityMapper = trainerUpdateRequestDtoToTrainerEntityMapper;
        this.trainerEntityToTrainerUpdateResponseDtoMapper = trainerEntityToTrainerUpdateResponseDtoMapper;
        this.trainerEntityToTrainerRetrievalResponseDtoMapper = trainerEntityToTrainerRetrievalResponseDtoMapper;
        this.usernamePasswordService = usernamePasswordService;
        this.idService = idService;
    }

    @Override
    public TrainerCreationResponseDto createTrainer(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        LOGGER.info("Creating a TrainerEntity according to the TrainerCreationRequestDto - {}", requestDto);

        Long trainerId = idService.getId();

        if (!trainerService.findById(trainerId).isEmpty()) {
            return new TrainerCreationResponseDto(
                List.of(String.format("A TrainerEntity with the specified id - %d, already exists", trainerId)));
        }

        String username = usernamePasswordService.username(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            trainerId
        );

        String password = usernamePasswordService.password();

        requestDto.setUserId(trainerId);
        requestDto.setUsername(username);
        requestDto.setPassword(password);

        TrainerCreationResponseDto responseDto = trainerEntityToTrainerCreationResponseDtoMapper.map(
            trainerService.create(trainerCreationRequestDtoToTrainerEntityMapper.map(requestDto)));
        idService.autoIncrement();

        LOGGER.info(
            "Successfully created a TrainerEntity according to the TrainerCreationRequestDto - {}, response - {}",
            requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainerUpdateResponseDto updateTrainer(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");
        LOGGER.info("Updating a TrainerEntity according to the TrainerUpdateRequestDto - {}", requestDto);

        if (trainerService.findById(requestDto.getUserId()).isEmpty()) {
            return new TrainerUpdateResponseDto(List.of(
                String.format("TrainerEntity with the specified id of %d does not exist", requestDto.getUserId())));
        }

        TrainerUpdateResponseDto responseDto = trainerEntityToTrainerUpdateResponseDtoMapper.map(
            trainerService.update(trainerUpdateRequestDtoToTrainerEntityMapper.map(requestDto)));

        LOGGER.info("Successfully updated a TrainerEntity according to the TrainerUpdateRequestDto - {}, response - {}",
            requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainerRetrievalResponseDto retrieveTrainer(Long trainerId) {
        Assert.notNull(trainerId, "TrainerEntity id must not be null");
        LOGGER.info("Retrieving a TrainerEntity with an id of {}", trainerId);

        if (trainerId <= 0) {
            return new TrainerRetrievalResponseDto(
                List.of(String.format("TrainerEntity id must be positive: %d specified", trainerId)));
        }

        if (trainerService.findById(trainerId).isEmpty()) {
            return new TrainerRetrievalResponseDto(
                List.of(String.format("A TrainerEntity with a specified id of %d not found", trainerId)));
        }

        TrainerRetrievalResponseDto responseDto =
            trainerEntityToTrainerRetrievalResponseDtoMapper.map(trainerService.select(trainerId));

        LOGGER.info("Successfully retrieved a TrainerEntity with an id of {}, response - {}", trainerId, responseDto);
        return responseDto;
    }
}
