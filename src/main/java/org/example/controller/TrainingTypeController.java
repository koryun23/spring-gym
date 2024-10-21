package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.mapper.training.TrainingTypeMapper;
import org.example.service.core.AuthenticatorService;
import org.example.service.core.TrainingTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/training-types")
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;
    private final TrainingTypeMapper trainingTypeMapper;
    private final AuthenticatorService authenticatorService;

    /**
     * Constructor.
     */
    public TrainingTypeController(TrainingTypeService trainingTypeService,
                                  TrainingTypeMapper trainingTypeMapper,
                                  AuthenticatorService authenticatorService) {
        this.trainingTypeService = trainingTypeService;
        this.trainingTypeMapper = trainingTypeMapper;
        this.authenticatorService = authenticatorService;
    }

    /**
     * Retrieve training types.
     */
    @GetMapping
    public ResponseEntity<RestResponse> retrieveTrainingTypes(
        HttpServletRequest request) {

        log.info("Attempting the retrieval of training types");

        // validations
        if (authenticatorService.authFail(request.getHeader("username"),
            request.getHeader("password"))) {
            return new ResponseEntity<>(
                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                    List.of("Authentication failed")), HttpStatus.UNAUTHORIZED);
        }

        // service and mapper calls
        TrainingTypeListRetrievalResponseDto responseDto =
            trainingTypeMapper.mapTrainingTypeEntityListToTrainingTypeListRetrievalResponseDto(
                trainingTypeService.findAll());

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of training types retrieval - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
