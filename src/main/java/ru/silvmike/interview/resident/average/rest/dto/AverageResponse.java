package ru.silvmike.interview.resident.average.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AverageResponse {

    private final String type;
    private final BigDecimal value;
    private final long processedCount;

}
