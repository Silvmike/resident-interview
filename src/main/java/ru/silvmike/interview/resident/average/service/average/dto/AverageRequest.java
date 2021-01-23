package ru.silvmike.interview.resident.average.service.average.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class AverageRequest {

    final String eventType;
    final long from;
    final long to;

}
