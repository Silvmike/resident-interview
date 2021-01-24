package ru.silvmike.interview.resident.average.rest.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AverageRequestValidatorTest {

    public static final String EVENT_TYPE_IS_REQUIRED = "'eventType' is required!";
    public static final String FROM_IS_REQUIRED = "'from' is required!";
    public static final String TO_IS_REQUIRED = "'to' is required!";
    public static final String TO_IS_EXPECTED_TO_BE_GREATER_THAN_ZERO = "'to' is expected to be greater than zero!";

    public static final String FROM_IS_EXPECTED_TO_BE_GREATER_THAN_ZERO =
            "'from' is expected to be greater than zero!";

    public static final String FROM_IS_EXPECTED_TO_BE_LESS_THAN_OR_EQUAL_TO_TO =
            "'from' is expected to be less than or equal to 'to'!";

    private final AverageRequestValidator validator = new AverageRequestValidator();

    public static Stream<Arguments> testValidationData() {

        return Stream.of(
            data(null, 1L, 2L, EVENT_TYPE_IS_REQUIRED),
            data("", 1L, 2L, EVENT_TYPE_IS_REQUIRED),
            data("   ", 1L, 2L, EVENT_TYPE_IS_REQUIRED),
            data("earth", null, 2L, FROM_IS_REQUIRED),
            data("earth", -1L, 2L, FROM_IS_EXPECTED_TO_BE_GREATER_THAN_ZERO),
            data("earth", 1L, null, TO_IS_REQUIRED),
            data("earth", 1L, -2L, TO_IS_EXPECTED_TO_BE_GREATER_THAN_ZERO),
            data("earth", 2L, 1L, FROM_IS_EXPECTED_TO_BE_LESS_THAN_OR_EQUAL_TO_TO)
        );
    }

    private static Arguments data(String eventType, Long from, Long to, String expectedMessage) {
        return arguments(new AverageRequest(eventType, from, to), expectedMessage);
    }

    @MethodSource("testValidationData")
    @ParameterizedTest
    void testValidation(AverageRequest averageRequest, String expectedMessage) {

        assertThatCode(() -> validator.validate(averageRequest))
            .as("Should throw exception on null object")
            .isInstanceOf(ValidationException.class)
            .extracting(Throwable::getMessage)
            .as("Validation message was different")
            .isEqualTo(expectedMessage);
    }

    @Test
    void testValidationOK() {

        AverageRequest averageRequest = new AverageRequest("earth", 1L, 2L);
        assertThatCode(() -> validator.validate(averageRequest))
            .as("Validation wasn't supposed to throw on valid object!")
            .doesNotThrowAnyException();
    }

}
