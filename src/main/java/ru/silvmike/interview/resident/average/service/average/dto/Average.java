package ru.silvmike.interview.resident.average.service.average.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Average {

    private final String type;
    private final BigDecimal value;
    private final long processedCount;

}
