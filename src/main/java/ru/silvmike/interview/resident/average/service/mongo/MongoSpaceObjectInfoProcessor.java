package ru.silvmike.interview.resident.average.service.mongo;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.silvmike.interview.resident.average.service.mongo.entity.AverageEntity;
import ru.silvmike.interview.resident.average.service.processor.SpaceObjectInfoProcessor;
import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

/**
 * Mongo-based {@link SpaceObjectInfoProcessor}.
 */
@Slf4j
@RequiredArgsConstructor
public class MongoSpaceObjectInfoProcessor implements SpaceObjectInfoProcessor {

    private final MongoTemplate mongoTemplate;

    @Override
    public void accept(SpaceObjectInfo spaceObjectInfo) {

        final AverageEntity.Identifier id = new AverageEntity.Identifier(
            spaceObjectInfo.getTimestamp(),
            spaceObjectInfo.getType()
        );

        Query query = new Query();
        query.addCriteria(Criteria.where(AverageEntity.Columns.ID).is(id));

        Update update = new Update();
        update.set(AverageEntity.Columns.ID, id);
        update.inc(AverageEntity.Columns.VALUE, spaceObjectInfo.getValue());
        update.inc(AverageEntity.Columns.COUNT, 1);

        UpdateResult updateResult = mongoTemplate.upsert(query, update, AverageEntity.class);
        log.debug(
            "Upsert for {}: matched [{}], modified [{}].",
            AverageEntity.class.getSimpleName(),
            updateResult.getMatchedCount(),
            updateResult.getModifiedCount()
        );
    }
}
