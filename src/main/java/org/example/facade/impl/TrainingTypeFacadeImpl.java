package org.example.facade.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.entity.TrainingTypeEntity;
import org.example.facade.core.TrainingTypeFacade;
import org.example.service.core.TrainingTypeService;

@Slf4j
public class TrainingTypeFacadeImpl implements TrainingTypeFacade {

    private final TrainingTypeService trainingTypeService;

    public TrainingTypeFacadeImpl(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @Override
    public TrainingTypeListRetrievalResponseDto getTrainingTypes() {
        log.info("Retrieving a list of training types");
        List<TrainingTypeEntity> all = trainingTypeService.findAll();

        TrainingTypeListRetrievalResponseDto responseDto =
            new TrainingTypeListRetrievalResponseDto(
                all.stream().map(trainingType -> new TrainingTypeDto(trainingType.getTrainingType())).toList());

        log.info("Successfully retrieved a list of all training types, result - {}", responseDto);
        return responseDto;
    }
}
