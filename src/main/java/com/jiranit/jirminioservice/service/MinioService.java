package com.jiranit.jirminioservice.service;

import com.jiranit.jirminioservice.core.utils.MinioUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioService.class);

    private final MinioUtils minioUtils;

    public MinioService(MinioUtils minioUtils) {
        this.minioUtils = minioUtils;
    }

    public void uploadFile(MultipartFile file) {
        minioUtils.uploadFile(file);
    }

}
