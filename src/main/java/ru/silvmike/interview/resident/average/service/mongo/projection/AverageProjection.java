package ru.silvmike.interview.resident.average.service.mongo.projection;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import ru.silvmike.interview.resident.average.service.mongo.entity.AverageEntity;

import java.math.BigDecimal;

@Data
public class AverageProjection {

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal value;

    private long count;

    /**
     * {@link AverageEntity} column names.
     */
    public interface Columns {

        String VALUE = "value";
        String COUNT = "count";
    }
}
