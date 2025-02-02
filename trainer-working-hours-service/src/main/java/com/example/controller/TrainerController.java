package com.example.controller;

import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursResponseDto;
import com.example.entity.TrainerEntity;
import com.example.mapper.TrainerMapper;
import com.example.service.core.TrainerService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TrainerController {

    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;

    public TrainerController(TrainerService trainerService, TrainerMapper trainerMapper) {
        this.trainerService = trainerService;
        this.trainerMapper = trainerMapper;
    }

    @PostMapping(value = "/trainer-working-hours", consumes = "application/json")
    public ResponseEntity<TrainerWorkingHoursResponseDto> calculateWorkingHours(@RequestBody TrainerWorkingHoursRequestDto requestDto) {
        log.info("Calculating working hours of the given trainer - {}", requestDto);

        TrainerEntity trainerEntity = trainerMapper.mapTrainerWorkingHoursRequestDtoToTrainerEntity(requestDto);
        TrainerEntity savedTrainerEntity = trainerService.create(trainerEntity);
        log.info("Successfully registered working hours of trainer, {}", savedTrainerEntity);

        return ResponseEntity.ok(new TrainerWorkingHoursResponseDto(requestDto.getTrainerUsername(), requestDto.getDuration().intValue()));
    }

    @GetMapping(value = "/trainer-working-hours")
    public ResponseEntity<List<TrainerEntity>> retrieveWorkingHours() {
        log.info("Retrieving all registered working hours of all trainers");

        List<TrainerEntity> all = trainerService.findAll();

        log.info("Successfully retrieved all registered working hours of all trainers, {}", all);

        return ResponseEntity.ok(all);
    }
}
