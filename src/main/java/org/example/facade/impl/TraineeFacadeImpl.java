package org.example.facade.impl;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.Trainee;
import org.example.entity.Trainer;
import org.example.facade.core.TraineeFacade;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.TrainerService;
import org.example.service.params.TraineeCreateParams;
import org.example.service.params.TraineeUpdateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

public class TraineeFacadeImpl implements TraineeFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeFacadeImpl.class);

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final IdService idService;

    @Autowired
    public TraineeFacadeImpl(TraineeService traineeService, TrainerService trainerService, IdService idService) {
        Assert.notNull(traineeService, "Trainee Service must not be null");
        Assert.notNull(trainerService, "Trainer Service must not be null");
        Assert.notNull(idService, "Id Service must not be null");
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.idService = idService;
    }

    @Override
    public TraineeCreationResponseDto createTrainee(TraineeCreationRequestDto requestDto) {

        LOGGER.info("Creating a Trainee based on the TraineeCreationRequestDto - {}", requestDto);

        String username = uniqueUsername(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUserId()
        );

        Trainee trainee = traineeService.create(new TraineeCreateParams(
                idService.getId(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                username,
                uniquePassword(),
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

    private String uniqueUsername(String firstName, String lastName, Long id) {
        String temporaryUsername = firstName + "." + lastName;
        Optional<Trainee> optionalTrainee = traineeService.findByUsername(temporaryUsername);

        if(optionalTrainee.isEmpty()) return temporaryUsername;

        temporaryUsername += ("." + id);
        Optional<Trainer> optionalTrainer = trainerService.findByUsername(temporaryUsername);

        if(optionalTrainer.isEmpty()) return temporaryUsername;
        return temporaryUsername + ".trainee";
    }

    private String uniquePassword() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
