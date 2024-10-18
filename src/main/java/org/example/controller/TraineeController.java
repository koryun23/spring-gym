package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeDeletionByUsernameRequestDto;
import org.example.dto.request.TraineeRetrievalByUsernameRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeDeletionResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeSwitchActivationStateResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.mapper.trainee.TraineeMapper;
import org.example.service.core.IdService;
import org.example.service.core.TraineeService;
import org.example.service.core.UserService;
import org.example.validator.TraineeValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/trainees", consumes = "application/json", produces = "application/json")
public class TraineeController {

    private final TraineeService traineeService;
    private final UserService userService;
    private final TraineeMapper traineeMapper;
    private final IdService idService;
    private final TraineeValidator traineeValidator;

    /**
     * Constructor.
     */
    public TraineeController(TraineeService traineeService,
                             UserService userService,
                             TraineeMapper traineeMapper,
                             @Qualifier("traineeIdService")
                             IdService idService,
                             TraineeValidator traineeValidator) {
        this.traineeService = traineeService;
        this.userService = userService;
        this.traineeMapper = traineeMapper;
        this.idService = idService;
        this.traineeValidator = traineeValidator;
    }

    /**
     * Trainee registration.
     */
    @PostMapping("/register")
    public ResponseEntity<RestResponse<TraineeCreationResponseDto>> register(
        @RequestBody TraineeCreationRequestDto requestDto) {

        log.info("Attempting a registration of a trainee according to the request - {}", requestDto);

        // validations
        RestResponse<TraineeCreationResponseDto> restResponse = traineeValidator.validateCreateTrainee(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        traineeMapper.mapTraineeCreationRequestDto(requestDto);
        userService.create(traineeMapper.mapTraineeCreationRequestDtoToUserEntity(requestDto));
        TraineeCreationResponseDto responseDto = traineeMapper.mapTraineeEntityToTraineeCreationResponseDto(
            traineeService.create(traineeMapper.mapTraineeCreationRequestDtoToTraineeEntity(requestDto)));
        idService.autoIncrement();

        // response
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());
        ResponseEntity<RestResponse<TraineeCreationResponseDto>> responseEntity =
            new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        log.info("Response of a trainee registration - {}", restResponse);
        return responseEntity;
    }

    /**
     * Trainee retrieval.
     */
    @GetMapping("/{username}")
    public ResponseEntity<RestResponse<TraineeRetrievalResponseDto>> retrieve(
        @PathVariable(value = "username") String username, HttpServletRequest httpServletRequest) {

        log.info("Attempting a retrieval of a trainee, username - {}", username);
        TraineeRetrievalByUsernameRequestDto requestDto = new TraineeRetrievalByUsernameRequestDto(username);
        requestDto.setRetrieverUsername(httpServletRequest.getHeader("username"));
        requestDto.setRetrieverPassword(httpServletRequest.getHeader("password"));

        // validations
        RestResponse<TraineeRetrievalResponseDto> restResponse = traineeValidator.validateRetrieveTrainee(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls + response
        restResponse = new RestResponse<>(
            traineeMapper.mapTraineeEntityToTraineeRetrievalResponseDto(traineeService.findByUsername(username).get()),
            HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of a trainee retrieval - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Trainee update.
     */
    @PutMapping("/update")
    public ResponseEntity<RestResponse<TraineeUpdateResponseDto>> update(
        @RequestBody TraineeUpdateRequestDto requestDto, HttpServletRequest httpServletRequest) {
        log.info("Attempting an update of a trainee, request - {}", requestDto);
        requestDto.setUpdaterUsername(httpServletRequest.getHeader("username"));
        requestDto.setUpdaterPassword(httpServletRequest.getHeader("password"));

        // validations
        RestResponse<TraineeUpdateResponseDto> restResponse = traineeValidator.validateUpdateTrainee(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        userService.update(traineeMapper.mapTraineeUpdateRequestDtoToUserEntity(requestDto));
        TraineeEntity traineeEntity =
            traineeService.update(traineeMapper.mapTraineeUpdateRequestDtoToTraineeEntity(requestDto));

        // response
        restResponse =
            new RestResponse<>(traineeMapper.mapTraineeEntityToTraineeUpdateResponseDto(traineeEntity), HttpStatus.OK,
                LocalDateTime.now(), Collections.emptyList());

        log.info("Response of a trainee update - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Trainee deletion by username.
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<RestResponse<TraineeDeletionResponseDto>> delete(
        @PathVariable(value = "username") String username, HttpServletRequest httpServletRequest) {

        log.info("Attempting a deletion of a trainee, username - {}", username);
        TraineeDeletionByUsernameRequestDto requestDto = new TraineeDeletionByUsernameRequestDto(username);
        requestDto.setDeleterUsername(httpServletRequest.getHeader("username"));
        requestDto.setDeleterPassword(httpServletRequest.getHeader("password"));

        // validations
        RestResponse<TraineeDeletionResponseDto> restResponse = traineeValidator.validateDeleteTrainee(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service calls
        traineeService.delete(requestDto.getUsername());

        // response
        restResponse =
            new RestResponse<>(new TraineeDeletionResponseDto(HttpStatus.OK), HttpStatus.OK, LocalDateTime.now(),
                Collections.emptyList());

        log.info("Response of a trainee deletion - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Trainee switch activation state.
     */
    @PatchMapping("/switch-active/{username}")
    public ResponseEntity<RestResponse<TraineeSwitchActivationStateResponseDto>> switchActivationState(
        @PathVariable("username") String username, HttpServletRequest httpServletRequest) {

        log.info("Attempting to switch the activation state of a trainee, username - {}", username);
        TraineeSwitchActivationStateRequestDto requestDto = new TraineeSwitchActivationStateRequestDto(username);
        requestDto.setUpdaterUsername(httpServletRequest.getHeader("username"));
        requestDto.setUpdaterPassword(httpServletRequest.getHeader("password"));

        // validations
        RestResponse<TraineeSwitchActivationStateResponseDto> restResponse =
            traineeValidator.validateSwitchActivationState(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        userService.update(traineeMapper.mapSwitchActivationStateRequestDtoToUserEntity(requestDto));

        // response
        TraineeSwitchActivationStateResponseDto responseDto =
            new TraineeSwitchActivationStateResponseDto(HttpStatus.OK);
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Response of switching the activation state of a trainee - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
