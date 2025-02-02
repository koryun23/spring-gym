package org.example.controller;

import com.example.dto.TrainerWorkingHoursRequestDto;
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
import org.example.dto.response.TraineeTrainingRetrievalResponseDto;
import org.example.dto.response.TrainerTrainingRetrievalResponseDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.entity.training.TrainingEntity;
import org.example.mapper.training.TrainingMapper;
import org.example.security.service.PermissionService;
import org.example.service.core.training.TrainingService;
import org.example.validator.TrainingValidator;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
@EnableFeignClients
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;
    private final TrainingValidator trainingValidator;
    private final PermissionService permissionService;
    private final TrainerWorkingHoursClient trainerWorkingHoursClient;

    /**
     * Constructor.
     */
    public TrainingController(TrainingService trainingService,
                              TrainingMapper trainingMapper,
                              TrainingValidator trainingValidator,
                              PermissionService permissionService,
                              TrainerWorkingHoursClient trainerWorkingHoursClient) {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
        this.trainingValidator = trainingValidator;
        this.permissionService = permissionService;
        this.trainerWorkingHoursClient = trainerWorkingHoursClient;
    }

    /**
     * Creation of training.
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> create(@RequestBody
                                               TrainingCreationRequestDto requestDto) {

        log.info("Attempting to create a training, request - {}", requestDto);

        // validations
        trainingValidator.validateCreateTraining(requestDto);

        // service and mapper calls

        TrainingDto trainingDto = trainingMapper.mapTrainingCreationRequestDtoToTrainingDto(requestDto);
        TrainingEntity trainingEntity = trainingService.create(trainingDto);
        TrainingCreationResponseDto responseDto =
            trainingMapper.mapTrainingEntityToTrainingCreationResponseDto(trainingEntity);

        TrainerWorkingHoursRequestDto trainerWorkingHoursRequestDto =
            trainingMapper.mapTrainingEntityToTrainerWorkingHoursAddRequestDto(trainingEntity);

        trainerWorkingHoursClient.updateWorkingHours(trainerWorkingHoursRequestDto);

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

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
        permissionService.canViewTrainingsOfTrainee(username);

        // service and mapper calls
        List<TraineeTrainingRetrievalResponseDto> responseDtoList =
            trainingMapper.mapTrainingEntityListToTraineeTrainingRetrievalResponseDtoList(
                trainingService.findAllByTraineeUsernameAndCriteria(
                    requestDto.getTraineeUsername(),
                    requestDto.getFrom() == null ? null : Date.valueOf(requestDto.getFrom()),
                    requestDto.getTo() == null ? null : Date.valueOf(requestDto.getTo()),
                    requestDto.getTrainerUsername(),
                    requestDto.getSpecializationId()
                )
            );

        // response
        RestResponse restResponse =
            new RestResponse(responseDtoList, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

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
        trainingValidator.validateRetrieveTrainingListByTrainer(requestDto);
        permissionService.canViewTrainingsOfTrainer(username);
        // service and mapper calls
        String trainerUsername = requestDto.getTrainerUsername();
        List<TrainerTrainingRetrievalResponseDto> trainings =
            trainingMapper.mapTrainingEntityListToTrainerTrainingRetrievalResponseDtoList(
                trainingService.findAllByTrainerUsernameAndCriteria(
                    trainerUsername,
                    requestDto.getFrom() == null ? null : Date.valueOf(requestDto.getFrom()),
                    requestDto.getTo() == null ? null : Date.valueOf(requestDto.getTo()),
                    requestDto.getTraineeUsername()
                ));

        // response
        RestResponse restResponse =
            new RestResponse(trainings, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Result of retrieving trainings of a trainer - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
