package ru.silvmike.interview.resident.average.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.silvmike.interview.resident.average.app.Profiles;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import ru.silvmike.interview.resident.average.service.average.StubAverageProvider;
import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.processor.StdOutSpaceObjectInfoProcessor;
import ru.silvmike.interview.resident.average.service.processor.StringSpaceObjectConverter;

import java.math.BigDecimal;

@Configuration
public class ServiceConfiguration {

    @Bean
    public StringSpaceObjectConverter stringSpaceObjectConverter() {
        return new StringSpaceObjectConverter();
    }

    @Profile(Profiles.STD_OUT_PROCESSOR)
    @Bean
    public StdOutSpaceObjectInfoProcessor stdOutSpaceObjectInfoProcessor() {
        return new StdOutSpaceObjectInfoProcessor();
    }

    @Profile(Profiles.STUB_AVERAGE_PROVIDER)
    @Bean
    public AverageProvider stubAverageProvider() {
        return new StubAverageProvider(
            new Average("earth", BigDecimal.ONE, 1)
        );
    }
}
