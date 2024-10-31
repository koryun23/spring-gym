package org.example.actuator;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class RandomHealthIndicator implements HealthIndicator {

    private DataSource dataSource;

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
        if (dataSource.getConnection() != null) {
            builder.down().withDetail("Database Connection", true);
        } else {
            builder.down().withDetail("Database Connection", false);
        }
    }

}
