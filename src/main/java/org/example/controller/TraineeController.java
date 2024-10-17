package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import org.example.facade.core.TraineeFacade;
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

    private TraineeFacade traineeFacade;

    public TraineeController(TraineeFacade traineeFacade) {
        this.traineeFacade = traineeFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<RestResponse<TraineeCreationResponseDto>> register(
        @RequestBody TraineeCreationRequestDto requestDto,
        HttpServletRequest httpServletRequest) {
        log.info("Attempting a registration of a trainee according to the request - {}", requestDto);
        RestResponse<TraineeCreationResponseDto> response = traineeFacade.createTrainee(requestDto);
        ResponseEntity<RestResponse<TraineeCreationResponseDto>> responseEntity =
            new ResponseEntity<>(response, response.getHttpStatus());
        log.info("Response of a trainee registration - {}", response);
        return responseEntity;
    }

    @GetMapping("/{username}")
    public ResponseEntity<RestResponse<TraineeRetrievalResponseDto>> retrieve(
        @PathVariable(value = "username") String username,
        HttpServletRequest httpServletRequest) {

        log.info("Attempting a retrieval of a trainee, username - {}", username);
        TraineeRetrievalByUsernameRequestDto requestDto = new TraineeRetrievalByUsernameRequestDto(username);
        requestDto.setRetrieverUsername(httpServletRequest.getHeader("username"));
        requestDto.setRetrieverPassword(httpServletRequest.getHeader("password"));
        RestResponse<TraineeRetrievalResponseDto> restResponse = traineeFacade.retrieveTrainee(requestDto);
        log.info("Response of a trainee retrieval - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @PutMapping("/update")
    public ResponseEntity<RestResponse<TraineeUpdateResponseDto>> update(
        @RequestBody TraineeUpdateRequestDto requestDto,
        HttpServletRequest httpServletRequest) {
        log.info("Attempting an update of a trainee, request - {}", requestDto);
        requestDto.setUpdaterUsername(httpServletRequest.getHeader("username"));
        requestDto.setUpdaterPassword(httpServletRequest.getHeader("password"));
        RestResponse<TraineeUpdateResponseDto> restResponse = traineeFacade.updateTrainee(requestDto);
        log.info("Response of a trainee update - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<RestResponse<TraineeDeletionResponseDto>> delete(
        @PathVariable(value = "username") String username,
        HttpServletRequest httpServletRequest) {

        log.info("Attempting a deletion of a trainee, username - {}", username);
        TraineeDeletionByUsernameRequestDto requestDto = new TraineeDeletionByUsernameRequestDto(username);
        requestDto.setDeleterUsername(httpServletRequest.getHeader("username"));
        requestDto.setDeleterPassword(httpServletRequest.getHeader("password"));
        RestResponse<TraineeDeletionResponseDto> restResponse = traineeFacade.deleteTraineeByUsername(requestDto);
        log.info("Response of a trainee deletion - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @PatchMapping("/switch-active/{username}")
    public ResponseEntity<RestResponse<TraineeSwitchActivationStateResponseDto>> switchActivationState(
        @PathVariable("username") String username,
        HttpServletRequest httpServletRequest) {

        log.info("Attempting to switch the activation state of a trainee, username - {}", username);
        TraineeSwitchActivationStateRequestDto requestDto = new TraineeSwitchActivationStateRequestDto(username);
        requestDto.setUpdaterUsername(httpServletRequest.getHeader("username"));
        requestDto.setUpdaterPassword(httpServletRequest.getHeader("password"));
        RestResponse<TraineeSwitchActivationStateResponseDto> restResponse =
            traineeFacade.switchActivationState(requestDto);
        log.info("Response of switching the activation state of a trainee - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
