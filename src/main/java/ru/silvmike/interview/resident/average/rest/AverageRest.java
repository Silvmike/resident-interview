package ru.silvmike.interview.resident.average.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.silvmike.interview.resident.average.rest.dto.AverageResponse;
import ru.silvmike.interview.resident.average.rest.validation.AverageRequestValidator;
import ru.silvmike.interview.resident.average.rest.validation.advice.ValidationExceptionHandling;
import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;

@RequiredArgsConstructor
@ValidationExceptionHandling
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AverageRest {

    private final AverageProvider averageProvider;
    private final AverageRequestValidator averageRequestValidator;

    @GetMapping({"/{eventType}/average","/average"})
    public AverageResponse getAverage(
            @PathVariable(name = "eventType", required = false) String eventType,
            @RequestParam(name = "from", required = false) Long from,
            @RequestParam(name = "to", required = false) Long to) {

        AverageRequest averageRequest = new AverageRequest(eventType, from, to);
        averageRequestValidator.validate(averageRequest);

        Average average = averageProvider.getAverage(averageRequest);
        return convertToResponse(average);
    }

    private AverageResponse convertToResponse(Average average) {

        return new AverageResponse(average.getType(), average.getValue(), average.getProcessedCount());
    }
}
