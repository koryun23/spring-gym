package org.example.facade.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.TrainingTypeListRetrievalRequestDto;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.facade.core.TrainingTypeFacade;
import org.example.mapper.training.TrainingTypeMapper;
import org.example.service.core.TrainingTypeService;
import org.example.validator.TrainingTypeValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrainingTypeFacadeImpl implements TrainingTypeFacade {

    private final TrainingTypeService trainingTypeService;
    private final TrainingTypeMapper trainingTypeMapper;
    private final TrainingTypeValidator trainingTypeValidator;

    public TrainingTypeFacadeImpl(TrainingTypeService trainingTypeService,
                                  TrainingTypeMapper trainingTypeMapper,
                                  TrainingTypeValidator trainingTypeValidator) {
        this.trainingTypeService = trainingTypeService;
        this.trainingTypeMapper = trainingTypeMapper;
        this.trainingTypeValidator = trainingTypeValidator;
    }

    @Override
    public RestResponse<TrainingTypeListRetrievalResponseDto> getTrainingTypes(
        TrainingTypeListRetrievalRequestDto requestDto) {
        log.info("Retrieving a list of training types");

        // validations
        RestResponse<TrainingTypeListRetrievalResponseDto> restResponse =
            trainingTypeValidator.validateGetTrainingTypes(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        TrainingTypeListRetrievalResponseDto responseDto =
            trainingTypeMapper.mapTrainingTypeEntityListToTrainingTypeListRetrievalResponseDto(
                trainingTypeService.findAll());

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Successfully retrieved a list of all training types, result - {}", restResponse);
        return restResponse;
    }
}
