package org.example.metrics.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomMetricsService {

    private final Counter requestsSentCounter;

    /**
     * Constructor.
     */
    public CustomMetricsService(MeterRegistry meterRegistry) {
        requestsSentCounter = Counter.builder("http_total_requests_sent")
            .description("Total number of HTTP requests sent to the API")
            .register(meterRegistry);
        Gauge.builder("jvm_memory_usage", this::getJvmMemoryUsage)
            .description("Memory usage in the JVM in bytes")
            .register(meterRegistry);

    }

    /**
     * A method for incrementing the total amount of requests sent to the API.
     */
    public void incrementTotalRequestsSent() {
        log.info("Incrementing the number of requests sent by 1");
        requestsSentCounter.increment();
        log.info("Total number of requests - {}", requestsSentCounter.count());
    }

    /**
     * A method for getting the total amount of requests sent to the API.
     */
    public double getTotalRequestsSent() {
        log.info("Getting the number of total requests sent, total number of requests - {}",
            requestsSentCounter.count());
        return requestsSentCounter.count();
    }

    /**
     * A method for getting information about memory usage.
     */
    public double getJvmMemoryUsage() {
        log.info("Getting the jvm memory usage");
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * A method for getting the maximum memory.
     */
    public double getJvmMaximumMemory() {
        return (double) Runtime.getRuntime().maxMemory();
    }

    /**
     * A method for getting the free memory.
     */
    public double getJvmFreeMemory() {
        return (double) Runtime.getRuntime().freeMemory();
    }
}
