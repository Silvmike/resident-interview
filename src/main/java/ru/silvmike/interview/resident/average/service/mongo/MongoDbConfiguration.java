package ru.silvmike.interview.resident.average.service.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.silvmike.interview.resident.average.app.Profiles;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import ru.silvmike.interview.resident.average.service.mongo.local.MongoDBReplicaSetConfiguration;
import ru.silvmike.interview.resident.average.service.processor.SpaceObjectInfoProcessor;

@Profile(Profiles.MONGO)
@Import({
    MongoClientConfiguration.class,
    MongoDBReplicaSetConfiguration.class
})
@Configuration
public class MongoDbConfiguration {

    @Bean
    public AverageProvider mongoDbAverageProvider(MongoTemplate mongoTemplate) {
        return new MongoAverageProvider(mongoTemplate);
    }

    @Bean
    public SpaceObjectInfoProcessor spaceObjectInfoProcessor(MongoTemplate mongoTemplate) {
        return new MongoSpaceObjectInfoProcessor(mongoTemplate);
    }

    @Bean
    @ConfigurationProperties("spring.average.mongo")
    public MongoSettingsProvider mongoSettingsProvider() {
        return new MongoSettingsProvider();
    }
}
