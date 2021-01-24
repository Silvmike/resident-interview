package ru.silvmike.interview.resident.average.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.silvmike.interview.resident.average.service.h2.config.H2Configuration;
import ru.silvmike.interview.resident.average.service.mongo.config.MongoDbConfiguration;
import ru.silvmike.interview.resident.average.stream.file.SampleFileConfiguration;

@Import({
    ServiceConfiguration.class,
    RestConfiguration.class,
    SampleFileConfiguration.class,
    H2Configuration.class,
    MongoDbConfiguration.class
})
@Configuration
@EnableConfigurationProperties
public class RootConfiguration {
}
