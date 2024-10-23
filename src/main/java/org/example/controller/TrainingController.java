package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainingTypeDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.TrainingType;
import org.example.mapper.training.TrainingMapper;
import org.example.service.core.AuthenticatorService;
import org.example.service.core.TrainingService;
import org.example.validator.TrainingValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/trainings")
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;
    private final TrainingValidator trainingValidator;

    /**
     * Constructor.
     */
    public TrainingController(TrainingService trainingService,
                              TrainingMapper trainingMapper,
                              TrainingValidator trainingValidator) {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
        this.trainingValidator = trainingValidator;
    }

    /**
     * Creation of training.
     */
    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> create(@RequestBody
                                               TrainingCreationRequestDto requestDto,
                                               HttpServletRequest request) {

        log.info("Attempting to create a training, request - {}", requestDto);

        // validations
        RestResponse restResponse = trainingValidator.validateCreateTraining(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        TrainingCreationResponseDto responseDto = trainingMapper.mapTrainingEntityToTrainingCreationResponseDto(
            trainingService.create(trainingMapper.mapTrainingCreationRequestDtoToTrainingEntity(requestDto)));

        // response
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of training creation - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Retrieve trainings of a trainee.
     */
    @GetMapping("/trainee-training/{username}")
    public ResponseEntity<RestResponse> retrieveTraineeTraining(
        @PathVariable(value = "username") String username,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String from,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String to,
        @RequestParam(required = false) String trainerUsername,
        @RequestParam(required = false) String trainingType,
        HttpServletRequest request) {

        log.info("Attempting to retrieve trainings of a trainee, username - {}", username);
        TrainingListRetrievalByTraineeRequestDto requestDto =
            new TrainingListRetrievalByTraineeRequestDto(
                username,
                from == null ? null : Date.valueOf(from),
                to == null ? null : Date.valueOf(to),
                trainerUsername,
                trainingType == null ? null :
                    new TrainingTypeDto(TrainingType.valueOf(TrainingType.class, trainingType))
            );

        // validation
        RestResponse restResponse =
            trainingValidator.validateRetrieveTrainingListByTrainee(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        TrainingListRetrievalResponseDto responseDto =
            new TrainingListRetrievalResponseDto(requestDto.getTraineeUsername(),
                trainingMapper.mapTrainingEntityListToTrainingRetrievalResponseDtoList(
                    trainingService.findAllByTraineeUsernameAndCriteria(
                        requestDto.getTraineeUsername(),
                        requestDto.getFrom(),
                        requestDto.getTo(),
                        requestDto.getTrainerUsername(),
                        requestDto.getTrainingTypeDto() == null ? null :
                            requestDto.getTrainingTypeDto().getTrainingType()
                    )
                )
            );

        // response
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Result of retrieving trainings of a trainee - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Retrieve trainings of a trainer.
     */
    @GetMapping("/trainer-training/{username}")
    public ResponseEntity<RestResponse> retrieveTrainerTraining(
        @PathVariable(value = "username") String username,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String from,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String to,
        @RequestParam(value = "trainee", required = false) String traineeUsername,
        HttpServletRequest request) {

        log.info("Attempting to retrieve trainings of a trainer, username - {}", username);

        TrainingListRetrievalByTrainerRequestDto requestDto =
            new TrainingListRetrievalByTrainerRequestDto(
                username,
                from == null ? null : Date.valueOf(from),
                to == null ? null : Date.valueOf(to),
                traineeUsername
            );

        // validations
//        if (authenticatorService.authFail(request.getHeader("username"),
//            request.getHeader("password"))) {
//            return new ResponseEntity<>(
//                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
//                    List.of("Authentication failed")), HttpStatus.UNAUTHORIZED);
//        }
        RestResponse restResponse =
            trainingValidator.validateRetrieveTrainingListByTrainer(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        String trainerUsername = requestDto.getTrainerUsername();
        List<TrainingRetrievalResponseDto> all = trainingMapper.mapTrainingEntityListToTrainingRetrievalResponseDtoList(
            trainingService.findAllByTrainerUsernameAndCriteria(
                trainerUsername,
                requestDto.getFrom(),
                requestDto.getTo(),
                requestDto.getTraineeUsername()
            ));

        // response
        restResponse = new RestResponse(new TrainingListRetrievalResponseDto(trainerUsername, all), HttpStatus.OK,
            LocalDateTime.now(), Collections.emptyList());

        log.info("Result of retrieving trainings of a trainer - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
