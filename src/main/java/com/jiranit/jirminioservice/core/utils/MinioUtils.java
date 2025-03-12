package com.jiranit.jirminioservice.core.utils;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class MinioUtils {

    private static final Logger log = LoggerFactory.getLogger(MinioUtils.class);
    @Value("${minio.bucketName}")
    private String bucketName;

    private final MinioClient minioClient;

    public MinioUtils(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void uploadFile(MultipartFile file) {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(UUID.randomUUID() + LocalDate.now().toString() + file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build();

            ObjectWriteResponse objWriteResponse = minioClient.putObject(args);
            log.debug("File uploaded successfully :: {}", objWriteResponse.object());
        } catch (Exception e) {
            log.error("Error while uploading file to Minio :: {}", e.getMessage());
        }
    }
}
