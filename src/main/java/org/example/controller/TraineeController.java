package org.example.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.plain.TraineeDto;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeSwitchActivationStateResponseDto;
import org.example.entity.trainee.TraineeEntity;
import org.example.mapper.trainee.TraineeMapper;
import org.example.security.service.PermissionService;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.user.UserService;
import org.example.validator.TraineeValidator;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(value = "/trainees")
public class TraineeController {

    private final TraineeService traineeService;
    private final UserService userService;
    private final TraineeMapper traineeMapper;
    private final TraineeValidator traineeValidator;
    @Autowired
    private PermissionService permissionService;

    /**
     * Constructor.
     */
    public TraineeController(TraineeService traineeService, UserService userService, TraineeMapper traineeMapper,
                             TraineeValidator traineeValidator) {
        this.traineeService = traineeService;
        this.userService = userService;
        this.traineeMapper = traineeMapper;
        this.traineeValidator = traineeValidator;
    }

    /**
     * Trainee registration.
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> register(@RequestBody TraineeCreationRequestDto requestDto) {

        log.info("{}, Attempting a registration of a trainee according to the request - {}", MDC.get("transactionId"),
            requestDto);

        // validations
        traineeValidator.validateCreateTrainee(requestDto);

        // service and mapper calls
        TraineeEntity params = traineeMapper.mapTraineeCreationRequestDtoToTraineeEntity(requestDto);
        TraineeDto trainee = traineeService.create(params);
        TraineeCreationResponseDto responseDto = traineeMapper.mapTraineeDtoToTraineeCreationResponseDto(trainee);

        // response
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        ResponseEntity<RestResponse> responseEntity = new ResponseEntity<>(restResponse, restResponse.getHttpStatus());

        log.info("Response of a trainee registration - {}", restResponse);

        return responseEntity;
    }

    /**
     * Trainee retrieval.
     */
    @GetMapping("/{username}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<RestResponse> retrieve(@PathVariable("username") String username) {

        log.info("Attempting a retrieval of a trainee, username - {}", username);
        log.info("Currently logged in user - {}", SecurityContextHolder.getContext().getAuthentication().getName());
        traineeValidator.validateRetrieveTrainee(username);

        // service and mapper calls + response

        TraineeEntity trainee = traineeService.selectByUsername(username);
        RestResponse restResponse =
            new RestResponse(traineeMapper.mapTraineeEntityToTraineeRetrievalResponseDto(trainee), HttpStatus.OK,
                LocalDateTime.now(), Collections.emptyList());

        log.info("Response of a trainee retrieval - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Trainee update.
     */
    @PreAuthorize("#username == authentication.name")
    @PutMapping(value = "/{username}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> update(@RequestBody TraineeUpdateRequestDto requestDto,
                                               @PathVariable(value = "username") String username) {

        log.info("Attempting an update of a trainee, request - {}", requestDto);

        // validations
        traineeValidator.validateUpdateTrainee(username, requestDto);

        // service and mapper calls
        TraineeEntity trainee = traineeMapper.mapTraineeUpdateRequestDtoToTraineeEntity(requestDto);
        TraineeEntity traineeEntity = traineeService.update(trainee);

        // response
        RestResponse restResponse =
            new RestResponse(traineeMapper.mapTraineeEntityToTraineeUpdateResponseDto(traineeEntity), HttpStatus.OK,
                LocalDateTime.now(), Collections.emptyList());

        log.info("Response of a trainee update - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Trainee deletion by username.
     */
    @PreAuthorize("#username == authentication.name")
    @DeleteMapping("/{username}")
    public ResponseEntity<RestResponse> delete(@PathVariable(value = "username") String username) {

        log.info("Attempting a deletion of a trainee, username - {}", username);
        TraineeDeletionByUsernameRequestDto requestDto = new TraineeDeletionByUsernameRequestDto(username);

        // validations
        traineeValidator.validateDeleteTrainee(requestDto);

        // service calls
        traineeService.delete(requestDto.getUsername());

        // response
        RestResponse restResponse =
            new RestResponse(new TraineeDeletionResponseDto(HttpStatus.OK), HttpStatus.OK, LocalDateTime.now(),
                Collections.emptyList());

        log.info("Response of a trainee deletion - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Trainee switch activation state.
     */
    @PreAuthorize("#username == authentication.name")
    @PatchMapping(value = "/{username}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> switchActivationState(
        @RequestBody TraineeSwitchActivationStateRequestDto requestDto,
        @PathVariable("username") String username) {

        log.info("Attempting to switch the activation state of a trainee, username - {}", username);

        // validations
        traineeValidator.validateSwitchActivationState(requestDto);

        // service and mapper calls
        userService.switchActivationState(username, requestDto.getState());

        // response
        TraineeSwitchActivationStateResponseDto responseDto =
            new TraineeSwitchActivationStateResponseDto(HttpStatus.OK);
        RestResponse restResponse =
            new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of switching the activation state of a trainee - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
