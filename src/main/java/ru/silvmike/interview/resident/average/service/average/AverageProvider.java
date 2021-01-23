package ru.silvmike.interview.resident.average.service.average;

import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;

/**
 * Interface for {@link Average} providers.
 */
public interface AverageProvider {

    /**
     * Returns {@link Average} corresponding to given {@link AverageRequest}.
     *
     * @param averageRequest average request
     * @return {@link Average} corresponding to given {@link AverageRequest}
     */
    Average getAverage(AverageRequest averageRequest);

}
