package ru.silvmike.interview.resident.average.rest.validation.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.silvmike.interview.resident.average.rest.dto.Error;
import ru.silvmike.interview.resident.average.rest.dto.ErrorResponse;
import ru.silvmike.interview.resident.average.rest.validation.ValidationException;

import static java.util.Collections.singletonList;

/**
 * Controller advice defining exception handler for all the instances of {@link ValidationException}.
 * <p>
 * Applicable only for controllers annotated by {@link ValidationExceptionHandling}.
 *
 * @see ValidationExceptionHandling
 */
@ControllerAdvice(annotations = ValidationExceptionHandling.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class ValidationControllerAdvice {

    /**
     * Handle all the {@link ValidationException} instances and build response objects based on it.
     *
     * @param exception exception for handling
     * @return response object for handled exception
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleError(ValidationException exception) {
        
        return ResponseEntity.badRequest()
            .body(
               new ErrorResponse()
                    .setErrors(
                        singletonList(
                            new Error(exception.getMessage())
                        )
                    )
            );
    }
}