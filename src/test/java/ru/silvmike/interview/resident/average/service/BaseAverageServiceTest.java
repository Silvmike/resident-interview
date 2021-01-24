package ru.silvmike.interview.resident.average.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;
import ru.silvmike.interview.resident.average.service.processor.SpaceObjectInfoProcessor;
import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseAverageServiceTest {

    @Autowired
    private AverageProvider averageProvider;

    @Autowired
    private SpaceObjectInfoProcessor spaceObjectInfoProcessor;

    @BeforeEach
    public abstract void setUp();

    @Test
    public void saveSingleAverageTest() {

        SpaceObjectInfo testObject =
            new SpaceObjectInfo()
                .setTimestamp(1L)
                .setType("earth")
                .setValue(BigDecimal.valueOf(0.36));

        AverageRequest averageRequest =
                new AverageRequest(testObject.getType(), testObject.getTimestamp(), testObject.getTimestamp());

        Average averageBeforeSave = averageProvider.getAverage(averageRequest);

        assertThat(averageBeforeSave.getType()).isEqualTo(testObject.getType());
        assertThat(averageBeforeSave.getProcessedCount()).isEqualTo(0L);
        assertThat(averageBeforeSave.getValue()).isEqualTo(BigDecimal.ZERO);

        spaceObjectInfoProcessor.accept(testObject);

        Average averageAfterSave = averageProvider.getAverage(averageRequest);
        assertThat(averageAfterSave.getType()).isEqualTo(testObject.getType());
        assertThat(averageAfterSave.getProcessedCount()).isEqualTo(1L);
        assertThat(averageAfterSave.getValue()).matches(value -> value.compareTo(testObject.getValue()) == 0);
    }

    @Test
    public void saveTwoEventsOfSameTypeAndTimestampTest() {

        SpaceObjectInfo first =
            new SpaceObjectInfo()
                .setTimestamp(1L)
                .setType("earth")
                .setValue(BigDecimal.valueOf(0.36));

        SpaceObjectInfo second =
            new SpaceObjectInfo()
                .setTimestamp(first.getTimestamp())
                .setType(first.getType())
                .setValue(BigDecimal.valueOf(0.64));

        AverageRequest averageRequest =
                new AverageRequest(first.getType(), first.getTimestamp(), first.getTimestamp());

        Average averageBeforeSave = averageProvider.getAverage(averageRequest);

        assertThat(averageBeforeSave.getType()).isEqualTo(first.getType());
        assertThat(averageBeforeSave.getProcessedCount()).isEqualTo(0L);
        assertThat(averageBeforeSave.getValue()).isEqualTo(BigDecimal.ZERO);

        spaceObjectInfoProcessor.accept(first);
        spaceObjectInfoProcessor.accept(second);

        Average averageAfterSave = averageProvider.getAverage(averageRequest);
        assertThat(averageAfterSave.getType()).isEqualTo(first.getType());
        assertThat(averageAfterSave.getProcessedCount()).isEqualTo(2L);
        assertThat(averageAfterSave.getValue()).matches(value -> value.compareTo(BigDecimal.valueOf(0.5)) == 0);
    }

    @Test
    public void saveTwoEventsOfDifferentTimestampsTest() {

        SpaceObjectInfo first =
            new SpaceObjectInfo()
                .setTimestamp(1L)
                .setType("earth")
                .setValue(BigDecimal.valueOf(0.36));

        SpaceObjectInfo second =
            new SpaceObjectInfo()
                .setTimestamp(first.getTimestamp() + 1L)
                .setType(first.getType())
                .setValue(BigDecimal.valueOf(0.64));

        AverageRequest averageRequest =
                new AverageRequest(first.getType(), first.getTimestamp(), first.getTimestamp());

        Average averageBeforeSave = averageProvider.getAverage(averageRequest);

        assertThat(averageBeforeSave.getType()).isEqualTo(first.getType());
        assertThat(averageBeforeSave.getProcessedCount()).isEqualTo(0L);
        assertThat(averageBeforeSave.getValue()).isEqualTo(BigDecimal.ZERO);

        spaceObjectInfoProcessor.accept(first);
        spaceObjectInfoProcessor.accept(second);

        Average averageAfterSave = averageProvider.getAverage(averageRequest);
        assertThat(averageAfterSave.getType()).isEqualTo(first.getType());
        assertThat(averageAfterSave.getProcessedCount()).isEqualTo(1L);
        assertThat(averageAfterSave.getValue()).matches(value -> value.compareTo(first.getValue()) == 0);
    }

    @Test
    public void saveTwoEventsOfDifferentEventTypesTest() {

        SpaceObjectInfo first =
                new SpaceObjectInfo()
                        .setTimestamp(1L)
                        .setType("earth")
                        .setValue(BigDecimal.valueOf(0.36));

        SpaceObjectInfo second =
                new SpaceObjectInfo()
                        .setTimestamp(first.getTimestamp())
                        .setType("pluto")
                        .setValue(BigDecimal.valueOf(0.64));

        AverageRequest averageRequest =
                new AverageRequest(first.getType(), first.getTimestamp(), first.getTimestamp());

        Average averageBeforeSave = averageProvider.getAverage(averageRequest);

        assertThat(averageBeforeSave.getType()).isEqualTo(first.getType());
        assertThat(averageBeforeSave.getProcessedCount()).isEqualTo(0L);
        assertThat(averageBeforeSave.getValue()).isEqualTo(BigDecimal.ZERO);

        spaceObjectInfoProcessor.accept(first);
        spaceObjectInfoProcessor.accept(second);

        Average averageAfterSave = averageProvider.getAverage(averageRequest);
        assertThat(averageAfterSave.getType()).isEqualTo(first.getType());
        assertThat(averageAfterSave.getProcessedCount()).isEqualTo(1L);
        assertThat(averageAfterSave.getValue()).matches(value -> value.compareTo(first.getValue()) == 0);
    }
}