package ru.silvmike.interview.resident.average.service.mongo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.silvmike.interview.resident.average.service.mongo.entity.projection.AverageProjection;

import java.io.Serializable;

@CompoundIndexes({
    @CompoundIndex(
        name = "timestamp_type_idx",
        def = "{ '_id.timestamp': 1, '_id.eventType': 1 }",
        unique = true
    )
})
@Document(collection = "average")
@Data
@EqualsAndHashCode(callSuper = true)
public class AverageEntity extends AverageProjection {

    @Id
    private Identifier id;

    @Value
    public static class Identifier implements Serializable {

        long timestamp;
        String eventType;

    }

    /**
     * {@link AverageEntity} column names.
     */
    public interface Columns extends AverageProjection.Columns {

        String ID = "id";
        String EVENT_TYPE = "id.eventType";
        String TIMESTAMP = "id.timestamp";
    }
}
