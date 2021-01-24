package ru.silvmike.interview.resident.average.service.mongo;

import com.google.common.collect.ImmutableSet;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import ru.silvmike.interview.resident.average.service.mongo.entity.AverageEntity;

import java.util.Set;

@Configuration
public class MongoClientConfiguration extends AbstractMongoClientConfiguration {

    @Value("${spring.average.mongo.auto-create-indexes:false}")
    private boolean autocreateIndexes;

    @Autowired
    private MongoSettingsProvider mongoSettingsProvider;

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {

        builder.applyConnectionString(new ConnectionString(this.mongoSettingsProvider.getUri()));
    }

    @Override
    protected String getDatabaseName() {
        return this.mongoSettingsProvider.getDatabase();
    }

    @Override
    protected Set<Class<?>> getInitialEntitySet() {
        return ImmutableSet.of(AverageEntity.class);
    }

    @Override
    protected boolean autoIndexCreation() {
        return this.autocreateIndexes;
    }
}
