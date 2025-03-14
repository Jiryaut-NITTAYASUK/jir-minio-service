package com.jiranit.jirminioservice.core.utils;

import io.minio.*;
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

    public void removeFile(String objectName) {
        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build();

            minioClient.removeObject(args);
            log.debug("File deleted successfully :: {}", objectName);
        } catch (Exception e) {
            log.error("Error while deleting file from Minio :: {}", e.getMessage());
        }
    }

    public void downloadObject (String objectName) {
        try {
            DownloadObjectArgs args = DownloadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .filename("downloaded_" + objectName)
                    .build();

            minioClient.downloadObject(args);
        } catch (Exception e) {
            log.error("Error while downloading file from Minio :: {}", e.getMessage());
        }
    }
}
