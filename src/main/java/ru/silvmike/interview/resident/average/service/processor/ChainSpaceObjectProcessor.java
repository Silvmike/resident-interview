package ru.silvmike.interview.resident.average.service.processor;

import lombok.RequiredArgsConstructor;
import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

import java.util.List;

/**
 * <pre>
 * {@link SpaceObjectInfoProcessor} chain.
 * Every {@link SpaceObjectInfo} will be processed by all processors in chain.
 * </pre>
 */
@RequiredArgsConstructor
public class ChainSpaceObjectProcessor implements SpaceObjectInfoProcessor {

    private final List<SpaceObjectInfoProcessor> chain;

    @Override
    public void accept(SpaceObjectInfo spaceObjectInfo) {

        chain.forEach(processor -> processor.accept(spaceObjectInfo));
    }
}
