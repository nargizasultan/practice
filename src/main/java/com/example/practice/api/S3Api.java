package com.example.practice.api;

import com.example.practice.config.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor

@Tag(name = "AWS S3 API", description = "Admin can upload, download or delete files")
public class S3Api {

    private final S3Service s3service;

    @Operation(
            summary = "Upload file",
            description = "for upload file to s3")
    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Map<String, String> upload(@RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        return s3service.upload(file);
    }

    @Operation(
            summary = "Delete file",
            description = "Deleting file from application")
    @DeleteMapping("/remove/{fileName}")
    Map<String, String> delete(@RequestParam String fileLink) {
        return s3service.delete(fileLink);
    }
}