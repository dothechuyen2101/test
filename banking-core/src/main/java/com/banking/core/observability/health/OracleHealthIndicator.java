package com.banking.core.observability.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Oracle Database health indicator.
 * Performs a lightweight check using "SELECT 1 FROM DUAL".
 */
@Component("oracleHealth")
public class OracleHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public OracleHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                return Health.up()
                        .withDetail("database", "Oracle")
                        .withDetail("status", "Connected")
                        .build();
            } else {
                return Health.down()
                        .withDetail("database", "Oracle")
                        .withDetail("status", "Connection invalid")
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("database", "Oracle")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
