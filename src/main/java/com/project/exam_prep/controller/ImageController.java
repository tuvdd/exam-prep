package com.project.exam_prep.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@CrossOrigin
@RequestMapping("/api/image")
public class ImageController {

    // Thư mục để lưu ảnh
    private static final String UPLOAD_DIR = "D:\\Project\\exam-prep-fe\\media\\";

    // Tạo thư mục nếu chưa có
    static {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @PostMapping("/upload-avatar/{userId}")
    public ResponseEntity<String> uploadFile(@RequestParam("avatar") MultipartFile file,
                                             @PathVariable int userId) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Không có file nào được tải lên!", HttpStatus.BAD_REQUEST);
        }

        try {
            // Lấy tên file gốc
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                return new ResponseEntity<>("Tên file không hợp lệ", HttpStatus.BAD_REQUEST);
            }

            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = "User" + userId + fileExtension;

            // Đặt đường dẫn lưu file
            Path path = Paths.get(UPLOAD_DIR + newFileName);

            // Kiểm tra nếu file đã tồn tại, nếu có thì xóa file cũ
            if (Files.exists(path)) {
                Files.delete(path); // Ghi đè (xóa file cũ và lưu file mới)
            }

            // Lưu ảnh vào thư mục media với tên file mới hoặc ghi đè nếu tồn tại
            Files.copy(file.getInputStream(), path);

            return new ResponseEntity<>("/media/" + newFileName, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi khi tải ảnh lên!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
