package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import org.example.facade.core.TrainerFacade;
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
@RequestMapping(value = "/trainers", consumes = "application/json", produces = "application/json")
public class TrainerController {

    private TrainerFacade trainerFacade;

    public TrainerController(TrainerFacade trainerFacade) {
        this.trainerFacade = trainerFacade;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RestResponse<TrainerCreationResponseDto>> register(
        @RequestBody TrainerCreationRequestDto requestDto) {
        log.info("Attempting a registration of a trainer according to the {}", requestDto);
        RestResponse<TrainerCreationResponseDto> response = trainerFacade.createTrainer(requestDto);

        log.info("Response of attempted registration - {}", response);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping(value = "/not-assigned-to-trainee")
    public ResponseEntity<RestResponse<TrainerListRetrievalResponseDto>> retrieveAllTrainersNotAssignedToTrainee(
        @RequestParam String username,
        HttpServletRequest request) {
        log.info("Attempting a retrieval of trainers not assigned to trainee with a username of {}", username);
        RestResponse<TrainerListRetrievalResponseDto> restResponse =
            trainerFacade.retrieveAllTrainersNotAssignedToTrainee(new RetrieveAllTrainersNotAssignedToTraineeRequestDto(
                request.getHeader("username"),
                request.getHeader("password"),
                username
            ));
        log.info("Response of retrieval of trainers not assigned to a trainee - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @PutMapping("/update")
    public ResponseEntity<RestResponse<TrainerUpdateResponseDto>> update(
        @RequestBody TrainerUpdateRequestDto requestDto,
        HttpServletRequest request) {
        log.info("Attempting an update of a trainer, request - {}", requestDto);
        requestDto.setUpdaterUsername(request.getHeader("username"));
        requestDto.setUpdaterPassword(request.getHeader("password"));
        RestResponse<TrainerUpdateResponseDto> restResponse = trainerFacade.updateTrainer(requestDto);
        log.info("Response of update of trainers - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @GetMapping
    public ResponseEntity<RestResponse<TrainerRetrievalResponseDto>> retrieve(@RequestParam String username,
                                                                              HttpServletRequest request) {
        log.info("Attempting a retrieval of a single trainer profile, username - {}", username);
        TrainerRetrievalByUsernameRequestDto requestDto =
            new TrainerRetrievalByUsernameRequestDto(username);
        requestDto.setRetrieverUsername(request.getHeader("username"));
        requestDto.setRetrieverPassword(request.getHeader("password"));
        RestResponse<TrainerRetrievalResponseDto> restResponse = trainerFacade.retrieveTrainerByUsername(requestDto);
        log.info("Response of trainer retrieval - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @PatchMapping("/switch-active/{username}")
    public ResponseEntity<RestResponse<TrainerSwitchActivationStateResponseDto>> switchActivationState(
        @PathVariable(value = "username") String username,
        HttpServletRequest request) {
        log.info("Attempting to switch the activation state of a trainer, username - {}", username);
        TrainerSwitchActivationStateRequestDto requestDto = new TrainerSwitchActivationStateRequestDto(
            username, request.getHeader("username"), request.getHeader("password")
        );
        RestResponse<TrainerSwitchActivationStateResponseDto> restResponse =
            trainerFacade.switchActivationState(requestDto);
        log.info("Response of switching the activation state of a trainer - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    @PutMapping("/trainee-trainers")
    public ResponseEntity<RestResponse<TraineeTrainerListUpdateResponseDto>> updateTraineeTrainerList(@RequestBody
                                                                                                      TraineeTrainerListUpdateRequestDto requestDto,
                                                                                                      HttpServletRequest request) {
        log.info("Attempting to update the trainers of a trainee, request - {}", requestDto);
        requestDto.setUpdaterUsername(request.getHeader("username"));
        requestDto.setUpdaterPassword(request.getHeader("password"));
        RestResponse<TraineeTrainerListUpdateResponseDto> restResponse =
            trainerFacade.updateTraineeTrainerList(requestDto);
        log.info("Response of updating trainee's trainers list - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
