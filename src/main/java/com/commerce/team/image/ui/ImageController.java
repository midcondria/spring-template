package com.commerce.team.image.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @PostMapping("/test/image")
    public String test() {
        return "hi";
    }
}
