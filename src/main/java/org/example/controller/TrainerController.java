package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.facade.core.TrainerFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/trainers")
public class TrainerController {

    private TrainerFacade trainerFacade;

    public TrainerController(TrainerFacade trainerFacade) {
        this.trainerFacade = trainerFacade;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RestResponse<TrainerCreationResponseDto>> register(@RequestBody TrainerCreationRequestDto requestDto) {
        log.info("Attempting a registration of a trainer according to the {}", requestDto);
        RestResponse<TrainerCreationResponseDto> response = trainerFacade.createTrainer(requestDto);

        log.info("Response of attempted registration - {}", response);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
