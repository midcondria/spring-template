package com.commerce.team;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthChecktController {

    @GetMapping
    public String health() {
        log.info("[헬스체크] \"/\" 호출");
        return "hi";
    }
}
