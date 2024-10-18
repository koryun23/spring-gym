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
import org.example.service.core.IdService;
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
@RequestMapping(value = "/trainers", consumes = "application/json", produces = "application/json")
public class TrainerController {

    private final TrainerService trainerService;
    private final UserService userService;
    private final IdService idService;
    private final TrainerMapper trainerMapper;
    private final TrainerValidator trainerValidator;

    /**
     * Constructor.
     */
    public TrainerController(TrainerService trainerService,
                             UserService userService,
                             @Qualifier("trainerIdService") IdService idService,
                             TrainerMapper trainerMapper,
                             TrainerValidator trainerValidator) {
        this.trainerService = trainerService;
        this.userService = userService;
        this.idService = idService;
        this.trainerMapper = trainerMapper;
        this.trainerValidator = trainerValidator;
    }

    /**
     * Trainer registration.
     */
    @PostMapping(value = "/register")
    public ResponseEntity<RestResponse<TrainerCreationResponseDto>> register(
        @RequestBody TrainerCreationRequestDto requestDto) {
        log.info("Attempting a registration of a trainer according to the {}", requestDto);

        // validations
        RestResponse<TrainerCreationResponseDto> restResponse = trainerValidator.validateCreateTrainer(requestDto);
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
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of attempted registration - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Get trainers not assigned to a trainee.
     */
    @GetMapping(value = "/not-assigned-to-trainee/{username}")
    public ResponseEntity<RestResponse<TrainerListRetrievalResponseDto>> retrieveAllTrainersNotAssignedToTrainee(
        @PathVariable(value = "username") String username, HttpServletRequest request) {
        log.info("Attempting a retrieval of trainers not assigned to trainee with a username of {}", username);

        // validations
        RestResponse<TrainerListRetrievalResponseDto> restResponse =
            trainerValidator.validateRetrieveAllTrainersNotAssignedToTrainee(
                new RetrieveAllTrainersNotAssignedToTraineeRequestDto(request.getHeader("username"),
                    request.getHeader("password"), username));

        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        TrainerListRetrievalResponseDto responseDto = new TrainerListRetrievalResponseDto(
            trainerService.findAllNotAssignedTo(username).stream().map(trainerMapper::mapTrainerEntityToTrainerDto)
                .toList(), username);

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of retrieval of trainers not assigned to a trainee - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Update trainer.
     */
    @PutMapping("/update")
    public ResponseEntity<RestResponse<TrainerUpdateResponseDto>> update(
        @RequestBody TrainerUpdateRequestDto requestDto, HttpServletRequest request) {
        log.info("Attempting an update of a trainer, request - {}", requestDto);
        requestDto.setUpdaterUsername(request.getHeader("username"));
        requestDto.setUpdaterPassword(request.getHeader("password"));

        // validations
        RestResponse<TrainerUpdateResponseDto> restResponse = trainerValidator.validateUpdateTrainer(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        userService.update(trainerMapper.mapTrainerUpdateRequestDtoToUserEntity(requestDto));
        TrainerUpdateResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerUpdateResponseDto(
            trainerService.update(trainerMapper.mapTrainerUpdateRequestDtoToTrainerEntity(requestDto)));

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of update of trainers - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Retrieve a trainer.
     */
    @GetMapping("/{username}")
    public ResponseEntity<RestResponse<TrainerRetrievalResponseDto>> retrieve(
        @PathVariable(value = "username") String username, HttpServletRequest request) {
        log.info("Attempting a retrieval of a single trainer profile, username - {}", username);
        TrainerRetrievalByUsernameRequestDto requestDto = new TrainerRetrievalByUsernameRequestDto(username);
        requestDto.setRetrieverUsername(request.getHeader("username"));
        requestDto.setRetrieverPassword(request.getHeader("password"));

        // validations
        RestResponse<TrainerRetrievalResponseDto> restResponse = trainerValidator.validateRetrieveTrainer(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        TrainerRetrievalResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerRetrievalResponseDto(
            trainerService.findByUsername(requestDto.getUsername()).get());

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of trainer retrieval - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Switch activation state of a trainee.
     */
    @PatchMapping("/switch-active/{username}")
    public ResponseEntity<RestResponse<TrainerSwitchActivationStateResponseDto>> switchActivationState(
        @PathVariable(value = "username") String username, HttpServletRequest request) {

        log.info("Attempting to switch the activation state of a trainer, username - {}", username);
        TrainerSwitchActivationStateRequestDto requestDto =
            new TrainerSwitchActivationStateRequestDto(request.getHeader("username"), request.getHeader("password"),
                username);

        // validations
        RestResponse<TrainerSwitchActivationStateResponseDto> restResponse =
            trainerValidator.validateSwitchActivationState(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        userService.update(trainerMapper.mapSwitchActivationStateRequestDtoToUserEntity(requestDto));

        // response
        TrainerSwitchActivationStateResponseDto responseDto =
            new TrainerSwitchActivationStateResponseDto(HttpStatus.OK);
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of switching the activation state of a trainer - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    // TODO: NOT WORKING

    /**
     * Update list of trainers of a trainee.
     */
    @PutMapping("/trainee-trainers")
    public ResponseEntity<RestResponse<TraineeTrainerListUpdateResponseDto>> updateTraineeTrainerList(
        @RequestBody TraineeTrainerListUpdateRequestDto requestDto, HttpServletRequest request) {
        log.info("Attempting to update the trainers of a trainee, request - {}", requestDto);
        requestDto.setUpdaterUsername(request.getHeader("username"));
        requestDto.setUpdaterPassword(request.getHeader("password"));

        // validations
        RestResponse<TraineeTrainerListUpdateResponseDto> restResponse =
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
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of updating trainee's trainers list - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
