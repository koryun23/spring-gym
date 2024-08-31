package org.example.facade.impl;

import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.Trainee;
import org.example.entity.Trainer;
import org.example.facade.core.TrainerFacade;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.params.TrainerCreateParams;
import org.example.service.params.TrainerUpdateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

public class TrainerFacadeImpl implements TrainerFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerFacadeImpl.class);

    private final TrainerService trainerService;
    private final TraineeService traineeService;

    @Autowired
    public TrainerFacadeImpl(TrainerService trainerService, TraineeService traineeService) {
        Assert.notNull(trainerService, "Trainer Service must not be null");
        Assert.notNull(traineeService, "Trainee Service must not be null");
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    @Override
    public TrainerCreationResponseDto createTrainer(TrainerCreationRequestDto requestDto) {

        LOGGER.info("Creating a Trainer according to the TrainerCreationRequestDto - {}", requestDto);

        String username = uniqueUsername(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUserId()
        );

        Trainer trainer = trainerService.create(new TrainerCreateParams(
                requestDto.getUserId(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                username,
                uniquePassword(),
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
        LOGGER.info("Successfully created a Trainer according to the TrainerCreationRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainerUpdateResponseDto updateTrainer(TrainerUpdateRequestDto requestDto) {

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

        LOGGER.info("Retrieving a Trainer with an id of {}", trainerId);

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

    private String uniqueUsername(String firstName, String lastName, Long id) {
        String temporaryUsername = firstName + "." + lastName;
        Optional<Trainer> optionalTrainer = trainerService.findByUsername(temporaryUsername);

        if(optionalTrainer.isEmpty()) return temporaryUsername;

        temporaryUsername += ("." + id);
        Optional<Trainee> optionalTrainee = traineeService.findByUsername(temporaryUsername);

        if(optionalTrainee.isEmpty()) return temporaryUsername;
        return temporaryUsername + ".trainer";
    }

    private String uniquePassword() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
