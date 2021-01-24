package ru.silvmike.interview.resident.average.service.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

/**
 * Logs error in case it happens during event processing.
 */
@Slf4j
@RequiredArgsConstructor
public class ErrorLoggingDelegatingSpaceObjectProcessor implements SpaceObjectInfoProcessor {

    private final SpaceObjectInfoProcessor delegate;

    @Override
    public void accept(SpaceObjectInfo spaceObjectInfo) {
        try {
            delegate.accept(spaceObjectInfo);
        } catch (Throwable e) {
            log.error("Unable to process event [{}]", spaceObjectInfo, e);
            throw e;
        }
    }
}
