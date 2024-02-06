package com.example.carwash.controller.s3;

import com.example.carwash.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/s3")
public class UploadController {
    private final S3Service s3Service;
    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file) throws IOException {
        return s3Service.saveFile(file);
    }
}
