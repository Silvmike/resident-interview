package ru.silvmike.interview.resident.average.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.silvmike.interview.resident.average.service.h2.H2Configuration;
import ru.silvmike.interview.resident.average.stream.file.SampleFileConfiguration;

@Import({
    ServiceConfiguration.class,
    RestConfiguration.class,
    SampleFileConfiguration.class,
    H2Configuration.class
})
@Configuration
public class RootConfiguration {
}
