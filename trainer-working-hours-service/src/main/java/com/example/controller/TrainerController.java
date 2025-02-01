package com.example.controller;

import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TrainerController {

    @PostMapping(value = "/trainer-working-hours", consumes = "application/json")
    public ResponseEntity<Object> calculateWorkingHours(@RequestBody TrainerWorkingHoursRequestDto requestDto) {
        log.info("Calculating working hours of the given trainer - {}", requestDto);
        Long durationMillis = requestDto.getDuration();
        Long durationSeconds = durationMillis / 1000;
        Long durationHours = durationSeconds / 3600;
        log.info("Working hours of the given trainer - {}", durationHours);
        return ResponseEntity.ok(new TrainerWorkingHoursResponseDto(requestDto.getTrainerUsername(), durationHours.intValue()));
    }
}
