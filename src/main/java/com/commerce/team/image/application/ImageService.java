package com.commerce.team.image.application;

import com.commerce.team.global.config.AwsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.CreateMultipartUploadPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final AwsConfig awsConfig;

    public void upload() {
        String imagePath = "src/main/resources/static/test2.jpg";
        Path path = Paths.get(imagePath);

        s3Client.putObject(builder -> builder
                .bucket(awsConfig.getS3AccessPoint())
                .key("myKey4")
            , path);
    }

    public String getPresignUrl(String filename) {
        if(filename == null || filename.equals("")) {
            return null;
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(awsConfig.getS3AccessPoint())
            .key("나는짱.jpg")
            .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(5))
            .putObjectRequest(putObjectRequest)
            .build();

        String url = s3Presigner
            .presignPutObject(presignRequest)
            .url()
            .toString();

//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//            .bucket(awsConfig.getS3AccessPoint())
//            .key(filename)
//            .build();
//
//        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
//            .signatureDuration(Duration.ofMinutes(5)) // presignedURL 5분간 접근 허용
//            .getObjectRequest(getObjectRequest)
//            .build();
//
//        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner
//            .presignGetObject(getObjectPresignRequest);
//
//        String url = presignedGetObjectRequest.url().toString();

        s3Presigner.close(); // presigner를 닫고 획득한 모든 리소스를 해제
        return url;
    }
}
