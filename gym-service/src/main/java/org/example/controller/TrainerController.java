package org.example.controller;

import com.example.dto.TrainerDto;
import com.example.dto.TrainerWorkingHoursRetrievalRequestDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
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
import org.example.entity.trainer.TrainerEntity;
import org.example.mapper.trainer.TrainerMapper;
import org.example.messaging.MessageListenerImpl;
import org.example.security.service.PermissionService;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.trainer.TrainerWorkingHoursService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/trainers")
public class TrainerController {

    private final TrainerService trainerService;
    private final UserService userService;
    private final TrainerMapper trainerMapper;
    private final TrainerValidator trainerValidator;
    private final PermissionService permissionService;
    private final TrainerWorkingHoursService trainerWorkingHoursService;
    private final MessageListenerImpl messageListener;

    /**
     * Constructor.
     */
    public TrainerController(TrainerService trainerService,
                             UserService userService,
                             TrainerMapper trainerMapper,
                             TrainerValidator trainerValidator, PermissionService permissionService,
                             TrainerWorkingHoursService trainerWorkingHoursService,
                             MessageListenerImpl messageListener) {
        this.trainerService = trainerService;
        this.userService = userService;
        this.trainerMapper = trainerMapper;
        this.trainerValidator = trainerValidator;
        this.permissionService = permissionService;
        this.trainerWorkingHoursService = trainerWorkingHoursService;
        this.messageListener = messageListener;
    }

    /**
     * Trainer registration.
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> register(
        @RequestBody TrainerCreationRequestDto requestDto) {

        log.info("POST /trainers - {}", requestDto);

        // validations
        trainerValidator.validateCreateTrainer(requestDto);

        // service and mapper calls

        org.example.dto.plain.TrainerDto trainerDto = trainerMapper.mapTrainerCreationRequestDtoToTrainerDto(requestDto);
        TrainerCreationResponseDto responseDto = trainerMapper.mapTrainerDtoToTrainerCreationResponseDto(
            trainerService.create(trainerDto));

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response Status - {}, Response Body - {}", restResponse.getHttpStatus(), restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Get trainers not assigned to a trainee.
     */
    @GetMapping(value = "/unassigned-to/{username}")
    public ResponseEntity<RestResponse> retrieveAllTrainersNotAssignedToTrainee(
        @PathVariable(value = "username") String username) {

        log.info("GET /trainers/unassigned-to");

        // validations
        trainerValidator.validateRetrieveAllTrainersNotAssignedToTrainee(
            new RetrieveAllTrainersNotAssignedToTraineeRequestDto(username));
        permissionService.canViewTrainersNotAssignedOnTrainee(username);

        // service and mapper calls
        TrainerListRetrievalResponseDto responseDto = new TrainerListRetrievalResponseDto(
            trainerService.findAllNotAssignedTo(username).stream().map(trainerMapper::mapTrainerEntityToTrainerDto)
                .toList());

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response Status - {}, Response Body - {}", restResponse.getHttpStatus(), restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Update trainer.
     */
    @PutMapping(value = "/{username}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> update(
        @RequestBody TrainerUpdateRequestDto requestDto,
        @PathVariable(value = "username") String username) {

        log.info("PUT /trainers - {}", requestDto);

        // validations
        trainerValidator.validateUpdateTrainer(username, requestDto);
        permissionService.canUpdateTrainer(username);

        // service and mapper calls
        TrainerEntity trainer = trainerMapper.mapTrainerUpdateRequestDtoToTrainerEntity(requestDto);
        trainerService.update(trainerMapper.mapTrainerUpdateRequestDtoToTrainerDto(requestDto));

        TrainerUpdateResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerUpdateResponseDto(trainer);

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response Status - {}, Response Body - {}", restResponse.getHttpStatus(), restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Retrieve a trainer.
     */
    @GetMapping("/{username}")
    public ResponseEntity<RestResponse> retrieve(
        @PathVariable(value = "username") String username) {

        log.info("GET /trainers");
        TrainerRetrievalByUsernameRequestDto requestDto = new TrainerRetrievalByUsernameRequestDto(username);

        // validations
        trainerValidator.validateRetrieveTrainer(requestDto);
        permissionService.canViewTrainer(username);

        // service and mapper calls
        TrainerEntity trainerEntity = trainerService.findByUsername(requestDto.getUsername()).get();
        TrainerRetrievalResponseDto responseDto = trainerMapper.mapTrainerEntityToTrainerRetrievalResponseDto(
            trainerEntity);

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response Status - {}, Response Body - {}", restResponse.getHttpStatus(), restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Switch activation state of a trainee.
     */
    @PatchMapping(value = "/{username}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> switchActivationState(
        @PathVariable(value = "username") String username,
        @RequestBody TrainerSwitchActivationStateRequestDto requestDto) {

        log.info("PATCH /trainers");

        // validations
        trainerValidator.validateSwitchActivationState(username, requestDto);
        permissionService.canUpdateTrainer(username);

        // service and mapper calls
        userService.switchActivationState(username, requestDto.getState());

        // response
        TrainerSwitchActivationStateResponseDto responseDto =
            new TrainerSwitchActivationStateResponseDto(HttpStatus.OK);
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response Status - {}, Response Body - {}", restResponse.getHttpStatus(), restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Get working hours of a trainer.
     */
    @GetMapping(value = "/hours/{username}", produces = "application/json")
    public ResponseEntity<RestResponse> getWorkingHours(
        @PathVariable(value = "username") String username,
        @RequestParam(value = "month", required = false) Long month,
        @RequestParam(value = "year", required = false) Long year) {
        log.info("/GET /trainers/hours");

        // no permissions

        trainerWorkingHoursService.getWorkingHours(new TrainerWorkingHoursRetrievalRequestDto(username, month, year));
        List<TrainerDto> trainerDtos = getTrainerWorkingHourDtosFromCompletableFuture();
        RestResponse restResponse =
            new RestResponse(trainerDtos, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        ResponseEntity<RestResponse> responseEntity = new ResponseEntity<>(restResponse, restResponse.getHttpStatus());

        log.info("Response Status - {}, Response Body - {}", responseEntity.getStatusCode(), responseEntity);
        return responseEntity;
    }

    // TODO: fix this
    private List<TrainerDto> getTrainerWorkingHourDtosFromCompletableFuture() {

        try {
            return messageListener.getCompletableFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
