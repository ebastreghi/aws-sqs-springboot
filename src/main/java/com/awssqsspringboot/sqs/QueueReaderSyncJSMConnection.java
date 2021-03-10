package com.awssqsspringboot.sqs;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.beans.factory.annotation.Value;

import javax.jms.*;


public class QueueReaderSyncJSMConnection {

    @Value("${aws.access-key}")
    private static String accessKey;

    @Value("${aws.secret-key}")
    private static String secretKey;

    @Value("${aws.sqs-resource}")
    private static String sqsResource;

    public static void main(String args[]) throws JMSException {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonSQS sqsClient = AmazonSQSClient.builder().withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);
        SQSConnection connection = connectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("aws-sqs-springboot");
        MessageConsumer consumer = session.createConsumer(queue);

        connection.start();

        Message receiveMessage = consumer.receive(1000);
        if (receiveMessage != null){
            System.out.println(receiveMessage);
        }
    }
}
