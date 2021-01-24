package ru.silvmike.interview.resident.average.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import ru.silvmike.interview.resident.average.rest.AverageRest;
import ru.silvmike.interview.resident.average.rest.validation.AverageRequestValidator;
import ru.silvmike.interview.resident.average.rest.validation.advice.ValidationControllerAdvice;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;

@Configuration
public class RestConfiguration {

    private static final int TEN_KB = 10 * 1024;

    @Bean
    public AverageRest averageRest(AverageProvider averageProvider, AverageRequestValidator validator) {

        return new AverageRest(averageProvider, validator);
    }

    @Bean
    public AverageRequestValidator averageRequestValidator() {
        return new AverageRequestValidator();
    }

    @Bean
    public ValidationControllerAdvice validationControllerAdvice() {
        return new ValidationControllerAdvice();
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(TEN_KB);
        return loggingFilter;
    }
}
