package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.facade.core.TraineeFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/trainees")
public class TraineeController {

    private TraineeFacade traineeFacade;

    public TraineeController(TraineeFacade traineeFacade) {
        this.traineeFacade = traineeFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<RestResponse<TraineeCreationResponseDto>> register(@RequestBody TraineeCreationRequestDto requestDto,
                                                                             HttpServletRequest httpServletRequest) {
        log.info("Attempting a registration of a trainee according to the request - {}", requestDto);
        RestResponse<TraineeCreationResponseDto> response = traineeFacade.createTrainee(requestDto);
        ResponseEntity<RestResponse<TraineeCreationResponseDto>> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        log.info("Successfully registered a user according to the request - {}", requestDto);
        return responseEntity;
    }
}
