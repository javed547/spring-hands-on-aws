package com.javed.service.aws.controller;

import com.javed.service.aws.model.S3Bucket;
import com.javed.service.aws.model.S3BucketObject;
import com.javed.service.aws.service.S3Service;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectTorrentRequest;
import software.amazon.awssdk.services.s3.model.GetObjectTorrentResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@RestController
public class S3Controller {

    private static final Logger logger = LogManager.getLogger(S3Controller.class);

    @Autowired
    private S3Service s3Service;

    @Autowired
    private S3Client s3Client;

    @GetMapping(value = "/buckets")
    public ResponseEntity<List<S3Bucket>> bucketList(){
        logger.info("calling method : S3Controller:bucketList");
        List<S3Bucket> s3BucketList = s3Service.getBuckets();

        return new ResponseEntity<List<S3Bucket>>(s3BucketList, HttpStatus.OK);
    }

    /**
     * list user available in application
     *
     * @return @
     */
    @GetMapping(value = "/buckets/{bucketName}/objects")
    public ResponseEntity<List<S3BucketObject>> getObjectList(@PathVariable String bucketName){
        logger.info("calling method : S3Controller:getObjectList");
        List<S3BucketObject> s3BucketObjectList = s3Service.getObjects(bucketName);

        return new ResponseEntity<List<S3BucketObject>>(s3BucketObjectList, HttpStatus.OK);
    }

    @GetMapping(value = "/buckets/{bucketName}/{object}")
    public ResponseEntity<byte[]> getObject(@PathVariable("bucketName") String bucketName, @PathVariable("object") String objectName) throws IOException {
        logger.info("calling method : S3Controller:getObject");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseBytes<GetObjectResponse> responseInputStream = s3Service.retrieveObjects(bucketName, objectName);

        FileUtils.writeByteArrayToFile(new File("G:\\"+objectName),responseInputStream.asByteArray());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(new File("G:\\"+objectName)));

        return ResponseEntity.ok().headers(headers).contentLength(responseInputStream.response().contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(responseInputStream.asByteArray());
    }

    @GetMapping(value = "/buckets/{bucketName}/presign/{object}")
    public ResponseEntity<URL> getPreSignedURL(@PathVariable("bucketName") String bucketName, @PathVariable("object") String objectName) throws IOException {
        logger.info("calling method : S3Controller:getPreSignedURL");
        URL url = s3Service.getPreSignedURL(bucketName, objectName);
        return new ResponseEntity<URL>(url, HttpStatus.OK);
    }

    @GetMapping(value = "/buckets/{bucketName}/torrent/{object}")
    public ResponseEntity<byte[]> getObjectTorrent(@PathVariable("bucketName") String bucketName, @PathVariable("object") String objectName) throws IOException {
        logger.info("calling method : S3Controller:getPreSignedURL");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        GetObjectTorrentRequest getObjectTorrentRequest = GetObjectTorrentRequest.builder().bucket("javed-the-developer-bucket").key("animal-africa-zoo-lion-33045.jpg").build();
        ResponseBytes<GetObjectTorrentResponse> getObjectTorrentResponse = s3Client.getObjectTorrentAsBytes(getObjectTorrentRequest);

        return ResponseEntity.ok().headers(headers).contentLength(getObjectTorrentResponse.asByteArray().length)
                .contentType(MediaType.parseMediaType("application/x-bittorrent"))
                .body(getObjectTorrentResponse.asByteArray());
    }


}
