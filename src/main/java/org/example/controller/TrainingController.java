package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.Date;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.entity.TrainingType;
import org.example.facade.core.TrainingFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/training")
public class TrainingController {

    private TrainingFacade trainingFacade;

    public TrainingController(TrainingFacade trainingFacade) {
        this.trainingFacade = trainingFacade;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<RestResponse<TrainingCreationResponseDto>> create(@RequestBody
                                                                            TrainingCreationRequestDto requestDto,
                                                                            HttpServletRequest request) {
        log.info("Attempting to create a training, request - {}", requestDto);
        requestDto.setCreatorUsername(request.getHeader("username"));
        requestDto.setCreatorPassword(request.getHeader("password"));
        RestResponse<TrainingCreationResponseDto> restResponse = trainingFacade.createTraining(requestDto);
        log.info("Response of training creation - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @GetMapping("/trainee-training/{username}")
    public ResponseEntity<RestResponse<TrainingListRetrievalResponseDto>> retrieveTraineeTraining(
        @PathVariable(value = "username") String username,
        @RequestParam String from, @RequestParam String to,
        @RequestParam String trainerUsername, @RequestParam String trainingType,
        HttpServletRequest request) {

        log.info("Attempting to retrieve trainings of a trainee, username - {}", username);
        TrainingListRetrievalByTraineeRequestDto requestDto =
            new TrainingListRetrievalByTraineeRequestDto(
                request.getHeader("username"),
                request.getHeader("password"),
                username,
                Date.valueOf(from),
                Date.valueOf(to),
                trainerUsername,
                new TrainingTypeDto(TrainingType.valueOf(TrainingType.class, trainingType))
            );
        RestResponse<TrainingListRetrievalResponseDto> restResponse =
            trainingFacade.retrieveTrainingListByTrainee(requestDto);
        log.info("Result of retrieving trainings of a trainee - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @GetMapping("/trainer-training/{username}")
    public ResponseEntity<RestResponse<TrainingListRetrievalResponseDto>> retrieveTrainerTraining(
        @PathVariable(value = "username") String username,
        @RequestParam String from, @RequestParam String to,
        @RequestParam String traineeUsername,
        HttpServletRequest request) {

        log.info("Attempting to retrieve trainings of a trainer, username - {}", username);

        TrainingListRetrievalByTrainerRequestDto requestDto =
            new TrainingListRetrievalByTrainerRequestDto(
                request.getHeader("username"),
                request.getHeader("password"),
                username,
                Date.valueOf("from"), Date.valueOf("to"),
                traineeUsername
            );

        RestResponse<TrainingListRetrievalResponseDto> restResponse =
            trainingFacade.retrieveTrainingListByTrainer(requestDto);

        log.info("Result of retrieving trainings of a trainer - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
