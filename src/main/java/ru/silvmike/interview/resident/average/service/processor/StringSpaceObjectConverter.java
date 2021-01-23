package ru.silvmike.interview.resident.average.service.processor;

import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Converts {@link String} to {@link SpaceObjectInfo}.
 */
public class StringSpaceObjectConverter {

    /**
     * Converts {@link String} to {@link SpaceObjectInfo}.
     *
     * @param input input string
     * @return instance of {@link SpaceObjectInfo}
     */
    public SpaceObjectInfo spaceObjectInfo(String input) {

        Objects.requireNonNull(input, "Input string can not be null!");

        String[] csv = input.split(",");
        if (csv.length != 2) {
            throw new IllegalStateException("Wrong data format!");
        }

        long timestamp = Long.parseLong(csv[0]);
        String name = csv[1];
        BigDecimal value = new BigDecimal(csv[2]);

        return new SpaceObjectInfo()
            .setTimestamp(timestamp)
            .setType(name)
            .setValue(value);

    }
}
