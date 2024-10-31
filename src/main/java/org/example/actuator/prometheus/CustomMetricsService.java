package org.example.actuator.prometheus;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomMetricsService {

    private final Counter requestsSentCounter;

    public CustomMetricsService(MeterRegistry meterRegistry) {
        requestsSentCounter = Counter.builder("http_total_requests_sent")
            .description("Total number of HTTP requests sent to the API")
            .register(meterRegistry);
    }

    public void incrementTotalRequestsSent() {
        log.info("Incrementing the number of requests sent by 1");
        requestsSentCounter.increment();
    }

    public double getTotalRequestsSent() {
        log.info("Getting the number of total requests sent");
        return requestsSentCounter.count();
    }
}
