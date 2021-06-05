package com.javed.service.aws.repository;

import com.javed.service.aws.model.S3Bucket;
import com.javed.service.aws.model.S3BucketObject;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.net.URL;
import java.util.List;

public interface S3Repository {

    /**
     * method to retrieve list of buckets associated with the integrated aws profile
     *
     * @return @{@link List} of @{@link S3Bucket}
     */
    List<S3Bucket> getBuckets();

    /**
     * method to retrieve list of objects from S3 bucket
     *
     * @param @{@link String}
     * @return @{@link List} of @{@link S3BucketObject}
     */
    List<S3BucketObject> getObjects(String bucketName);

    /**
     * method to get s3 bucket object
     *
     * @param @{@link String}
     * @param @{@link String}
     * @return @{@link ResponseBytes}
     */
    ResponseBytes<GetObjectResponse> retrieveObjects(String bucketName, String objectName);

    /**
     * method to get s3 bucket objects preSigned URL
     *
     * @param @{@link String}
     * @param @{@link String}
     * @return @{@link URL}
     */
    URL getPreSignedURL(String bucketName, String objectName);
}
