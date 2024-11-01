package org.example.actuator;

import java.sql.SQLException;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

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
        builder = builder.withDetail("Used memory", Runtime.getRuntime().totalMemory())
            .withDetail("Max memory", Runtime.getRuntime().maxMemory())
            .withDetail("Free memory", Runtime.getRuntime().freeMemory());
        if (Runtime.getRuntime().freeMemory() < FREE_MEMORY_THRESHOLD) {
            builder = builder.down();
        } else {
            builder = builder.up();
        }
    }

}
