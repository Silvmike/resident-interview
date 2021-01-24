package ru.silvmike.interview.resident.average.service.h2;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.silvmike.interview.resident.average.app.AverageApplication;
import ru.silvmike.interview.resident.average.app.Profiles;
import ru.silvmike.interview.resident.average.service.BaseAverageServiceTest;
import ru.silvmike.interview.resident.average.service.h2.config.H2Configuration;

import javax.sql.DataSource;

@SpringBootTest(
    classes = {
        AverageApplication.class,
        H2Configuration.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ActiveProfiles({ Profiles.H2 })
class H2AverageModeTest extends BaseAverageServiceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private H2SQLProvider sqlProvider;

    @BeforeEach
    public void setUp() {
        new H2CleanUpHelper(dataSource, sqlProvider).cleanUp();
    }

}