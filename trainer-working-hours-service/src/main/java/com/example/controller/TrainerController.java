package com.example.controller;

import com.example.dto.ActionType;
import com.example.dto.TrainerDto;
import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursResponseDto;
import com.example.entity.TrainerEntity;
import com.example.mapper.TrainerMapper;
import com.example.service.core.TrainerService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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

    public TrainerController(TrainerService trainerService, TrainerMapper trainerMapper) {
        this.trainerService = trainerService;
        this.trainerMapper = trainerMapper;
    }

    /**
     * A method for updating the working hours of a trainee on the given year and month. In case
     * no record is found for the given trainer, a new record is created.
     */
    @PutMapping(consumes = "application/json")
    public ResponseEntity<TrainerWorkingHoursResponseDto> updateWorkingHours(
        @RequestBody TrainerWorkingHoursRequestDto requestDto) {
        log.debug("Calculating working hours of the given trainer - {}", requestDto.getTrainerUsername());

        TrainerEntity trainerEntity = trainerMapper.mapTrainerWorkingHoursRequestDtoToTrainerEntity(requestDto);
        TrainerEntity savedTrainerEntity = null;
        if (requestDto.getActionType() == ActionType.ADD) {
            savedTrainerEntity = trainerService.addWorkingHours(trainerEntity);
        } else {
            savedTrainerEntity = trainerService.removeWorkingHours(trainerEntity);
        }
        log.info("Successfully registered working hours of trainer, {}", savedTrainerEntity);

        return ResponseEntity.ok(
            new TrainerWorkingHoursResponseDto(requestDto.getTrainerUsername(), requestDto.getDuration().intValue()));
    }

    /**
     * A method for retrieving the working hours for all trainers on all available years and months.
     */
    @GetMapping
    public ResponseEntity<List<TrainerDto>> retrieveWorkingHours() {
        log.info("Retrieving all registered working hours of all trainers");

        List<TrainerDto> all =
            trainerService.findAll().stream().map(trainerMapper::mapTrainerEntityToTrainerDto).toList();

        log.info("Successfully retrieved all registered working hours of all trainers, {}", all);

        return ResponseEntity.ok(all);
    }
}
