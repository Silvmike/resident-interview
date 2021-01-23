package ru.silvmike.interview.resident.average.app.rnd.kinesis;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.*;

import static ru.silvmike.interview.resident.average.app.rnd.kinesis.TestCredentials.*;

/**
 * DOESN'T WORK. WRONG CREDS.
 */
public class StdOutPrintingClient {

    public static void main(String[] args) {

        AmazonKinesis kinesis = buildAmazonKinesis(ACCESS_KEY, SECRET_KEY, Regions.US_EAST_1);
        try {

            ListShardsRequest listShardsRequest = new ListShardsRequest();
            listShardsRequest.setStreamName(STREAM_NAME);

            ListShardsResult shardsResult = kinesis.listShards(listShardsRequest);
            shardsResult.getShards().forEach(shard -> {

                GetShardIteratorResult shardIterator = buildShardIterator(kinesis, shard.getShardId());

                GetRecordsRequest recordsRequest = new GetRecordsRequest();
                recordsRequest.setShardIterator(shardIterator.getShardIterator());
                recordsRequest.setLimit(25);

                GetRecordsResult recordsResult = kinesis.getRecords(recordsRequest);
                while (!recordsResult.getRecords().isEmpty()) {
                    recordsResult.getRecords().stream()
                            .map(record -> new String(record.getData().array()))
                            .forEach(System.out::println);

                    recordsRequest.setShardIterator(recordsResult.getNextShardIterator());
                    recordsResult = kinesis.getRecords(recordsRequest);
                }
            });
        } finally {
            kinesis.shutdown();
        }
    }

    private static AmazonKinesis buildAmazonKinesis(String accessKey, String secretKey, Regions regions) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonKinesisClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(regions)
            .build();
    }

    private static GetShardIteratorResult buildShardIterator(AmazonKinesis kinesis, String shardId) {
        GetShardIteratorRequest readShardsRequest = new GetShardIteratorRequest();
        readShardsRequest.setStreamName(STREAM_NAME);
        readShardsRequest.setShardIteratorType(ShardIteratorType.LATEST);
        readShardsRequest.setShardId(shardId);

        return kinesis.getShardIterator(readShardsRequest);
    }

}
