package org.example.actuator;

import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemoryHealthIndicator implements HealthIndicator {

    private static final long FREE_MEMORY_THRESHOLD = 10485760L;

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        try {
            doHealthCheck(builder);
        } catch (Exception e) {
            builder.down(e);
        }
        return builder.build();
    }

    protected void doHealthCheck(Health.Builder builder) throws SQLException {
        log.info("Performing health check - checking if free memory is beyond the threshold");
        builder = builder.withDetail("Used memory", Runtime.getRuntime().totalMemory())
            .withDetail("Max memory", Runtime.getRuntime().maxMemory())
            .withDetail("Free memory", Runtime.getRuntime().freeMemory());
        if (Runtime.getRuntime().freeMemory() < FREE_MEMORY_THRESHOLD) {
            log.warn("Status of health check - down");
            log.warn("Free memory - {}", Runtime.getRuntime().freeMemory());
            builder = builder.down();
        } else {
            log.info("Status of health check - up");
            log.info("Free memory - {}", Runtime.getRuntime().freeMemory());
            builder = builder.up();
        }
    }
}
