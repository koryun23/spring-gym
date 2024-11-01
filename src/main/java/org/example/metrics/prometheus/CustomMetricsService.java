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

    public CustomMetricsService() {
        requestsSentCounter = Counter.builder()
            .name("http_total_requests_sent")
            .help("Total number of HTTP requests sent to the API")
            .register();
        jvmMemoryUsageGauage = Gauge.builder().name("jvm_memory_usage")
            .help("Memory usage in the JVM in bytes")
            .register();
    }

    public void incrementTotalRequestsSent() {
        log.info("Incrementing the number of requests sent by 1");
        requestsSentCounter.inc(1L);
    }

    public double getTotalRequestsSent() {
        log.info("Getting the number of total requests sent");
        return requestsSentCounter.get();
    }

    public void updateJvmMemoryUsage() {
        log.info("Updating JVM Memory Usage");
        jvmMemoryUsageGauage.set(Runtime.getRuntime().totalMemory());
    }

    public double getJvmMemoryUsage() {
        log.info("Getting the jvm memory usage");
        updateJvmMemoryUsage();
        return jvmMemoryUsageGauage.get();
    }

    public double getJvmMaximumMemory() {
        return (double) Runtime.getRuntime().maxMemory();
    }

    public double getJvmFreeMemory() {
        return (double) Runtime.getRuntime().freeMemory();
    }
}
