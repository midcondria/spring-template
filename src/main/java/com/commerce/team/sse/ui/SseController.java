package com.commerce.team.sse.ui;

import com.commerce.team.global.config.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SseController {

    private final SseEmitters sseEmitters;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() throws IOException {
        SseEmitter emitter = new SseEmitter(60 * 1000L);
        sseEmitters.add(emitter);
        return emitter;
    }

    @PostMapping("/count")
    public void count() {
        System.out.println("count");
        sseEmitters.count();
    }
}
