package com.awssqsspringboot.sqs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class CreateQueue {

    @Value("${aws.access-key}")
    private static String accessKey;
    @Value("${aws.secret-key}")
    private static String secretKey;

    public static void main(String args[])
    {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonSQS sqsClient = AmazonSQSClient.builder().withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

        CreateQueueRequest createQueueRequest = new CreateQueueRequest("aws-sqs-from-code");
        CreateQueueResult createQueueResult = sqsClient.createQueue(createQueueRequest);
        System.out.println("QUEUE URL: " + createQueueResult.getQueueUrl());

    }
}
