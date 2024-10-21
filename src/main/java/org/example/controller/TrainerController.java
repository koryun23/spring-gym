package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TraineeTrainerListUpdateRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TraineeTrainerListUpdateResponseDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerListRetrievalResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerSwitchActivationStateResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.mapper.trainer.TrainerMapper;
import org.example.service.core.AuthenticatorService;
import org.example.service.core.IdService;
import org.example.service.core.LoggingService;
import org.example.service.core.TrainerService;
import org.example.service.core.UserService;
import org.example.validator.TrainerValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/trainers")
public class TrainerController {

    private final LoggingService loggingService;
    private final TrainerService trainerService;
    private final UserService userService;
    private final IdService idService;
    private final TrainerMapper trainerMapper;
    private final TrainerValidator trainerValidator;
    private final AuthenticatorService authenticatorService;

    /**
     * Constructor.
     */
    public TrainerController(LoggingService loggingService, TrainerService trainerService,
                             UserService userService,
                             @Qualifier("trainerIdService") IdService idService,
                             TrainerMapper trainerMapper,
                             TrainerValidator trainerValidator, AuthenticatorService authenticatorService) {
        this.loggingService = loggingService;
        this.trainerService = trainerService;
        this.userService = userService;
        this.idService = idService;
        this.trainerMapper = trainerMapper;
        this.trainerValidator = trainerValidator;
        this.authenticatorService = authenticatorService;
    }

    /**
     * Trainer registration.
     */
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> register(
        @RequestBody TrainerCreationRequestDto requestDto) {

        loggingService.storeTransactionId();
        log.info("Attempting a registration of a trainer according to the {}", requestDto);

        // validations
        RestResponse restResponse = trainerValidator.validateCreateTrainer(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        trainerMapper.mapTrainerCreationRequestDto(requestDto);
        userService.create(trainerMapper.mapTrainerCreationRequestDtoToUserEntity(requestDto));
        TrainerCreationResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerCreationResponseDto(
            trainerService.create(trainerMapper.mapTrainerCreationRequestDtoToTrainerEntity(requestDto)));
        idService.autoIncrement();

        // response
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of attempted registration - {}", restResponse);

        loggingService.clear();
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Get trainers not assigned to a trainee.
     */
    @GetMapping(value = "/not-assigned-to-trainee/{username}")
    public ResponseEntity<RestResponse> retrieveAllTrainersNotAssignedToTrainee(
        @PathVariable(value = "username") String username, HttpServletRequest request) {

        loggingService.storeTransactionId();
        log.info("Attempting a retrieval of trainers not assigned to trainee with a username of {}", username);

        // validations
        if (authenticatorService.authFail(request.getHeader("username"),
            request.getHeader("password"))) {
            return new ResponseEntity<>(
                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                    List.of("Authentication failed")), HttpStatus.UNAUTHORIZED);
        }
        RestResponse restResponse =
            trainerValidator.validateRetrieveAllTrainersNotAssignedToTrainee(
                new RetrieveAllTrainersNotAssignedToTraineeRequestDto(username));

        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        TrainerListRetrievalResponseDto responseDto = new TrainerListRetrievalResponseDto(
            trainerService.findAllNotAssignedTo(username).stream().map(trainerMapper::mapTrainerEntityToTrainerDto)
                .toList(), username);

        // response
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of retrieval of trainers not assigned to a trainee - {}", restResponse);

        loggingService.clear();
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Update trainer.
     */
    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> update(
        @RequestBody TrainerUpdateRequestDto requestDto, HttpServletRequest request) {

        loggingService.storeTransactionId();

        log.info("Attempting an update of a trainer, request - {}", requestDto);

        // validations
        if (authenticatorService.authFail(request.getHeader("username"),
            request.getHeader("password"))) {
            return new ResponseEntity<>(
                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                    List.of("Authentication failed")), HttpStatus.UNAUTHORIZED);
        }

        RestResponse restResponse = trainerValidator.validateUpdateTrainer(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        userService.update(trainerMapper.mapTrainerUpdateRequestDtoToUserEntity(requestDto));
        TrainerUpdateResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerUpdateResponseDto(
            trainerService.update(trainerMapper.mapTrainerUpdateRequestDtoToTrainerEntity(requestDto)));

        // response
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of update of trainers - {}", restResponse);

        loggingService.clear();
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Retrieve a trainer.
     */
    @GetMapping("/{username}")
    public ResponseEntity<RestResponse> retrieve(
        @PathVariable(value = "username") String username, HttpServletRequest request) {

        loggingService.storeTransactionId();

        log.info("Attempting a retrieval of a single trainer profile, username - {}", username);
        TrainerRetrievalByUsernameRequestDto requestDto = new TrainerRetrievalByUsernameRequestDto(username);

        // validations
        if (authenticatorService.authFail(request.getHeader("username"),
            request.getHeader("password"))) {
            return new ResponseEntity<>(
                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                    List.of("Authentication failed")), HttpStatus.UNAUTHORIZED);
        }
        RestResponse restResponse = trainerValidator.validateRetrieveTrainer(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        TrainerRetrievalResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerRetrievalResponseDto(
            trainerService.findByUsername(requestDto.getUsername()).get());

        // response
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of trainer retrieval - {}", restResponse);

        loggingService.clear();
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Switch activation state of a trainee.
     */
    @PatchMapping(value = "/switch-active/{username}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> switchActivationState(
        @PathVariable(value = "username") String username, HttpServletRequest request) {

        loggingService.storeTransactionId();

        log.info("Attempting to switch the activation state of a trainer, username - {}", username);
        TrainerSwitchActivationStateRequestDto requestDto =
            new TrainerSwitchActivationStateRequestDto(username);

        // validations
        if (authenticatorService.authFail(request.getHeader("username"),
            request.getHeader("password"))) {
            return new ResponseEntity<>(
                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                    List.of("Authentication failed")), HttpStatus.UNAUTHORIZED);
        }
        RestResponse restResponse =
            trainerValidator.validateSwitchActivationState(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        userService.update(trainerMapper.mapSwitchActivationStateRequestDtoToUserEntity(requestDto));

        // response
        TrainerSwitchActivationStateResponseDto responseDto =
            new TrainerSwitchActivationStateResponseDto(HttpStatus.OK);
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of switching the activation state of a trainer - {}", restResponse);

        loggingService.clear();
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    // TODO: NOT WORKING

    /**
     * Update list of trainers of a trainee.
     */
    @PutMapping(value = "/trainee-trainers", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> updateTraineeTrainerList(
        @RequestBody TraineeTrainerListUpdateRequestDto requestDto, HttpServletRequest request) {

        loggingService.storeTransactionId();

        log.info("Attempting to update the trainers of a trainee, request - {}", requestDto);

        // validations
        if (authenticatorService.authFail(request.getHeader("username"),
            request.getHeader("password"))) {
            return new ResponseEntity<>(
                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                    List.of("Authentication failed")), HttpStatus.UNAUTHORIZED);
        }
        RestResponse restResponse =
            trainerValidator.validateUpdateTraineeTrainerList(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        trainerService.updateTrainersAssignedTo(requestDto.getTraineeUsername(),
            requestDto.getTrainerDtoList().stream().map(trainerMapper::mapTrainerDtoToTrainerEntity)
                .collect(Collectors.toSet()));

        // response
        TraineeTrainerListUpdateResponseDto responseDto =
            new TraineeTrainerListUpdateResponseDto(requestDto.getTrainerDtoList());
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of updating trainee's trainers list - {}", restResponse);

        loggingService.clear();
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
