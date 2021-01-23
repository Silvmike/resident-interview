package ru.silvmike.interview.resident.average.stream.file;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import ru.silvmike.interview.resident.average.app.Profiles;
import ru.silvmike.interview.resident.average.service.processor.SpaceObjectInfoProcessor;
import ru.silvmike.interview.resident.average.service.processor.StringSpaceObjectConverter;

import java.util.concurrent.ScheduledExecutorService;

@Profile(Profiles.SAMPLE_FILE)
@EnableConfigurationProperties(SampleFileProperties.class)
@Configuration
public class SampleFileConfiguration {

    @Bean
    public SampleFileEventDispatcher sampleFileEventDispatcher(
            SpaceObjectInfoProcessor processor,
            StringSpaceObjectConverter converter,
            ScheduledExecutorService taskScheduler,
            SampleFileProperties config) {

        return new SampleFileEventDispatcher(processor, converter, taskScheduler, config);
    }

    @Bean
    public ScheduledExecutorFactoryBean sampleFileTaskScheduler() {
        ScheduledExecutorFactoryBean scheduler = new ScheduledExecutorFactoryBean();
        scheduler.setPoolSize(1);
        return scheduler;
    }

}
