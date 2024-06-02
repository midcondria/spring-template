package com.commerce.team.image.ui;

import com.commerce.team.image.application.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public String uploadFile(@RequestPart MultipartFile file) throws IOException {
        imageService.upload(file);
        return "이미지 저장 성공";
    }
}
