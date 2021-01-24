package ru.silvmike.interview.resident.average.service.h2.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.silvmike.interview.resident.average.app.Profiles;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import ru.silvmike.interview.resident.average.service.h2.H2AverageProvider;
import ru.silvmike.interview.resident.average.service.h2.H2SQLProvider;
import ru.silvmike.interview.resident.average.service.h2.H2SpaceObjectInfoProcessor;
import ru.silvmike.interview.resident.average.service.processor.SpaceObjectInfoProcessor;

import javax.sql.DataSource;

@Profile(Profiles.H2)
@PropertySource("classpath:queries.xml")
@EnableTransactionManagement
@Import(DataSourceConfiguration.class)
@Configuration
public class H2Configuration {

    @Bean
    public AverageProvider averageProvider(DataSource dataSource, H2SQLProvider sqlProvider) {
        return new H2AverageProvider(dataSource, sqlProvider);
    }

    @Bean
    public SpaceObjectInfoProcessor spaceObjectInfoProcessor(DataSource dataSource, H2SQLProvider sqlProvider) {
        return new H2SpaceObjectInfoProcessor(dataSource, sqlProvider);
    }

    @Bean
    public H2SQLProvider sqlProvider() {
        return new H2SQLProvider();
    }

    @Bean
    public PlatformTransactionManager datasourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
