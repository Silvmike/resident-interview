package ru.silvmike.interview.resident.average.service.h2;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    public static final String AVERAGE_DATA_SOURCE = "averageDataSource";

    @FlywayDataSource
    @Bean(AVERAGE_DATA_SOURCE)
    public DataSource walletDataSource(HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.average.hikari")
    public HikariConfig averageHikariConfig() {
        return new HikariConfig();
    }
}
