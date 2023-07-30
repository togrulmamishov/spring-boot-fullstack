package com.togrulmamishov;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {

    private static int COUNTER = 0;

    @GetMapping("ping")
    public PingPong getPingPong() {
        return new PingPong("Pong: %s".formatted(++COUNTER));
    }

    private static record PingPong(String message) {
    }
}
