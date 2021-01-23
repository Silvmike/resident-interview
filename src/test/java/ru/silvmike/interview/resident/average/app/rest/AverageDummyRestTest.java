package ru.silvmike.interview.resident.average.app.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.silvmike.interview.resident.average.app.AverageApplication;
import ru.silvmike.interview.resident.average.config.RootConfiguration;
import ru.silvmike.interview.resident.average.rest.AverageRest;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(
    classes = {
        AverageApplication.class,
        RootConfiguration.class,
        AverageDummyRestTest.TestConfiguration.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient
public class AverageDummyRestTest {

    private static final BigDecimal TEST_VALUE = BigDecimal.ONE.divide(BigDecimal.TEN);
    private static final long TEST_PROCESSED_COUNT = 100L;
    private static final String TEST_TYPE = "earth";
    private static final long TEST_FROM = 1L;
    private static final long TEST_TO = 2L;

    private static final String BASE_URL_TEMPLATE = "http://localhost:%d/%s/average?from=%d&to=%d";
    private static final long WEB_TEST_CLIENT_TIMEOUT_SECONDS = 5L;

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private AverageProvider averageProvider;

    @Test
    public void testResponseCorrespondsToDummyProvider() {

        request(TEST_TYPE, TEST_FROM, TEST_TO)
            .jsonPath("$.type").isEqualTo(TEST_TYPE)
            .jsonPath("$.value").isEqualTo(TEST_VALUE)
            .jsonPath("$.processedCount").isEqualTo(TEST_PROCESSED_COUNT);

        ArgumentCaptor<AverageRequest> averageRequestCaptor = ArgumentCaptor.forClass(AverageRequest.class);
        verify(averageProvider, times(1)).getAverage(averageRequestCaptor.capture());

        AverageRequest capturedRequest = averageRequestCaptor.getValue();
        assertEquals(TEST_TYPE, capturedRequest.getEventType());
        assertEquals(TEST_FROM, capturedRequest.getFrom());
        assertEquals(TEST_TO, capturedRequest.getTo());

    }

    private WebTestClient.BodyContentSpec request(String eventType, long from, long to) {

        WebTestClient client = createClient(String.format(BASE_URL_TEMPLATE, randomServerPort, eventType, from, to));
        return client.get()
            .exchange()
            .expectStatus().isOk()
            .expectBody();
    }

    private WebTestClient createClient(String baseUrl) {

        return WebTestClient.bindToServer()
            .responseTimeout(Duration.ofSeconds(WEB_TEST_CLIENT_TIMEOUT_SECONDS))
            .baseUrl(baseUrl)
            .build();
    }

    @Configuration
    public static class TestConfiguration {

        @Primary
        @Bean
        public AverageProvider dummyAverageProvider() {

            return Mockito.mock(
                AverageProvider.class,
                    AdditionalAnswers.delegatesTo((AverageProvider)
                        (averageRequest ->
                            new Average(
                                averageRequest.getEventType(),
                                TEST_VALUE,
                                TEST_PROCESSED_COUNT
                            )
                        )
                    )
            );
        }
    }

}