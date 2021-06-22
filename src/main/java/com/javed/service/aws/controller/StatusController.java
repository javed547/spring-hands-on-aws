package com.javed.service.aws.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.Arrays;

@RestController
public class StatusController {

    private static final Logger logger = LogManager.getLogger(SQSController.class);

    @GetMapping("/health/status")
    public ResponseEntity<String> healthStatus(){
        logger.info("calling method : StatusController:healthStatus");

        /*CloudWatchLogsClient logsClient = CloudWatchLogsClient.builder()
                .build();

        DescribeLogStreamsRequest logStreamRequest = DescribeLogStreamsRequest.builder()
                .logGroupName("spring-hands-on-aws-log-group")
                .logStreamNamePrefix("streamName1")
                .build();
        DescribeLogStreamsResponse describeLogStreamsResponse = logsClient.describeLogStreams(logStreamRequest);*/

        // Assume that a single stream is returned since a specific stream name was specified in the previous request.
        //String sequenceToken = describeLogStreamsResponse.logStreams().get(0).uploadSequenceToken();

        // Build an input log message to put to CloudWatch.
        /*InputLogEvent inputLogEvent = InputLogEvent.builder()
                .message("{ \"key1\": \"value1\", \"key2\": \"value2\" }")
                .timestamp(System.currentTimeMillis())
                .build();*/

        // Specify the request parameters.
        // Sequence token is required so that the log can be written to the
        // latest location in the stream.
        /*PutLogEventsRequest putLogEventsRequest = PutLogEventsRequest.builder()
                .logEvents(Arrays.asList(inputLogEvent))
                .logGroupName("spring-hands-on-aws-log-group")
                .logStreamName("streamName1")
                .sequenceToken(sequenceToken)
                .build();

        logsClient.putLogEvents(putLogEventsRequest);*/
        // snippet-end:[cloudwatch.java2.put_log_events.main]

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }
}
