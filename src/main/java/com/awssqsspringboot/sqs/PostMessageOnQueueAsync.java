package com.awssqsspringboot.sqs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class PostMessageOnQueueAsync {

    @Value("${aws.access-key}")
    private static String accessKey;

    @Value("${aws.secret-key}")
    private static String secretKey;

    @Value("${aws.sqs-resource}")
    private static String sqsResource;

    public static void main(String args[])
    {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonSQSAsync sqsClient = AmazonSQSAsyncClient.asyncBuilder().withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

        final SendMessageRequest sendMessageRequest = new SendMessageRequest();

        final String body = "Your message text" + System.currentTimeMillis() + "aws-sqs-springboot";

        sendMessageRequest.setMessageBody(body);
        sendMessageRequest.setQueueUrl(sqsResource);
        sqsClient.sendMessage(sendMessageRequest);

        final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                .withMaxNumberOfMessages(1)
                .withQueueUrl(sqsResource);
        final ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(receiveMessageRequest);

    }
}
