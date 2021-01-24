package ru.silvmike.interview.resident.average.service.average.dto;

import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import lombok.Data;

/**
 * Represents {@link AverageProvider#getAverage(AverageRequest)}'s request dto.
 */
@Data
public class AverageRequest {

    private final String eventType;
    private final Long from;
    private final Long to;

}
