package ru.silvmike.interview.resident.average.rest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ErrorResponse {

    private List<Error> errors;

}
