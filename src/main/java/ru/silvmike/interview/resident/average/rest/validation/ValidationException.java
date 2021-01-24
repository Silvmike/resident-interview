package ru.silvmike.interview.resident.average.rest.validation;

/**
 * Validation exception.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
