package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/training-type")
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;
    private final TrainingTypeMapper trainingTypeMapper;
    private final TrainingTypeValidator trainingTypeValidator;

    public TrainingTypeController(TrainingTypeService trainingTypeService,
                                  TrainingTypeMapper trainingTypeMapper,
                                  TrainingTypeValidator trainingTypeValidator) {
        this.trainingTypeService = trainingTypeService;
        this.trainingTypeMapper = trainingTypeMapper;
        this.trainingTypeValidator = trainingTypeValidator;
    }

    /**
     * Retrieve training types.
     */
    @GetMapping
    public ResponseEntity<RestResponse<TrainingTypeListRetrievalResponseDto>> retrieveTrainingTypes(HttpServletRequest request) {
        log.info("Attempting the retrieval of training types");

        TrainingTypeListRetrievalRequestDto requestDto =
            new TrainingTypeListRetrievalRequestDto(request.getHeader("username"), request.getHeader("password"));

        // validations
        RestResponse<TrainingTypeListRetrievalResponseDto> restResponse =
            trainingTypeValidator.validateGetTrainingTypes(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        TrainingTypeListRetrievalResponseDto responseDto =
            trainingTypeMapper.mapTrainingTypeEntityListToTrainingTypeListRetrievalResponseDto(
                trainingTypeService.findAll());

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of training types retrieval - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
