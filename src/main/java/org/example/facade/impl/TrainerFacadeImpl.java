package org.example.facade.impl;

import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.Trainee;
import org.example.entity.Trainer;
import org.example.facade.core.TrainerFacade;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.core.UsernamePasswordService;
import org.example.service.params.TrainerCreateParams;
import org.example.service.params.TrainerUpdateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TrainerFacadeImpl implements TrainerFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerFacadeImpl.class);

    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final IdService idService;
    private final UsernamePasswordService usernamePasswordService;

    @Autowired
    public TrainerFacadeImpl(TrainerService trainerService,
                             TraineeService traineeService,
                             IdService idService,
                             UsernamePasswordService usernamePasswordService) {
        Assert.notNull(trainerService, "Trainer Service must not be null");
        Assert.notNull(traineeService, "Trainee Service must not be null");
        Assert.notNull(idService, "Id Service must not be null");
        Assert.notNull(usernamePasswordService, "Username Password Service must not be null");
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.idService = idService;
        this.usernamePasswordService = usernamePasswordService;
    }

    @Override
    public TrainerCreationResponseDto createTrainer(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        LOGGER.info("Creating a Trainer according to the TrainerCreationRequestDto - {}", requestDto);

        Long trainerId = idService.getId();

        if(!trainerService.findById(trainerId).isEmpty()) {
            return new TrainerCreationResponseDto(List.of(String.format("A Trainer with the specified id - %d, already exists", trainerId)));
        }

        String username = usernamePasswordService.username(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                trainerId
        );

        String password = usernamePasswordService.password();

        Trainer trainer = trainerService.create(new TrainerCreateParams(
                trainerId,
                requestDto.getFirstName(),
                requestDto.getLastName(),
                username,
                password,
                requestDto.isActive(),
                requestDto.getSpecializationType()
        ));
        TrainerCreationResponseDto responseDto = new TrainerCreationResponseDto(
                trainer.getUserId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getUsername(),
                trainer.getPassword(),
                trainer.isActive(),
                trainer.getSpecialization()
        );
        idService.autoIncrement();
        LOGGER.info("Successfully created a Trainer according to the TrainerCreationRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainerUpdateResponseDto updateTrainer(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");
        LOGGER.info("Updating a Trainer according to the TrainerUpdateRequestDto - {}", requestDto);

        Trainer trainer = trainerService.update(new TrainerUpdateParams(
                requestDto.getUserId(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.isActive(),
                requestDto.getSpecializationType()
        ));

        TrainerUpdateResponseDto responseDto = new TrainerUpdateResponseDto(
                trainer.getUserId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getUsername(),
                trainer.getPassword(),
                trainer.isActive(),
                trainer.getSpecialization()
        );

        LOGGER.info("Successfully updated a Trainer according to the TrainerUpdateRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainerRetrievalResponseDto retrieveTrainer(Long trainerId) {
        Assert.notNull(trainerId, "Trainer id must not be null");
        LOGGER.info("Retrieving a Trainer with an id of {}", trainerId);

        if(trainerId <= 0) {
            return new TrainerRetrievalResponseDto(List.of(String.format("Trainer id must be positive: %d specified", trainerId)));
        }

        if(trainerService.findById(trainerId).isEmpty()) {
            return new TrainerRetrievalResponseDto(List.of(String.format("A Trainer with a specified id of %d not found", trainerId)));
        }

        Trainer trainer = trainerService.select(trainerId);
        TrainerRetrievalResponseDto responseDto = new TrainerRetrievalResponseDto(
                trainer.getUserId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getUsername(),
                trainer.getPassword(),
                trainer.isActive(),
                trainer.getSpecialization()
        );

        LOGGER.info("Successfully retrieved a Trainer with an id of {}, response - {}", trainerId, responseDto);
        return responseDto;
    }
}
