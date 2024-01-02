package com.lambda.user;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.lambda.user.collection.User;
import com.lambda.user.dto.Request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class LambdaMethodHandler implements RequestHandler<Request, List<User>> {

    private static final String SNS_TOPIC_ARM = System.getenv("SNS_TOPIC_ARN");
    private static final String MESSAGE = "Envio mensaje de texto con fecha %S hora %s";
    private final AmazonDynamoDB clientDB = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    private final AmazonSNS clientSNS = AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    @Override
    public List<User> handleRequest(Request input, Context context) {
        if (input.getHttpMethod().equalsIgnoreCase("GET")) {
            return getUsers();
        } else if (input.getHttpMethod().equalsIgnoreCase("POST")) {
            publishSMS();
        }
        return Collections.emptyList();
    }

    public List<User> getUsers() {
        DynamoDBMapper mapper = new DynamoDBMapper(clientDB);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return mapper.scan(User.class, scanExpression);
    }

    public void publishSMS() {
        PublishRequest request = new PublishRequest()
                .withMessage(String.format(MESSAGE, LocalDate.now(), LocalTime.now()))
                .withTopicArn(SNS_TOPIC_ARM);
        clientSNS.publish(request);
    }
}
