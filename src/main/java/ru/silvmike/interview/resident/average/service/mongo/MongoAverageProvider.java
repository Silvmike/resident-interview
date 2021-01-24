package ru.silvmike.interview.resident.average.service.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;
import ru.silvmike.interview.resident.average.service.mongo.entity.AverageEntity;
import ru.silvmike.interview.resident.average.service.mongo.entity.projection.AverageProjection;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static ru.silvmike.interview.resident.average.service.mongo.entity.AverageEntity.Columns.*;

/**
 * MongoDB-based {@link AverageProvider}.
 */
@RequiredArgsConstructor
public class MongoAverageProvider implements AverageProvider {

    private final MongoTemplate mongoTemplate;

    @Override
    public Average getAverage(AverageRequest averageRequest) {

        MatchOperation match = match(
            new Criteria()
                .and(TIMESTAMP).gte(averageRequest.getFrom()).lte(averageRequest.getTo())
                .and(EVENT_TYPE).is(averageRequest.getEventType())
        );

        GroupOperation average = group(EVENT_TYPE)
                .sum(VALUE).as(VALUE)
                .sum(COUNT).as(COUNT);

        List<AverageProjection> averageList = mongoTemplate.aggregate(
            newAggregation(match, average),
            AverageEntity.class,
            AverageProjection.class).getMappedResults();

        if (!averageList.isEmpty()) return convertToAverage(averageList.get(0), averageRequest);
        return new Average(averageRequest.getEventType(), BigDecimal.ZERO, 0);
    }

    private Average convertToAverage(AverageProjection averageEntity, AverageRequest averageRequest) {
        return AverageToAverageEntityConverter.INSTANCE.convert(averageEntity, averageRequest.getEventType());
    }
}
