package com.example.controller;

import com.example.dto.RestResponse;
import com.example.dto.TrainerDto;
import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursResponseDto;
import com.example.entity.TrainerEntity;
import com.example.mapper.TrainerMapper;
import com.example.service.TrainerService;
import com.example.strategy.TrainerWorkingHoursUpdateStrategyFactory;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/trainer/hours")
public class TrainerController {

    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;
    private final TrainerWorkingHoursUpdateStrategyFactory strategyFactory;

    /**
     * Constructor.
     */
    public TrainerController(TrainerService trainerService, TrainerMapper trainerMapper,
                             TrainerWorkingHoursUpdateStrategyFactory strategyFactory) {
        this.trainerService = trainerService;
        this.trainerMapper = trainerMapper;
        this.strategyFactory = strategyFactory;
    }

    /**
     * A method for updating the working hours of a trainee on the given year and month. In case
     * no record is found for the given trainer, a new record is created.
     */
    @PutMapping(consumes = "application/json")
    public ResponseEntity<RestResponse> updateWorkingHours(
        @RequestBody TrainerWorkingHoursRequestDto requestDto) {
        log.info("GET /trainers/hours, Trainer Id - {}", requestDto.getTrainerId());

        TrainerEntity trainerEntity = trainerMapper.mapTrainerWorkingHoursRequestDtoToTrainerEntity(requestDto);
        TrainerEntity savedTrainerEntity =
            trainerService.updateWorkingHours(trainerEntity, strategyFactory.getStrategy(requestDto.getActionType()));
        TrainerWorkingHoursResponseDto trainerWorkingHoursResponseDto =
            trainerMapper.mapTrainerEntityToTrainerWorkingHoursResponseDto(savedTrainerEntity);

        ResponseEntity<TrainerWorkingHoursResponseDto> responseEntity = ResponseEntity.ok(
            new TrainerWorkingHoursResponseDto(requestDto.getTrainerUsername(), requestDto.getDuration()));

        log.info("Response Status - {}, Trainer Id - {}", responseEntity.getStatusCode(), requestDto.getTrainerId());

        RestResponse restResponse = new RestResponse(trainerWorkingHoursResponseDto, HttpStatus.OK, LocalDateTime.now(),
            Collections.emptyList());

        log.info("Rest Response - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * A method for retrieving the working hours for all trainers on all available years and months.
     */
    @GetMapping
    public ResponseEntity<RestResponse> retrieveWorkingHours() {
        log.info("Retrieving all registered working hours of all trainers");

        List<TrainerDto> all =
            trainerService.findAll().stream().map(trainerMapper::mapTrainerEntityToTrainerDto).toList();

        log.info("Successfully retrieved all registered working hours of all trainers, {}", all);

        RestResponse restResponse = new RestResponse(all, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Rest Response - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
