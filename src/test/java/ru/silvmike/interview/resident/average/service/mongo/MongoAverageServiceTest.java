package ru.silvmike.interview.resident.average.service.mongo;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.silvmike.interview.resident.average.app.AverageApplication;
import ru.silvmike.interview.resident.average.app.Profiles;
import ru.silvmike.interview.resident.average.service.BaseAverageServiceTest;
import ru.silvmike.interview.resident.average.service.mongo.config.MongoDbConfiguration;

@SpringBootTest(
    classes = {
        AverageApplication.class,
        MongoDbConfiguration.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ActiveProfiles({ Profiles.MONGO, Profiles.LOCAL_MONGO })
class MongoAverageServiceTest extends BaseAverageServiceTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        new MongoCleanUpHelper(mongoTemplate).cleanUp();
    }

}