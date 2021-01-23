package ru.silvmike.interview.resident.average.stream.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("sample.dispatcher")
public class SampleFileProperties {

    /**
     * Input file.
     */
    private String file;

    /**
     * Scheduling interval.
     */
    private long interval;

    /**
     * Max records read per attempt.
     */
    private int limit;

}
