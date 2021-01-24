package ru.silvmike.interview.resident.average.service.mongo.local;

import com.github.silaev.mongodb.replicaset.MongoDbReplicaSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Test {@link MongoDbReplicaSet} maintaining {@link FactoryBean}.
 */
@Slf4j
public class MongoDBReplicaSetFactoryBean
        implements FactoryBean<MongoDbReplicaSet>, InitializingBean, DisposableBean {

    private MongoDbReplicaSet instance;

    @Override
    public MongoDbReplicaSet getObject() {
        return instance;
    }

    @Override
    public Class<?> getObjectType() {
        return MongoDbReplicaSet.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        instance = MongoDbReplicaSet.builder()
            .mongoDockerImageName("mongo:4.4.0")
            .replicaSetNumber(1)
            .build();

        instance.start();

        log.info("Starting MongoDB: [{}]", instance.getReplicaSetUrl());
    }

    @Override
    public void destroy() {
        if (instance != null) {
            instance.stop();
        }
    }
}
