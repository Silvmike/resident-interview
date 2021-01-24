package ru.silvmike.interview.resident.average.rest.validation.advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for MVC-controller.
 * Adds additional error handler for validation exceptions.
 *
 * @see ValidationControllerAdvice
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationExceptionHandling {
}
