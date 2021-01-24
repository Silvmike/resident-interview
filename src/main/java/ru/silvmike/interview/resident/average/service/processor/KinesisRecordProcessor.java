package ru.silvmike.interview.resident.average.service.processor;


import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

@Slf4j
@RequiredArgsConstructor
public class KinesisRecordProcessor implements IRecordProcessor {

    private final SpaceObjectInfoProcessor infoConsumer;
    private final StringSpaceObjectConverter converter;

    private String shardId;

    @Override
    public void initialize(InitializationInput initializationInput) {

        this.shardId = initializationInput.getShardId();
    }

    @Override
    public void processRecords(ProcessRecordsInput processRecordsInput) {

        try {
            processRecordsInput.getRecords().forEach(record -> {

                String data = new String(record.getData().array());
                SpaceObjectInfo objectInfo = converter.spaceObjectInfo(data);
                infoConsumer.accept(objectInfo);
            });

        } catch (Exception e) {
            log.error("Unable to process one or more records, shardId=[{}]", shardId, e);
        }
    }

    @Override
    public void shutdown(ShutdownInput shutdownInput) {

        try {
            shutdownInput.getCheckpointer().checkpoint();
        } catch (InvalidStateException | ShutdownException e) {
            log.error("Unable to checkpoint, shardId=[{}]", shardId, e);
        }
    }
}
