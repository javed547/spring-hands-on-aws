package com.javed.service.aws.controller;

import com.javed.service.aws.model.PostSQSRequest;
import com.javed.service.aws.model.PostSQSResponse;
import com.javed.service.aws.model.QueueSearchCriteria;
import com.javed.service.aws.model.SearchSQSMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@RestController
public class SQSController {

    private static final Logger logger = LogManager.getLogger(SQSController.class);

    @PostMapping(value = "/queues", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> queuesList(@RequestBody QueueSearchCriteria queueSearchCriteria){
        logger.info("calling method : SQSController:queuesList");
        SqsClient sqsClient = SqsClient
                .builder()
                .region(Region.AP_SOUTH_1)
                .build();

        ListQueuesRequest listQueuesRequest = ListQueuesRequest
                .builder()
                .queueNamePrefix(queueSearchCriteria.getQueuePrefixCriteria())
                .build();

        ListQueuesResponse listQueuesResponse = sqsClient
                .listQueues(listQueuesRequest);

        return new ResponseEntity<List<String>>(listQueuesResponse.queueUrls(), HttpStatus.OK);
    }

    @PostMapping(value = "/queues/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostSQSResponse> postMessage(@RequestBody PostSQSRequest postSQSRequest){
        logger.info("calling method : SQSController:postMessage");
        SqsClient sqsClient = SqsClient
                .builder()
                .region(Region.AP_SOUTH_1)
                .build();

        SendMessageRequest sendMessageRequest = SendMessageRequest
                .builder()
                .queueUrl(postSQSRequest.getQueueName())
                .messageBody(postSQSRequest.getMessage())
                .build();

        SendMessageResponse sendMessageResponse = sqsClient
                .sendMessage(sendMessageRequest);

        PostSQSResponse postSQSResponse = new PostSQSResponse();
        postSQSResponse.setPostMessageResponse(sendMessageResponse.messageId());

        return new ResponseEntity<PostSQSResponse>(postSQSResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/queues/messages/{messageId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMessage(@RequestBody SearchSQSMessage searchSQSMessage, @PathVariable String messageId){
        logger.info("calling method : SQSController:getMessage");
        SqsClient sqsClient = SqsClient
                .builder()
                .region(Region.AP_SOUTH_1)
                .build();

        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest
                .builder()
                .queueUrl(searchSQSMessage.getQueueURL())
                .receiveRequestAttemptId(messageId)
                .build();

        ReceiveMessageResponse receiveMessageResponse = sqsClient
                .receiveMessage(receiveMessageRequest);

        return new ResponseEntity<String>(receiveMessageResponse.messages().get(0).body(), HttpStatus.OK);
    }

}
