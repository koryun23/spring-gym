package org.example.metrics.prometheus;


import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.core.metrics.Gauge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomMetricsService {

    private final Counter requestsSentCounter;
    private final Gauge jvmMemoryUsageGauage;

    /**
     * Constructor.
     */
    public CustomMetricsService() {
        requestsSentCounter = Counter.builder()
            .name("http_total_requests_sent")
            .help("Total number of HTTP requests sent to the API")
            .register();
        jvmMemoryUsageGauage = Gauge.builder().name("jvm_memory_usage")
            .help("Memory usage in the JVM in bytes")
            .register();
    }

    /**
     * A method for incrementing the total amount of requests sent to the API.
     */
    public void incrementTotalRequestsSent() {
        log.info("Incrementing the number of requests sent by 1");
        requestsSentCounter.inc(1L);
    }

    /**
     * A method for getting the total amount of requests sent to the API.
     */
    public double getTotalRequestsSent() {
        log.info("Getting the number of total requests sent");
        return requestsSentCounter.get();
    }

    /**
     * A method for updating the memory usage information in the gauge.
     */
    public void updateJvmMemoryUsage() {
        log.info("Updating JVM Memory Usage");
        jvmMemoryUsageGauage.set(Runtime.getRuntime().totalMemory());
    }

    /**
     * A method for getting information about memory usage.
     */
    public double getJvmMemoryUsage() {
        log.info("Getting the jvm memory usage");
        updateJvmMemoryUsage();
        return jvmMemoryUsageGauage.get();
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
