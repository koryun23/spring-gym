package org.example.service.impl.trainer;

import com.example.dto.TrainerWorkingHoursRequestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.TrainerWorkingHoursClient;
import org.example.service.core.trainer.TrainerWorkingHoursService;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrainerWorkingHoursServiceImpl implements TrainerWorkingHoursService {

    private final TrainerWorkingHoursClient trainerWorkingHoursClient;

    public TrainerWorkingHoursServiceImpl(TrainerWorkingHoursClient trainerWorkingHoursClient) {
        this.trainerWorkingHoursClient = trainerWorkingHoursClient;
    }

    @CircuitBreaker(name = "update", fallbackMethod = "updateWorkingHoursFallback")
    public void updateWorkingHours(TrainerWorkingHoursRequestDto requestDto) {
        trainerWorkingHoursClient.updateWorkingHours(requestDto);
    }

    public void updateWorkingHoursFallback(TrainerWorkingHoursRequestDto requestDto, Throwable throwable) {
        log.error("Inside updateWorkingHoursFallback method, cause - {}, request - {}", throwable, requestDto);
    }
}
