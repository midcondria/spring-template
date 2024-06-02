package com.commerce.team.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "aws")
public class AwsConfig {

    private final String accessKey;
    private final String secretKey;
    private final String s3AccessPoint;

    public String getS3AccessPoint() {
        return s3AccessPoint;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
            .credentialsProvider(getAwsBasicCredentials())
            .region(Region.AP_NORTHEAST_2)
            .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
            .credentialsProvider(getAwsBasicCredentials())
            .region(Region.AP_NORTHEAST_2)
            .build();
    }

    private StaticCredentialsProvider getAwsBasicCredentials() {
        return StaticCredentialsProvider.create(
            AwsBasicCredentials.create(accessKey, secretKey)
        );
    }
}
