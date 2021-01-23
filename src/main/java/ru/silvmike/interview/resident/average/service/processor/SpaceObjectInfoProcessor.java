package ru.silvmike.interview.resident.average.service.processor;

import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

import java.util.function.Consumer;

/**
 * Base interface for {@link SpaceObjectInfo} processor.
 */
public interface SpaceObjectInfoProcessor extends Consumer<SpaceObjectInfo> {

}
