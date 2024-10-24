package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
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
import org.example.service.core.user.AuthenticatorService;
import org.example.service.core.user.IdService;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.user.UserService;
import org.example.validator.TrainerValidator;
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

    private final TrainerService trainerService;
    private final UserService userService;
    private final IdService idService;
    private final TrainerMapper trainerMapper;
    private final TrainerValidator trainerValidator;
    private final AuthenticatorService authenticatorService;

    /**
     * Constructor.
     */
    public TrainerController(TrainerService trainerService,
                             UserService userService,
                             IdService idService,
                             TrainerMapper trainerMapper,
                             TrainerValidator trainerValidator, AuthenticatorService authenticatorService) {
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

        log.info("Attempting a registration of a trainer according to the {}", requestDto);

        // validations
        trainerValidator.validateCreateTrainer(requestDto);

        // service and mapper calls
        TrainerCreationResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerCreationResponseDto(
            trainerService.create(trainerMapper.mapTrainerCreationRequestDtoToTrainerEntity(requestDto)));

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of attempted registration - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Get trainers not assigned to a trainee.
     */
    @GetMapping(value = "/not-assigned-to-trainee/{username}")
    public ResponseEntity<RestResponse> retrieveAllTrainersNotAssignedToTrainee(
        @PathVariable(value = "username") String username, HttpServletRequest request) {

        log.info("Attempting a retrieval of trainers not assigned to trainee with a username of {}", username);

        // validations
        trainerValidator.validateRetrieveAllTrainersNotAssignedToTrainee(
            new RetrieveAllTrainersNotAssignedToTraineeRequestDto(username));

        // service and mapper calls
        TrainerListRetrievalResponseDto responseDto = new TrainerListRetrievalResponseDto(
            trainerService.findAllNotAssignedTo(username).stream().map(trainerMapper::mapTrainerEntityToTrainerDto)
                .toList(), username);

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of retrieval of trainers not assigned to a trainee - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Update trainer.
     */
    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> update(
        @RequestBody TrainerUpdateRequestDto requestDto, HttpServletRequest request) {

        log.info("Attempting an update of a trainer, request - {}", requestDto);

        // validations
        trainerValidator.validateUpdateTrainer(requestDto);

        // service and mapper calls
        userService.update(trainerMapper.mapTrainerUpdateRequestDtoToUserEntity(requestDto));
        TrainerUpdateResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerUpdateResponseDto(
            trainerService.update(trainerMapper.mapTrainerUpdateRequestDtoToTrainerEntity(requestDto)));

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of update of trainers - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Retrieve a trainer.
     */
    @GetMapping("/{username}")
    public ResponseEntity<RestResponse> retrieve(
        @PathVariable(value = "username") String username, HttpServletRequest request) {

        log.info("Attempting a retrieval of a single trainer profile, username - {}", username);
        TrainerRetrievalByUsernameRequestDto requestDto = new TrainerRetrievalByUsernameRequestDto(username);

        // validations
        trainerValidator.validateRetrieveTrainer(requestDto);

        // service and mapper calls
        TrainerRetrievalResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerRetrievalResponseDto(
            trainerService.findByUsername(requestDto.getUsername()).get());

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of trainer retrieval - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Switch activation state of a trainee.
     */
    @PatchMapping(value = "/switch-active/{username}")
    public ResponseEntity<RestResponse> switchActivationState(
        @PathVariable(value = "username") String username) {

        log.info("Attempting to switch the activation state of a trainer, username - {}", username);
        TrainerSwitchActivationStateRequestDto requestDto =
            new TrainerSwitchActivationStateRequestDto(username);

        // validations
        trainerValidator.validateSwitchActivationState(requestDto);

        // service and mapper calls
        userService.update(trainerMapper.mapSwitchActivationStateRequestDtoToUserEntity(requestDto));

        // response
        TrainerSwitchActivationStateResponseDto responseDto =
            new TrainerSwitchActivationStateResponseDto(HttpStatus.OK);
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of switching the activation state of a trainer - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
