package ru.silvmike.interview.resident.average.service.processor;

import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

/**
 * {@link SpaceObjectInfoProcessor} which simply prints input event to standard output.
 */
public class StdOutSpaceObjectInfoProcessor implements SpaceObjectInfoProcessor {

    @Override
    public void accept(SpaceObjectInfo spaceObjectInfo) {

        System.out.println(spaceObjectInfo);
    }
}
