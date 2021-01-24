package ru.silvmike.interview.resident.average.service.mongo.config.local;

import com.github.silaev.mongodb.replicaset.MongoDbReplicaSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ru.silvmike.interview.resident.average.app.Profiles;
import ru.silvmike.interview.resident.average.service.mongo.MongoSettingsProvider;

@Profile(Profiles.LOCAL_MONGO)
@Configuration
public class MongoDBReplicaSetConfiguration {

    @Bean
    public MongoDBReplicaSetFactoryBean replicaSetFactoryBean() {
        return new MongoDBReplicaSetFactoryBean();
    }

    @Primary
    @Bean
    public MongoSettingsProvider primaryMongoSettingsProvider(MongoDbReplicaSet replicaSet) {

        return new MongoSettingsProvider()
            .setUri(replicaSet.getReplicaSetUrl())
            .setDatabase("average");
    }
}
