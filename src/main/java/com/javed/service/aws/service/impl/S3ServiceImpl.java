package com.javed.service.aws.service.impl;

import com.javed.service.aws.model.S3Bucket;
import com.javed.service.aws.model.S3BucketObject;
import com.javed.service.aws.repository.S3Repository;
import com.javed.service.aws.service.S3Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.net.URL;
import java.util.List;

@Service
public class S3ServiceImpl implements S3Service {

    private static final Logger logger = LogManager.getLogger(S3ServiceImpl.class);

    @Autowired
    private S3Repository s3Repository;

    /**
     * method to retrieve list of buckets associated with the integrated aws profile
     *
     * @return @{@link List} of @{@link S3Bucket}
     */
    @Override
    public List<S3Bucket> getBuckets() {
        return s3Repository.getBuckets();
    }

    /**
     * method to retrieve list of objects from S3 bucket
     *
     * @param @{@link String}
     * @return @{@link List} of @{@link S3BucketObject}
     */
    @Override
    public List<S3BucketObject> getObjects(String bucketName) {
        return s3Repository.getObjects(bucketName);
    }

    /**
     * method to get s3 bucket object
     *
     * @param @{@link String}
     * @param @{@link String}
     * @return @{@link ResponseBytes}
     */
    @Override
    public ResponseBytes<GetObjectResponse> retrieveObjects(String bucketName, String objectName) {
        return s3Repository.retrieveObjects(bucketName, objectName);
    }

    /**
     * method to get s3 bucket objects preSigned URL
     *
     * @param @{@link String}
     * @param @{@link String}
     * @return @{@link URL}
     */
    @Override
    public URL getPreSignedURL(String bucketName, String objectName) {
        return s3Repository.getPreSignedURL(bucketName, objectName);
    }
}
