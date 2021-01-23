package ru.silvmike.interview.resident.average.stream.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import ru.silvmike.interview.resident.average.service.processor.SpaceObjectInfoProcessor;
import ru.silvmike.interview.resident.average.service.processor.StringSpaceObjectConverter;
import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * Dispatches sample events from file.
 * Used to emulate Kinesis work.
 * </pre>
 */
@Slf4j
public class SampleFileEventDispatcher {

    private static final int MEGABYTE = 1024 * 1024;

    private final SpaceObjectInfoProcessor processor;
    private final StringSpaceObjectConverter converter;
    private final ScheduledExecutorService taskScheduler;
    private final SampleFileProperties config;

    private volatile BufferedReader reader;

    public SampleFileEventDispatcher(
            SpaceObjectInfoProcessor processor,
            StringSpaceObjectConverter converter,
            ScheduledExecutorService taskScheduler,
            SampleFileProperties config) {

        this.processor = Objects.requireNonNull(processor);
        this.converter = Objects.requireNonNull(converter);
        this.taskScheduler = Objects.requireNonNull(taskScheduler);
        this.config = Objects.requireNonNull(config);

        validateConfig(config);
    }

    private void validateConfig(SampleFileProperties config) {

        Objects.requireNonNull(config.getFile(), "File name should present!");
        if (StringUtils.isBlank(config.getFile())) {
            throw  new IllegalArgumentException("File name should not be empty!");
        }
        checkFileExists(config.getFile());

        if (config.getLimit() <= 0) {
            throw  new IllegalArgumentException("Limit should be greater than zero!");
        }
        if (config.getInterval() <= 0) {
            throw  new IllegalArgumentException("Interval should be greater than zero!");
        }
    }

    private void checkFileExists(String file) {
        try {
            ResourceUtils.getFile(file);
        } catch (FileNotFoundException e) {
            throw  new IllegalArgumentException("Specified file should exist!", e);
        }
    }

    private void readToTheLimit() {
        String line;
        int count = 0;
        boolean hasNext;
        try {
            BufferedReader reader = this.reader;
            while ((hasNext = ((line = reader.readLine()) != null))) {
                log.debug("Dispatching new sample: [{}]", line);

                SpaceObjectInfo data = converter.spaceObjectInfo(line);
                processor.accept(data);

                count++;
                if (count >= config.getLimit()) {

                    log.debug("Current limit [{}] was exceeded, scheduling next attempt...", config.getLimit());
                    scheduleNext();
                    break;
                }
            }
            if (!hasNext) {
                closeSilently();
            }
        } catch (IOException e) {

            log.error("Unable to read sample file data", e);
        }
    }

    private void scheduleNext() {

        taskScheduler.schedule(
            this::readToTheLimit,
            config.getInterval(),
            TimeUnit.MILLISECONDS
        );
    }

    @PostConstruct
    public void afterPropertiesSet() {

        try {
            this.reader = new BufferedReader(
                new FileReader(
                    ResourceUtils.getFile(config.getFile())
                ),
                MEGABYTE
            );
            scheduleNext();
        } catch (FileNotFoundException e) {
            log.error("File [{}] was not found", config.getFile(), e);
        }
    }

    @PreDestroy
    public void destroy() {
        closeSilently();
    }

    private void closeSilently() {

        BufferedReader reader = this.reader;
        if (reader != null) {
            try {

                log.info("The file [{}] has been processed!", config.getFile());
                reader.close();
                this.reader = null;
            } catch (IOException e) {
                // doesn't matter
            }
        }
    }
}
