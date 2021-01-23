package ru.silvmike.interview.resident.average.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    ServiceConfiguration.class,
    RestConfiguration.class
})
@Configuration
public class RootConfiguration {
}
