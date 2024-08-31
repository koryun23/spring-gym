package org.example.facade.impl;

import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.Training;
import org.example.facade.core.TrainingFacade;
import org.example.service.core.IdService;
import org.example.service.core.TrainingService;
import org.example.service.params.TrainingCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class TrainingFacadeImpl implements TrainingFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingFacadeImpl.class);

    private final TrainingService trainingService;
    private final IdService idService;

    @Autowired
    public TrainingFacadeImpl(TrainingService trainingService, IdService idService) {
        Assert.notNull(trainingService, "Training Service must not be null");
        Assert.notNull(idService, "Id Service must not be null");
        this.trainingService = trainingService;
        this.idService = idService;
    }

    @Override
    public TrainingCreationResponseDto createTraining(TrainingCreationRequestDto requestDto) {

        LOGGER.info("Creating a Training according to the TrainingCreationRequestDto - {}", requestDto);

        Training training = trainingService.create(new TrainingCreateParams(
                idService.getId(),
                requestDto.getTraineeId(),
                requestDto.getTrainerId(),
                requestDto.getName(),
                requestDto.getTrainingType(),
                requestDto.getTrainingDate(),
                requestDto.getDuration()
        ));
        TrainingCreationResponseDto responseDto = new TrainingCreationResponseDto(
                training.getTrainingId(),
                training.getTraineeId(),
                training.getTrainerId(),
                training.getName(),
                training.getTrainingType(),
                training.getTrainingDate(),
                training.getDuration()
        );

        idService.autoIncrement();
        LOGGER.info("Successfully created a Training according to the TrainingCreationRequestDto - {}, response - {}", requestDto, responseDto);
        return responseDto;
    }

    @Override
    public TrainingRetrievalResponseDto retrieveTraining(Long trainingId) {

        LOGGER.info("Retrieving a Training with an id of {}", trainingId);

        Training training = trainingService.select(trainingId);
        TrainingRetrievalResponseDto responseDto = new TrainingRetrievalResponseDto(
                training.getTrainingId(),
                training.getTraineeId(),
                training.getTrainerId(),
                training.getName(),
                training.getTrainingType(),
                training.getTrainingDate(),
                training.getDuration()
        );

        LOGGER.info("Successfully retrieved a Training with an id of {}, response - {}", trainingId, responseDto);

        return responseDto;
    }
}
