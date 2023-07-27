package com.togrulmamishov;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PingPongController {

    @GetMapping("ping")
    public Map<String, String> getPingPong() {
        return Map.of("message", "pong");
    }
}
