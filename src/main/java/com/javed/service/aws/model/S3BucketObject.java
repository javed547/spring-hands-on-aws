package com.javed.service.aws.model;

import java.time.Instant;

public class S3BucketObject {

    private String key;
    private Instant lastModified;
    private Long size;

    public S3BucketObject(String key, Instant lastModified, Long size) {
        this.key = key;
        this.lastModified = lastModified;
        this.size = size;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
