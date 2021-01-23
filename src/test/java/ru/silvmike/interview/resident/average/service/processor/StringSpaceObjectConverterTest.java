package ru.silvmike.interview.resident.average.service.processor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringSpaceObjectConverterTest {

    private final StringSpaceObjectConverter converter = new StringSpaceObjectConverter();

    static Stream<Arguments> validationTestData() {
        return Stream.of(
            arguments("Null input", null, NullPointerException.class),
            arguments("Empty input", "", IllegalArgumentException.class),
            arguments("Less than three values input", "123,earth", IllegalArgumentException.class),
            arguments("Malformed timestamp", "timestamp,earth,3.65", NumberFormatException.class),
            arguments("Malformed value", "123,earth,ABC", NumberFormatException.class)
        );
    }

    @MethodSource("validationTestData")
    @ParameterizedTest(name = "[{index}] {0}")
    void validationTest(String testDescription, String input, Class<? extends Throwable> expectedException) {

        assertThatCode(() -> converter.spaceObjectInfo(input))
            .isInstanceOf(expectedException);
    }

    @Test
    void okTest() {

        assertThatCode(() -> converter.spaceObjectInfo("1567365890,earth,0.018"))
            .doesNotThrowAnyException();
    }

}
