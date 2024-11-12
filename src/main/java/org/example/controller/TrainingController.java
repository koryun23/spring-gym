package org.example.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainingDto;
import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.request.TrainingListRetrievalByTraineeRequestDto;
import org.example.dto.request.TrainingListRetrievalByTrainerRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingListRetrievalResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.example.exception.TrainingTypeNotFoundException;
import org.example.mapper.training.TrainingMapper;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.training.TrainingService;
import org.example.service.core.training.TrainingTypeService;
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
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingTypeService trainingTypeService;
    private final TrainingMapper trainingMapper;
    private final TrainingValidator trainingValidator;

    /**
     * Constructor.
     */
    public TrainingController(TrainingService trainingService,
                              TraineeService traineeService,
                              TrainerService trainerService,
                              TrainingTypeService trainingTypeService,
                              TrainingMapper trainingMapper,
                              TrainingValidator trainingValidator) {
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingTypeService = trainingTypeService;
        this.trainingMapper = trainingMapper;
        this.trainingValidator = trainingValidator;
    }

    /**
     * Creation of training.
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> create(@RequestBody
                                               TrainingCreationRequestDto requestDto) {

        log.info("Attempting to create a training, request - {}", requestDto);

        // validations
        RestResponse restResponse = trainingValidator.validateCreateTraining(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        TraineeEntity trainee = traineeService.selectByUsername(requestDto.getTraineeUsername());
        TrainerEntity trainer = trainerService.selectByUsername(requestDto.getTrainerUsername());

        TrainingDto trainingDto = trainingMapper.mapTrainingCreationRequestDtoToTrainingDto(requestDto);
        TrainingEntity trainingEntity = trainingService.create(trainingDto);
        TrainingCreationResponseDto responseDto =
            trainingMapper.mapTrainingEntityToTrainingCreationResponseDto(trainingEntity);

        // response
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of training creation - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Retrieve trainings of a trainee.
     */
    @GetMapping("/trainee/{username}")
    public ResponseEntity<RestResponse> retrieveTraineeTraining(
        @PathVariable(value = "username") String username,
        @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String from,
        @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String to,
        @RequestParam(value = "trainer", required = false) String trainerUsername,
        @RequestParam(value = "type", required = false) Long specializationId) {

        log.info("Attempting to retrieve trainings of a trainee, username - {}", username);
        TrainingListRetrievalByTraineeRequestDto requestDto =
            new TrainingListRetrievalByTraineeRequestDto(
                username,
                from,
                to,
                trainerUsername,
                specializationId
            );

        // validation
        trainingValidator.validateRetrieveTrainingListByTrainee(requestDto);

        // service and mapper calls
        TrainingListRetrievalResponseDto responseDto =
            new TrainingListRetrievalResponseDto(requestDto.getTraineeUsername(),
                trainingMapper.mapTrainingEntityListToTrainingDtoList(
                    trainingService.findAllByTraineeUsernameAndCriteria(
                        requestDto.getTraineeUsername(),
                        requestDto.getFrom() == null ? null : Date.valueOf(requestDto.getFrom()),
                        requestDto.getTo() == null ? null : Date.valueOf(requestDto.getTo()),
                        requestDto.getTrainerUsername(),
                        requestDto.getSpecializationId()
                    )
                )
            );

        // response
        RestResponse restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Result of retrieving trainings of a trainee - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Retrieve trainings of a trainer.
     */
    @GetMapping("/trainer/{username}")
    public ResponseEntity<RestResponse> retrieveTrainerTraining(
        @PathVariable(value = "username") String username,
        @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String from,
        @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String to,
        @RequestParam(value = "trainee", required = false) String traineeUsername) {

        log.info("Attempting to retrieve trainings of a trainer, username - {}", username);

        TrainingListRetrievalByTrainerRequestDto requestDto =
            new TrainingListRetrievalByTrainerRequestDto(
                username,
                from,
                to,
                traineeUsername
            );

        // validations
        RestResponse restResponse =
            trainingValidator.validateRetrieveTrainingListByTrainer(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        String trainerUsername = requestDto.getTrainerUsername();
        List<TrainingDto> all = trainingMapper.mapTrainingEntityListToTrainingDtoList(
            trainingService.findAllByTrainerUsernameAndCriteria(
                trainerUsername,
                requestDto.getFrom() == null ? null : Date.valueOf(requestDto.getFrom()),
                requestDto.getTo() == null ? null : Date.valueOf(requestDto.getTo()),
                requestDto.getTraineeUsername()
            ));

        // response
        restResponse = new RestResponse(new TrainingListRetrievalResponseDto(trainerUsername, all), HttpStatus.OK,
            LocalDateTime.now(), Collections.emptyList());

        log.info("Result of retrieving trainings of a trainer - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
