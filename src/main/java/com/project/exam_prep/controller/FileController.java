package com.project.exam_prep.controller;

import com.project.exam_prep.service.impl.S3ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final S3ServiceImpl s3Service;

    @Autowired
    public FileController(S3ServiceImpl s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileUrl = s3Service.uploadFile(file);
        return ResponseEntity.ok(fileUrl);
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<List<String>> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
        List<String> fileUrls = files.stream()
                .map(s3Service::uploadFile)
                .collect(Collectors.toList());
        return ResponseEntity.ok(fileUrls);
    }
}

