package com.example.practice.config.s3.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Map;

@Service
@Getter
@Setter
@Slf4j
public class S3Service {

    private final S3Client s3;
    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.backetPath}")
    private String bucketPath;

    public S3Service(S3Client s3)
    {
        this.s3 = s3;
    }

    public Map<String, String> upload(MultipartFile file) throws IOException {

        System.out.println("00");
        log.info("Uploading file ...");
        String key = System.currentTimeMillis() + file.getOriginalFilename();

        System.out.println("11");
        PutObjectRequest por = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        System.out.println("22");
        s3.putObject(por, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        System.out.println("33");
        log.info("Upload complete.");

        return Map.of(
                "link", bucketPath + key
        );

    }

    public Map<String, String> delete(String fileLink) {

        log.info("Deleting file...");

        try {

            String key = fileLink.substring(bucketPath.length());

            log.warn("Deleting object: {}", key);

            s3.deleteObject(dor -> dor.bucket(bucketName).key(key).build());

        } catch (S3Exception e) {
            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }

        return Map.of(
                "message", fileLink + " has been deleted."
        );
    }
}