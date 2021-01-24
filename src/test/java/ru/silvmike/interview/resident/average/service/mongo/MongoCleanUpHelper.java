package ru.silvmike.interview.resident.average.service.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.silvmike.interview.resident.average.service.mongo.entity.AverageEntity;

@Data
class MongoCleanUpHelper {

    private final MongoTemplate mongoTemplate;

    public void cleanUp() {

        this.mongoTemplate.dropCollection(AverageEntity.class);
    }

}
