package com.jiranit.jirminioservice.contoller;

import com.jiranit.jirminioservice.service.MinioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/minio")
public class MinioController {

    private MinioService minioService;

    public MinioController(MinioService minioService) {
        this.minioService = minioService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        minioService.uploadFile(file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Object> removeFile(@RequestParam("objectName") String objectName) {
        minioService.removeFile(objectName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/download/{objectName}")
    public ResponseEntity<Object> downloadObject(@PathVariable("objectName") String objectName) {
        minioService.downloadObject(objectName);
        return ResponseEntity.ok().build();
    }

}
