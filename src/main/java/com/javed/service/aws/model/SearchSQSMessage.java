package com.javed.service.aws.model;

public class SearchSQSMessage {

    private String queueURL;
    private String requestAttemptId;

    public String getQueueURL() {
        return queueURL;
    }

    public void setQueueURL(String queueURL) {
        this.queueURL = queueURL;
    }

    public String getRequestAttemptId() {
        return requestAttemptId;
    }

    public void setRequestAttemptId(String requestAttemptId) {
        this.requestAttemptId = requestAttemptId;
    }
}
