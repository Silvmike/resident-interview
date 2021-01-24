package ru.silvmike.interview.resident.average.service.mongo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Provides connection string and database name.
 */
@Data
@Accessors(chain = true)
public class MongoSettingsProvider {

    private String database;
    private String uri;

}
