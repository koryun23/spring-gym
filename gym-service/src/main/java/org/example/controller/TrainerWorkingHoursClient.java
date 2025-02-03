package org.example.controller;

import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursResponseDto;
import com.example.entity.TrainerEntity;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("trainer-working-hours-service")
public interface TrainerWorkingHoursClient {

    @PutMapping("/trainer/hours")
    ResponseEntity<TrainerWorkingHoursResponseDto> updateWorkingHours(TrainerWorkingHoursRequestDto requestDto);

    @GetMapping("/trainer/hours")
    ResponseEntity<List<TrainerEntity>> retrieveWorkingHours();
}
