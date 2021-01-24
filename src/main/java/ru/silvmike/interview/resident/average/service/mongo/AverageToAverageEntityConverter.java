package ru.silvmike.interview.resident.average.service.mongo;

import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.mongo.entity.AverageEntity;
import ru.silvmike.interview.resident.average.service.mongo.entity.projection.AverageProjection;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Converts {@link AverageEntity} to {@link Average}.
 */
class AverageToAverageEntityConverter {

    static final AverageToAverageEntityConverter INSTANCE = new AverageToAverageEntityConverter();

    public Average convert(AverageProjection averageEntity, String eventType) {

        BigDecimal value = averageEntity.getValue();
        long count = averageEntity.getCount();

        return new Average(
                eventType,
            calculateAverage(value, count),
            count
        );
    }

    private BigDecimal calculateAverage(BigDecimal value, long count) {

        return value.divide(BigDecimal.valueOf(count), 4, RoundingMode.UP);
    }
}
