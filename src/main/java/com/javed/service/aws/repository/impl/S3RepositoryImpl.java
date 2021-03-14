package com.javed.service.aws.repository.impl;

import com.javed.service.aws.model.S3Bucket;
import com.javed.service.aws.model.S3BucketObject;
import com.javed.service.aws.repository.S3Repository;
import com.javed.service.aws.utils.AWSServiceUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Repository
public class S3RepositoryImpl implements S3Repository {

    private static final Logger logger = LogManager.getLogger(S3RepositoryImpl.class);

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3Presigner;

    /**
     * method to retrieve list of buckets associated with the integrated aws profile
     *
     * @return @{@link List} of @{@link S3Bucket}
     */
    @Override
    public List<S3Bucket> getBuckets() {
        List<S3Bucket> s3BucketList = new ArrayList<>();
        try {
            s3BucketList = AWSServiceUtility.getS3Buckets(s3Client.listBuckets());
        } catch (Exception exception) {
            logger.error("exception occurred while retrieving buckets list {}", exception.getMessage());
        }
        return s3BucketList;
    }

    /**
     * method to retrieve list of objects from S3 bucket
     *
     * @param bucketName
     * @return @{@link List} of @{@link S3BucketObject}
     */
    @Override
    public List<S3BucketObject> getObjects(String bucketName) {
        List<S3BucketObject> s3BucketObjectList = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = ListObjectsRequest
                .builder()
                .bucket(bucketName)
                .build();

        try {
            s3BucketObjectList = AWSServiceUtility.getS3BucketObjects(s3Client.listObjects(listObjectsRequest).contents());
        } catch (Exception exception) {
            logger.error("exception occurred while retrieving objects list {}", exception.getMessage());
        }

        return s3BucketObjectList;
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
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        ResponseBytes<GetObjectResponse> responseInputStream = s3Client.getObjectAsBytes(getObjectRequest);
        return responseInputStream;
    }

    /**
     * method to get s3 bucket objects preSigned URL
     *
     * @param @{@link String}
     * @param @{@link String}
     * @return @{@link ResponseBytes}
     */
    @Override
    public URL getPreSignedURL(String bucketName, String objectName) {
        URL preSignedURL = null;

        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build();

        GetObjectPresignRequest getObjectPresignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();

        getObjectBitTorrent();

        try {
            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
            preSignedURL = presignedGetObjectRequest.url();
        } catch (Exception exception) {
            logger.error("exception occurred while pre signing object {}", exception.getMessage());
        }

        return preSignedURL;
    }

    public void getObjectBitTorrent(){
        GetObjectTorrentRequest getObjectTorrentRequest = GetObjectTorrentRequest.builder().bucket("javed-the-developer-bucket").key("animal-africa-zoo-lion-33045.jpg").build();
        ResponseInputStream<GetObjectTorrentResponse> getObjectTorrentResponse = s3Client.getObjectTorrent(getObjectTorrentRequest);
        GetObjectTorrentResponse getObjectTorrentResponse1 = getObjectTorrentResponse.response();
    }

}
