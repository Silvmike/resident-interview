package ru.silvmike.interview.resident.average.service.processor.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class SpaceObjectInfo {

    long timestamp;
    String type;
    BigDecimal value;

}
