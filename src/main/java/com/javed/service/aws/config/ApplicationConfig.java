package com.javed.service.aws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class ApplicationConfig {

    @Bean
    public S3Client s3Client() {
        Region region = Region.AP_SOUTH_1;
        S3Client s3 = S3Client.builder().region(region).build();
        return s3;
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.create();
    }
}
