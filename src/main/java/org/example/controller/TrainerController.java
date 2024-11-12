package org.example.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.plain.TrainerDto;
import org.example.dto.request.RetrieveAllTrainersNotAssignedToTraineeRequestDto;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerRetrievalByUsernameRequestDto;
import org.example.dto.request.TrainerSwitchActivationStateRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerListRetrievalResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerSwitchActivationStateResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.TrainerEntity;
import org.example.mapper.trainer.TrainerMapper;
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
    private final TrainerMapper trainerMapper;
    private final TrainerValidator trainerValidator;

    /**
     * Constructor.
     */
    public TrainerController(TrainerService trainerService,
                             UserService userService,
                             TrainerMapper trainerMapper,
                             TrainerValidator trainerValidator) {
        this.trainerService = trainerService;
        this.userService = userService;
        this.trainerMapper = trainerMapper;
        this.trainerValidator = trainerValidator;
    }

    /**
     * Trainer registration.
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> register(
        @RequestBody TrainerCreationRequestDto requestDto) {

        log.info("Attempting a registration of a trainer according to the {}", requestDto);

        // validations
        trainerValidator.validateCreateTrainer(requestDto);

        // service and mapper calls
        TrainerDto trainerDto = trainerMapper.mapTrainerCreationRequestDtoToTrainerDto(requestDto);
        TrainerCreationResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerCreationResponseDto(
            trainerService.create(trainerDto));

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of attempted registration - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Get trainers not assigned to a trainee.
     */
    @GetMapping(value = "/unassigned-to/{username}")
    public ResponseEntity<RestResponse> retrieveAllTrainersNotAssignedToTrainee(
        @PathVariable(value = "username") String username) {

        log.info("Attempting a retrieval of trainers not assigned to trainee with a username of {}", username);

        // validations
        trainerValidator.validateRetrieveAllTrainersNotAssignedToTrainee(
            new RetrieveAllTrainersNotAssignedToTraineeRequestDto(username));

        // service and mapper calls
        TrainerListRetrievalResponseDto responseDto = new TrainerListRetrievalResponseDto(
            trainerService.findAllNotAssignedTo(username).stream().map(trainerMapper::mapTrainerEntityToTrainerDto)
                .toList());

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of retrieval of trainers not assigned to a trainee - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Update trainer.
     */
    @PutMapping(value = "/{username}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> update(
        @RequestBody TrainerUpdateRequestDto requestDto,
        @PathVariable(value = "username") String username) {

        log.info("Attempting an update of a trainer, request - {}", requestDto);

        // validations
        requestDto.setUsername(username);
        trainerValidator.validateUpdateTrainer(requestDto);

        // service and mapper calls
        TrainerEntity trainer = trainerMapper.mapTrainerUpdateRequestDtoToTrainerEntity(requestDto);
        trainerService.update(trainerMapper.mapTrainerUpdateRequestDtoToTrainerDto(requestDto));

        TrainerUpdateResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerUpdateResponseDto(trainer);

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
        @PathVariable(value = "username") String username) {

        log.info("Attempting a retrieval of a single trainer profile, username - {}", username);
        TrainerRetrievalByUsernameRequestDto requestDto = new TrainerRetrievalByUsernameRequestDto(username);

        // validations
        trainerValidator.validateRetrieveTrainer(requestDto);

        // service and mapper calls
        TrainerEntity trainerEntity = trainerService.findByUsername(requestDto.getUsername()).get();
        TrainerRetrievalResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerRetrievalResponseDto(
            trainerEntity);

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of trainer retrieval - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Switch activation state of a trainee.
     */
    @PatchMapping(value = "/{username}")
    public ResponseEntity<RestResponse> switchActivationState(
        @PathVariable(value = "username") String username) {

        log.info("Attempting to switch the activation state of a trainer, username - {}", username);
        TrainerSwitchActivationStateRequestDto requestDto =
            new TrainerSwitchActivationStateRequestDto(username);

        // validations
        trainerValidator.validateSwitchActivationState(requestDto);

        // service and mapper calls
        userService.switchActivationState(username);

        // response
        TrainerSwitchActivationStateResponseDto responseDto =
            new TrainerSwitchActivationStateResponseDto(HttpStatus.OK);
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of switching the activation state of a trainer - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
