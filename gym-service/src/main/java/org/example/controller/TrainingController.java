package org.example.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainingDto;
import org.example.dto.request.ActionType;
import org.example.dto.request.TrainerWorkingHoursRequestDto;
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
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/trainings")
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;
    private final TrainingValidator trainingValidator;
    private final PermissionService permissionService;
    private final WebClient.Builder webClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction loadBalancer;

    /**
     * Constructor.
     */
    public TrainingController(TrainingService trainingService,
                              TrainingMapper trainingMapper,
                              TrainingValidator trainingValidator, PermissionService permissionService,
                              WebClient.Builder webClientBuilder,
                              ReactorLoadBalancerExchangeFilterFunction loadBalancer) {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
        this.trainingValidator = trainingValidator;
        this.permissionService = permissionService;
        this.webClientBuilder = webClientBuilder;
        this.loadBalancer = loadBalancer;
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

        // sending a request to the microservice
        TrainerWorkingHoursRequestDto body = new TrainerWorkingHoursRequestDto(
            trainingEntity.getTrainer().getUser().getUsername(),
            trainingEntity.getTrainer().getUser().getFirstName(),
            trainingEntity.getTrainer().getUser().getLastName(),
            trainingEntity.getTrainer().getUser().getIsActive(),
            trainingEntity.getDate(),
            trainingEntity.getDuration(),
            ActionType.ADD
        );
        Mono<ResponseEntity> responseEntityMono =
            webClientBuilder.build().get()
                .uri("http://trainer-working-hour-service/trainer")
                .retrieve().bodyToMono(ResponseEntity.class);
        log.info("Response Entity Mono - {}", responseEntityMono);

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
