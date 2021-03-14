package com.javed.service.aws.utils;

import com.javed.service.aws.model.S3Bucket;
import com.javed.service.aws.model.S3BucketObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AWSServiceUtility {

    private static final Logger logger = LogManager.getLogger(AWSServiceUtility.class);

    public static List<S3Bucket> getS3Buckets(ListBucketsResponse listBucketsResponse) {
        List<S3Bucket> s3Buckets = new ArrayList<>();

        listBucketsResponse
                .buckets()
                .stream()
                .forEach(bucket -> s3Buckets
                        .add(new S3Bucket(bucket.name(), bucket.creationDate())));

        return s3Buckets;
    }

    public static List<S3BucketObject> getS3BucketObjects(List<S3Object> objectList) {
        List<S3BucketObject> s3BucketObjectList =  new ArrayList<>();

        objectList
                .stream()
                .forEach(s3Object -> s3BucketObjectList.add(new S3BucketObject(s3Object.key(),
                        s3Object.lastModified(),
                        s3Object.size())));

        return s3BucketObjectList;
    }

}
