package ru.silvmike.interview.resident.average.rest.validation;

import org.apache.commons.lang3.StringUtils;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;

/**
 * Validates {@link AverageRequest}.
 */
public class AverageRequestValidator {

    /**
     * Validates {@link AverageRequest}.
     *
     * @param averageRequest {@link AverageRequest} ot validate
     * @throws ValidationException if given {@link AverageRequest} is invalid
     */
    public void validate(AverageRequest averageRequest) throws ValidationException {

        validateEventType(averageRequest.getEventType());
        validateTimestamp(averageRequest.getFrom(), "from");
        validateTimestamp(averageRequest.getTo(), "to");
        validateInterval(averageRequest.getFrom(), averageRequest.getTo());
    }

    private void validateInterval(long from, long to) {
        if (from > to) {
            throw new ValidationException("'from' is expected to be less than or equal to 'to'!");
        }
    }

    private void validateTimestamp(Long timestamp, String field) {
        if (timestamp == null) throw new ValidationException("'" +field + "' is required!");
        if (timestamp < 0) {
            throw new ValidationException("'" + field + "' is expected to be greater than zero!");
        }
    }

    private void validateEventType(String eventType) {
        if (StringUtils.isBlank(eventType)) {
            throw new ValidationException("'eventType' is required!");
        }
    }

}
