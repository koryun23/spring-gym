package org.example.facade.impl;

import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.facade.core.TrainerFacade;
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
public class TrainerFacadeImpl implements TrainerFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerFacadeImpl.class);

    private final TrainerService trainerService;
    private final TraineeService traineeService;

    @Autowired
    @Qualifier("trainerUsernamePasswordService")
    private UsernamePasswordService usernamePasswordService;

    @Autowired
    @Qualifier("trainerIdService")
    private IdService idService;

    @Autowired
    public TrainerFacadeImpl(TrainerService trainerService,
                             TraineeService traineeService) {
        Assert.notNull(trainerService, "TrainerEntity Service must not be null");
        Assert.notNull(traineeService, "TraineeEntity Service must not be null");
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    @Override
    public TrainerCreationResponseDto createTrainer(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        LOGGER.info("Creating a TrainerEntity according to the TrainerCreationRequestDto - {}", requestDto);

        Long trainerId = idService.getId();

        if(!trainerService.findById(trainerId).isEmpty()) {
            return new TrainerCreationResponseDto(List.of(String.format("A TrainerEntity with the specified id - %d, already exists", trainerId)));
        }

        String username = usernamePasswordService.username(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                trainerId
        );

        String password = usernamePasswordService.password();

        TrainerEntity trainerEntity = trainerService.create(new TrainerEntity(
                trainerId,
                requestDto.getFirstName(),
                requestDto.getLastName(),
                username,
                password,
                requestDto.isActive(),
                requestDto.getSpecializationType()
        ));
        TrainerCreationResponseDto responseDto = new TrainerCreationResponseDto(
                trainerEntity.getUserId(),
                trainerEntity.getFirstName(),
                trainerEntity.getLastName(),
                trainerEntity.getUsername(),
                trainerEntity.getPassword(),
                trainerEntity.isActive(),
                trainerEntity.getSpecialization()
        );
        idService.autoIncrement();
        LOGGER.info("Successfully created a TrainerEntity according to the TrainerCreationRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainerUpdateResponseDto updateTrainer(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");
        LOGGER.info("Updating a TrainerEntity according to the TrainerUpdateRequestDto - {}", requestDto);

        if(trainerService.findById(requestDto.getUserId()).isEmpty()) {
            return new TrainerUpdateResponseDto(List.of(String.format("TrainerEntity with the specified id of %d does not exist", requestDto.getUserId())));
        }

        TrainerEntity trainerEntity = trainerService.update(new TrainerEntity(
                requestDto.getUserId(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.isActive(),
                requestDto.getSpecializationType()
        ));

        TrainerUpdateResponseDto responseDto = new TrainerUpdateResponseDto(
                trainerEntity.getUserId(),
                trainerEntity.getFirstName(),
                trainerEntity.getLastName(),
                trainerEntity.getUsername(),
                trainerEntity.getPassword(),
                trainerEntity.isActive(),
                trainerEntity.getSpecialization()
        );

        LOGGER.info("Successfully updated a TrainerEntity according to the TrainerUpdateRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainerRetrievalResponseDto retrieveTrainer(Long trainerId) {
        Assert.notNull(trainerId, "TrainerEntity id must not be null");
        LOGGER.info("Retrieving a TrainerEntity with an id of {}", trainerId);

        if(trainerId <= 0) {
            return new TrainerRetrievalResponseDto(List.of(String.format("TrainerEntity id must be positive: %d specified", trainerId)));
        }

        if(trainerService.findById(trainerId).isEmpty()) {
            return new TrainerRetrievalResponseDto(List.of(String.format("A TrainerEntity with a specified id of %d not found", trainerId)));
        }

        TrainerEntity trainerEntity = trainerService.select(trainerId);
        TrainerRetrievalResponseDto responseDto = new TrainerRetrievalResponseDto(
                trainerEntity.getUserId(),
                trainerEntity.getFirstName(),
                trainerEntity.getLastName(),
                trainerEntity.getUsername(),
                trainerEntity.getPassword(),
                trainerEntity.isActive(),
                trainerEntity.getSpecialization()
        );

        LOGGER.info("Successfully retrieved a TrainerEntity with an id of {}, response - {}", trainerId, responseDto);
        return responseDto;
    }
}
