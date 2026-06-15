package com.pancaran.master.config;

import org.flywaydb.core.Flyway;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
public class DatabaseHelper {

    public static Flyway createFlyway(DataSource dataSource, String locations) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(locations)
                .baselineOnMigrate(true)
                .load();
    }

    public static void testConnection(DataSource ds, String dbName) {
        try (Connection conn = ds.getConnection()) {
            log.info("Database '{}' is connected successfully", dbName);
        } catch (Exception e) {
            log.warn("Database '{}' is unreachable: {}. Application will continue to start.", dbName, e.getMessage());
        }
    }
}
