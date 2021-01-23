package ru.silvmike.interview.resident.average.rest.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Error {

    private final String message;

}