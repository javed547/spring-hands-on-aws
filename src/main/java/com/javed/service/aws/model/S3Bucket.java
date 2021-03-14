package com.javed.service.aws.model;

import java.time.Instant;

public class S3Bucket {

    String bucketName;
    Instant creationDate;

    public S3Bucket(String bucketName, Instant creationDate) {
        this.bucketName = bucketName;
        this.creationDate = creationDate;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
