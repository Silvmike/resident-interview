package ru.silvmike.interview.resident.average.service.average;

import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;

/**
 * <pre>
 * Stub implementation of {@link AverageProvider}.
 * Uses predefined single answer as resulting {@link Average}.
 * </pre>
 */
public class StubAverageProvider implements AverageProvider {

    private final Average average;

    public StubAverageProvider(Average average) {
        this.average = average;
    }

    @Override
    public Average getAverage(AverageRequest averageRequest) {

        return new Average(
            averageRequest.getEventType(),
            average.getValue(),
            average.getProcessedCount()
        );
    }
}
