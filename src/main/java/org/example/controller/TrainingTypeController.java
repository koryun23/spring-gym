package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.example.dto.RestResponse;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.facade.core.TrainingTypeFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/training-type")
public class TrainingTypeController {

    private TrainingTypeFacade trainingTypeFacade;

    public TrainingTypeController(TrainingTypeFacade trainingTypeFacade) {
        this.trainingTypeFacade = trainingTypeFacade;
    }

    @GetMapping
    public ResponseEntity<RestResponse<TrainingTypeListRetrievalResponseDto>> retrieveTrainingTypes() {
        log.info("Attempting the retrieval of training types");
        RestResponse<TrainingTypeListRetrievalResponseDto> restResponse = trainingTypeFacade.getTrainingTypes();
        log.info("Response of training types retrieval - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
