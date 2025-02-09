package org.example.service.impl.trainer;

import com.example.dto.TrainerWorkingHoursRequestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.TrainerWorkingHoursClient;
import org.example.service.core.trainer.TrainerWorkingHoursService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrainerWorkingHoursServiceImpl implements TrainerWorkingHoursService {

    private final TrainerWorkingHoursClient trainerWorkingHoursClient;

    public TrainerWorkingHoursServiceImpl(TrainerWorkingHoursClient trainerWorkingHoursClient) {
        this.trainerWorkingHoursClient = trainerWorkingHoursClient;
    }

    /**
     * Method for updating the working hours of a trainee given by the parameter.
     * Uses CircuitBreaker as a fault tolerance strategy.
     *
     * @param requestDto TrainerWorkingHoursRequestDto
     */
    @CircuitBreaker(name = "update", fallbackMethod = "updateWorkingHoursFallback")
    public void updateWorkingHours(TrainerWorkingHoursRequestDto requestDto) {
        trainerWorkingHoursClient.updateWorkingHours(requestDto);
    }

    /**
     * Fallback method which is called when the updateWorkingHours method fails for some reason.
     *
     * @param requestDto TrainerWorkingHoursRequestDto
     * @param throwable Throwable - the exception that was thrown in the updateWorkingHours() method.
     */
    public void updateWorkingHoursFallback(TrainerWorkingHoursRequestDto requestDto, Throwable throwable) {
        log.error("Inside updateWorkingHoursFallback method, cause - {}, request - {}", throwable, requestDto);
    }
}
