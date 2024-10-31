package org.example.actuator.prometheus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("custom/metrics")
public class CustomMetricsController {

    private final CustomMetricsService customMetricsService;
    private final ObjectMapper objectMapper;

    public CustomMetricsController(CustomMetricsService customMetricsService, ObjectMapper objectMapper) {
        this.customMetricsService = customMetricsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/requests")
    public ResponseEntity<RestResponse> totalRequestsSent() {
        log.info("Retrieving the number of total requests");
        Map<String, Double> totalRequests = new HashMap<>();
        totalRequests.put("Number of Total Requests", customMetricsService.getTotalRequestsSent());
        log.info("{}", totalRequests);

        return new ResponseEntity<>(
            new RestResponse(totalRequests, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList()),
            HttpStatus.OK);
    }

    @GetMapping("/memory")
    public ResponseEntity<RestResponse> memoryUsage() {
        log.info("Retrieving the memory usage");
        Map<String, Double> memoryUsage = new HashMap<>();
        memoryUsage.put("Memory usage", customMetricsService.getJvmMemoryUsage());
        memoryUsage.put("Maximum memory", customMetricsService.getJvmMaximumMemory());
        memoryUsage.put("Free memory", customMetricsService.getJvmFreeMemory());

        log.info("{}", memoryUsage);
        return new ResponseEntity<>(
            new RestResponse(memoryUsage, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList()),
            HttpStatus.OK
        );
    }
}
