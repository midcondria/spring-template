package com.commerce.team;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthChecktController implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

    @GetMapping
    public String health() {
        log.info("[헬스체크] 포트번호: {} 호출", serverPort);
        return "hi" + serverPort;
    }
}
