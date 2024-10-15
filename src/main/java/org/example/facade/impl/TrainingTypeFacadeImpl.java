package org.example.facade.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.entity.TrainingTypeEntity;
import org.example.facade.core.TrainingTypeFacade;
import org.example.service.core.TrainingTypeService;
import org.example.service.core.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrainingTypeFacadeImpl implements TrainingTypeFacade {

    private final TrainingTypeService trainingTypeService;

    public TrainingTypeFacadeImpl(TrainingTypeService trainingTypeService, UserService userService) {
        this.trainingTypeService = trainingTypeService;
    }

    @Override
    public RestResponse<TrainingTypeListRetrievalResponseDto> getTrainingTypes() {
        log.info("Retrieving a list of training types");

        List<TrainingTypeEntity> all = trainingTypeService.findAll();

        TrainingTypeListRetrievalResponseDto responseDto =
            new TrainingTypeListRetrievalResponseDto(
                all.stream().map(trainingType -> new TrainingTypeDto(trainingType.getTrainingType())).toList());

        RestResponse<TrainingTypeListRetrievalResponseDto> restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        log.info("Successfully retrieved a list of all training types, result - {}", restResponse);
        return restResponse;
    }
}
