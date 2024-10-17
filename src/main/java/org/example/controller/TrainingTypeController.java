package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.TrainingTypeListRetrievalRequestDto;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.facade.core.TrainingTypeFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/training-type", produces = "application/json", consumes = "application/json")
public class TrainingTypeController {

    private TrainingTypeFacade trainingTypeFacade;

    public TrainingTypeController(TrainingTypeFacade trainingTypeFacade) {
        this.trainingTypeFacade = trainingTypeFacade;
    }

    @GetMapping
    public ResponseEntity<RestResponse<TrainingTypeListRetrievalResponseDto>> retrieveTrainingTypes(HttpServletRequest request) {
        log.info("Attempting the retrieval of training types");
        RestResponse<TrainingTypeListRetrievalResponseDto> restResponse = trainingTypeFacade.getTrainingTypes(
            new TrainingTypeListRetrievalRequestDto(request.getHeader("username"), request.getHeader("password"))
        );
        log.info("Response of training types retrieval - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
