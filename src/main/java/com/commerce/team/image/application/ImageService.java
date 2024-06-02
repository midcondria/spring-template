package com.commerce.team.image.application;

import com.commerce.team.global.config.AwsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final AwsConfig awsConfig;

    public void upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

        s3Client.putObject(builder -> builder
                .bucket(awsConfig.getS3AccessPoint())
                .key(fileName)
            , requestBody);
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

        s3Presigner.close(); // presigner를 닫고 획득한 모든 리소스를 해제
        return url;
    }
}
