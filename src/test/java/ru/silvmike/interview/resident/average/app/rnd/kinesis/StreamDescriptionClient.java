package ru.silvmike.interview.resident.average.app.rnd.kinesis;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;

import static ru.silvmike.interview.resident.average.app.rnd.kinesis.TestCredentials.*;

/**
 * DOESN'T WORK. WRONG CREDS.
 */
public class StreamDescriptionClient {

    public static void main(String[] args) {

        AmazonKinesis client = buildAmazonKinesis(ACCESS_KEY, SECRET_KEY, Regions.US_EAST_1);
        try {
            client.describeStream(STREAM_NAME).getStreamDescription().getShards().forEach(shard -> {
                System.out.println(shard.getShardId());
            });
        } finally {
            client.shutdown();
        }
    }

    private static AmazonKinesis buildAmazonKinesis(String accessKey, String secretKey, Regions regions) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonKinesisClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(regions)
                .build();
    }
}