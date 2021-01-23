package ru.silvmike.interview.resident.average.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.silvmike.interview.resident.average.rest.dto.AverageResponse;
import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AverageRest {

    private final AverageProvider averageProvider;

    @GetMapping("/{eventType}/average")
    public AverageResponse getAverage(
            @PathVariable("eventType") String eventType,
            @RequestParam("from") Long from,
            @RequestParam("to") Long to) {

        Average average = averageProvider.getAverage(new AverageRequest(eventType, from, to));
        return convertToResponse(average);
    }

    private AverageResponse convertToResponse(Average average) {

        return new AverageResponse(average.getType(), average.getValue(), average.getProcessedCount());
    }
}
